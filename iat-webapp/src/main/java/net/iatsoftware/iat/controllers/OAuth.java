/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.controllers;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.dataservices.XsltService;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.OAuthAccess;
import net.iatsoftware.iat.forms.OAuthErrorReport;
import net.iatsoftware.iat.forms.OAuthRequestForm;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.services.EmailParameters;
import net.iatsoftware.iat.services.MailService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.Marshaller;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Named;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/OAuth")
public class OAuth {

    private static final Logger logger = LogManager.getLogger();

    @Inject
    ApplicationContext ctx;
    @Inject
    IATRepositoryManager repositoryManager;
    @Inject
    MessageSource messageSource;
    @Inject
    MailService mailService;
    @Inject
    Marshaller marshaller;
    @Inject
    XsltService xsltService;
    @Named("ExceptionMessageSource")
    @Inject
    MessageSource exceptionMessageSource;

    @GetMapping(value = "/RequestAuth")
    public ModelAndView doRequestAuth(@RequestParam(value = "client_id", required = true) String oClientId,
            @RequestParam(value = "state", required = false, defaultValue = "") String state,
            @RequestParam(value = "request_uri", required = false, defaultValue = "") String redirectUri)
            throws java.io.UnsupportedEncodingException, OAuthException {
        Pattern pat = Pattern.compile("^CLIENT([1-9][0-9]*):([A-Za-z0-9_\\-]+):(.+)$");
        Matcher matcher = pat.matcher(oClientId);
        if (!matcher.matches()) {
            throw new OAuthException(OAuthExceptionType.INVALID_CLIENT_ID, oClientId);
        }
        Client c = repositoryManager.getClient(Long.parseLong(matcher.group(1)));
        String testName = matcher.group(2);
        IAT test = repositoryManager.getIATByNameAndClientID(testName, c.getClientId());
        if (test == null) {
            throw new OAuthException(OAuthExceptionType.NOT_REGISTERED, oClientId);
        }

        if (test.getOauthClientId() == null) {
            throw new OAuthException(OAuthExceptionType.NOT_REGISTERED, oClientId);
        }
        if (!test.getOauthClientId().equals(oClientId)) {
            throw new OAuthException(OAuthExceptionType.INVALID_CLIENT_ID, oClientId);
        }
        String oauthToken = repositoryManager.createOAuthToken(c, test);
        if ((!(test.isOauthSubpathRedirects()) && (!redirectUri.equals("")))
                && (!test.getOauthClientRedirect().equals(redirectUri))) {
            throw new OAuthException(OAuthExceptionType.EXPLICIT_REDIRECT_NOT_SUPPORTED, test, oClientId);
        }
        String redirectURL;
        if (redirectUri.isEmpty()) {
            redirectURL = test.getOauthClientRedirect();
        } else {
            redirectURL = URLDecoder.decode(redirectUri, "UTF-8");
        }
        redirectURL += "?code=" + URLEncoder.encode(oauthToken, "UTF-8")
                + ((state.isEmpty()) ? "" : ("&state=" + URLEncoder.encode(state, "UTF-8")));
        return new ModelAndView(new RedirectView(redirectURL));
    }

    @ExceptionHandler(OAuthException.class)
    @ResponseBody
    public ResponseEntity<ModelAndView> handleException(
            @RequestHeader(name = "accept", required = false, defaultValue = "text/json") String accepts,
            @RequestParam(value = "client_id", required = true) String oClientId,
            @RequestParam(value = "code", required = false, defaultValue = "") String authToken, OAuthException ex) {
        OAuthErrorReport errorReport = new OAuthErrorReport();
        Pattern patt = Pattern.compile("([1-9][0-9]*):([a-zA-Z0-9\\-_]+):(.+)");
        if (!patt.matcher(oClientId).matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long clientId = Long.getLong(patt.matcher(oClientId).group(1));
        String testName = patt.matcher(oClientId).group(2);
        IAT test = this.repositoryManager.getIATByNameAndClientID(testName, clientId);
        if (test == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (authToken.isEmpty()) {
            errorReport.setExceptionCaption("Error initiating OAuth");
        } else if (!authToken.isEmpty()) {
            errorReport.setExceptionCaption("Error performing auth");
        }
        errorReport.setStackTrace(
                Arrays.asList(ex.getStackTrace()).stream().map((ste) -> ste.toString()).toArray(String[]::new));
        errorReport.setContactEmail(test.getUser().getEMail());
        errorReport.setContactName(test.getUser().getFullName());
        errorReport.setExceptionMessage(ex.getMessage());
        errorReport.setTestName(test.getTestName());
        errorReport.setClientId(oClientId);
        try {
            EmailParameters params = new EmailParameters("sysinfo@iatsoftware.net", "OAuth Error",
                    "email/oauth-error-report.html");
            params.addParameter("errorReport", errorReport);
            this.mailService.sendEmail(params);
            errorReport.setReported(true);
        } catch (jakarta.mail.MessagingException ex2) {
            logger.error("Error reporting oauth exception:", ex);
            logger.error("Because of: ", ex2);
            errorReport.setReported(false);
        }
        if (accepts.equalsIgnoreCase("text/json") || accepts.equalsIgnoreCase("application/json")) {
            MappingJackson2JsonView jackson = new MappingJackson2JsonView();
            jackson.setPrettyPrint(true);
            jackson.setModelKey("errorReport");
            ModelAndView mv = new ModelAndView(jackson);
            mv.addObject("errorReport", errorReport);
            return new ResponseEntity<>(mv, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            ModelAndView mv = new ModelAndView("OAuthWebException.html");
            mv.addObject("errorReport", errorReport);
            return new ResponseEntity<>(mv, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/RequestAuth", produces = "text/json", consumes = { "application/json", "text/json" })
    public ModelAndView requestOAuthenticateJson(@Valid @RequestBody OAuthRequestForm request, Errors errors) {
        Map<String, Object> model = new HashMap<>();
        if (errors.hasErrors()) {
            model.put("access_token", "error");
            model.put("refresh_token", "error");
            model.put("token_type", "none");
            model.put("error", errors.getAllErrors().get(0).getDefaultMessage());
        } else if (!request.getGrantType().equals("code")) {
            model.put("access_token", "error");
            model.put("refresh_token", "error");
            model.put("token_type", "none");
            model.put("error", "grant_type must be 'code'");
        } else {
            int authResult = repositoryManager.verifyAuthToken(request.getCode(), request.getClientId(),
                    request.getClientSecret());
            if (authResult == OAuthAccess.AUTH_TOKEN_VALID) {
                OAuthAccess oAuth = repositoryManager.performOAuth(request.getCode());
                model.put("access_token", oAuth.getAccessToken());
                model.put("refresh_token", oAuth.getRefreshToken());
                model.put("token_type", "bearer");
                model.put("expires", OAuthAccess.accessExpiration);
            } else {
                model = generateResponseModel(authResult);
            }
        }
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);
        return new ModelAndView(view, model);
    }

    @PostMapping(value = "/RequestAuth", produces = "text/json")
    public ModelAndView requestOAuthenticateForm(@RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret, @RequestParam("code") String code,
            @RequestParam("grant_type") String grantType) {

        Map<String, Object> model = new HashMap<>();
        if (!repositoryManager.clientIdMatchesClientSecret(clientId, clientSecret)) {
            model.put("access_token", "error");
            model.put("refresh_token", "error");
            model.put("token_type", "none");
            model.put("error", "client_id does not match client_secret");
        } else if (!grantType.equals("code")) {
            model.put("access_token", "error");
            model.put("refresh_token", "error");
            model.put("token_type", "none");
            model.put("error", "grant_type must be 'code'");
        } else {
            int authResult = repositoryManager.verifyAuthToken(code, clientId, clientSecret);
            if (authResult == OAuthAccess.AUTH_TOKEN_VALID) {
                OAuthAccess oAuth = repositoryManager.performOAuth(code);
                model.put("access_token", oAuth.getAccessToken());
                model.put("refresh_token", oAuth.getRefreshToken());
                model.put("token_type", "bearer");
                model.put("expires", OAuthAccess.accessExpiration);
            } else {
                model = generateResponseModel(authResult);
            }
        }
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);
        return new ModelAndView(view, model);
    }

    @PostMapping(value = "/Refresh", produces = "text/json")
    public ModelAndView processRefresh(@RequestParam(value = "client_id", required = true) String clientId,
            @RequestParam(value = "client_secret", required = true) String clientSecret,
            @RequestParam(value = "refresh_token", required = true) String refreshToken,
            @RequestParam(value = "grant_type", required = true) String grantType) {
        Map<String, Object> model = new HashMap<>();
        if (!grantType.equals("refresh_token")) {
            model.put("access_token", "error");
            model.put("refresh_token", "error");
            model.put("token_type", "none");
            model.put("error", "grant_type must be 'refresh_token'");
        } else {
            int authResult = repositoryManager.verifyRefreshToken(refreshToken, clientId, clientSecret);
            if (authResult == OAuthAccess.REFRESH_TOKEN_VALID) {
                model.put("access_token", repositoryManager.refreshOAuthAccessToken(refreshToken));
                model.put("refresh_token", refreshToken);
                model.put("token_type", "bearer");
                model.put("expires", OAuthAccess.accessExpiration);
            } else {
                model = generateResponseModel(authResult);
            }
        }
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);
        return new ModelAndView(view, model);
    }

    private Map<String, Object> generateResponseModel(int oauthResult) {
        Map<String, Object> model = new HashMap<>();
        switch (oauthResult) {
            case OAuthAccess.AUTH_TOKEN_NOT_FOUND:
                model.put("access_token", "error");
                model.put("refresh_token", "error");
                model.put("token_type", "none");
                model.put("expires", 0L);
                model.put("error", "invalid authentication code");
                break;

            case OAuthAccess.NO_SUCH_REFRESH_TOKEN:
                model.put("access_token", "error");
                model.put("refres_token", "error");
                model.put("token_type", "none");
                model.put("expires", 0L);
                model.put("error", "no such refresh token");
                break;

            case OAuthAccess.INVALID_CLIENT_ID:
                model.put("access_token", "error");
                model.put("refresh_token", "error");
                model.put("token_type", "none");
                model.put("expires", 0L);
                model.put("error", "invalid client id");
                break;

            case OAuthAccess.MISMATCHED_CLIENT_ID:
                model.put("access_token", "error");
                model.put("refresh_token", "error");
                model.put("token_type", "none");
                model.put("expires", 0L);
                model.put("error", "client id does not match authentication code");
                break;

            case OAuthAccess.AUTH_TOKEN_CONSUMED:
                model.put("access_token", "error");
                model.put("refresh_token", "error");
                model.put("token_type", "none");
                model.put("expires", 0L);
                model.put("error", "authentication code already consumed");
                break;

            case OAuthAccess.INVALID_CLIENT_SECRET:
                model.put("access_token", "error");
                model.put("refresh_token", "error");
                model.put("token_type", "none");
                model.put("expires", 0L);
                model.put("error", "invalid client secret");
                break;

            default:
                return null;
        }
        return model;
    }

    @PostMapping(value = "/ErrorReport", consumes = "text/json")
    public void processErrorReport(@RequestBody OAuthErrorReport errorReport) {
        try {
            EmailParameters params = new EmailParameters("sysinfo@iatsoftware.net", "OAuth Error",
                    "email/oauth-error-report.html");
            params.addParameter("errorReport", errorReport);
            this.mailService.sendEmail(params);
        } catch (Exception ex) {
            logger.error("Error sending oauth error report", ex);
        }
    }

    @Scheduled(initialDelay = 60_000, fixedDelay = 60_000)
    private void cleanupExpiredAuthSessions() {
        repositoryManager.cleanupExpiredOAuthTokens();
    }
}
