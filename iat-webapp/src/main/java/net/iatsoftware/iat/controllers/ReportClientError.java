/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.controllers;

/**
 *
 * @author michael
 */
import net.iatsoftware.iat.admin.IATSessionManager;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.ClientExceptionReport;
import net.iatsoftware.iat.entities.User;
import net.iatsoftware.iat.generated.ErrorReportResponseCode;
import net.iatsoftware.iat.messaging.ActivationException;
import net.iatsoftware.iat.messaging.ClientErrorReport;
import net.iatsoftware.iat.messaging.ClientException;
import net.iatsoftware.iat.messaging.CorruptedSaveFileReport;
import net.iatsoftware.iat.messaging.ErrorReportResponse;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.services.EmailParameters;
import net.iatsoftware.iat.services.MailService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.Marshaller;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.StringWriter;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;
import javax.inject.Inject;
import javax.xml.transform.stream.StreamResult;

@Controller
@RequestMapping("/ClientErrorReport")
@PropertySource("classpath:email/email-config.properties")
@PropertySource("classpath:client-error-report.properties")
public class ReportClientError {

    private static final Logger logger = LogManager.getLogger("critical");
    private static final Base64.Decoder b64Decoder = Base64.getDecoder();
    private static final Base64.Encoder b64Encoder = Base64.getEncoder();
    private static final ConcurrentHashMap<String, Long> reporters = new ConcurrentHashMap<>();
    private static final Random rand = new Random();

    @Inject
    MailService mailService;
    @Inject
    IATRepositoryManager iatRepositoryManager;
    @Inject
    Marshaller marshaller;
    @Inject
    IATSessionManager sessionManager;

    @Value("${error-report-recipient}")
    private String errorReportRecipient;

    @Value("${mail.images.logo-classpath-location}")
    private String logoClasspathLocation;

    @Value("${mail.images.header-classpath-location}")
    private String headerClasspathLocation;

    @Value("${rsa.modulus}")
    private String rsaModulus;

    @Value("${rsa.exponent}")
    private String rsaExponent;

    @Value("${error-response-caption.invalid-handshake}")
    private String invalidHandshakeCaption;

    @Value("${error-response-message.invalid-handshake}")
    private String invalidHandshakeMessage;

    @Value("${error-response-caption.kill-filed}")
    private String killFiledCaption;

    @Value("${error-response-message.kill-filed}")
    private String killFiledMessage;

    private static final String PNG_MIME_TYPE = "image/png";

    private Cipher getCipher()
            throws java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException,
            javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException {
        var modulusData = b64Decoder.decode(rsaModulus);
        var modulusBytes = new byte[modulusData.length + 1];
        modulusBytes[0] = 0;
        System.arraycopy(modulusData, 0, modulusBytes, 1, modulusData.length);
        var exponentData = b64Decoder.decode(rsaExponent);
        var exponentBytes = new byte[exponentData.length + 1];
        exponentBytes[0] = 0;
        System.arraycopy(exponentData, 0, exponentBytes, 1, exponentData.length);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(new BigInteger(modulusBytes),
                new BigInteger(exponentBytes));
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PublicKey pubKey = fact.generatePublic(keySpec);
        Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c.init(Cipher.ENCRYPT_MODE, pubKey);
        return c;
    }

    @GetMapping(value = "", produces="text/plain")
    @ResponseBody
    public String initErrorReport()
            throws java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException,
            javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException,
            javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
        byte[] responseBytes = new byte[64];
        rand.nextBytes(responseBytes);
        Cipher c = getCipher();
        String challenge = b64Encoder.encodeToString(c.doFinal(responseBytes));
        String response = b64Encoder.encodeToString(responseBytes);
        reporters.put(response, Calendar.getInstance().getTimeInMillis());
        return challenge;
    }

    @PostMapping(value = "/Activation", consumes = "text/xml", produces = "text/xml") 
    public ResponseEntity<ErrorReportResponse> acvtivationErrorReport(@RequestBody ActivationException exception,
            @RequestHeader("response") String challengeResponse) {
        try {
            ErrorReportResponse resp = new ErrorReportResponse();
            if (!reporters.containsKey(challengeResponse)) {
                resp.setCaption(invalidHandshakeCaption);
                resp.setMessage(invalidHandshakeMessage);
                resp.setResponse(ErrorReportResponseCode.INVALID_HANDSHAKE);
                return new ResponseEntity<>(resp, HttpStatus.OK);
            }
            reporters.remove(challengeResponse);
            EmailParameters emailParams = new EmailParameters(errorReportRecipient, 
                "Product Activation Error", "email/client-activation-error-report.html");
            emailParams.addInlineImage("logo", logoClasspathLocation, PNG_MIME_TYPE);
            emailParams.addInlineImage("header", headerClasspathLocation, PNG_MIME_TYPE);
            if (exception.getProductKey() != null) {
                emailParams.addParameter("client", iatRepositoryManager.getClient(exception.getProductKey()));
            }
            emailParams.addParameter("email", exception.getEmail());
            emailParams.addParameter("version", exception.getVersion());
            emailParams.addParameter("error", exception.getException());
            mailService.sendEmail(emailParams);
            resp.setResponse(ErrorReportResponseCode.SUCCESS);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        catch (Exception ex) {
            logger.error("Error reporting client activation error", ex);
            ErrorReportResponse serverErrorResponse = new ErrorReportResponse();
            serverErrorResponse.setCaption("Server Error");
            serverErrorResponse.setMessage(ex.toString());
            serverErrorResponse.setResponse(ErrorReportResponseCode.SERVER_ERROR);
            return new ResponseEntity<>(serverErrorResponse, HttpStatus.OK);
        }
    }

    @PostMapping(value = "", consumes = "text/xml", produces = "text/xml")
    public ResponseEntity<ErrorReportResponse> errorReport(@RequestBody ClientException exception,
            @RequestHeader("response") String challengeResponse) {
        try {
            ErrorReportResponse resp = new ErrorReportResponse();
            if (!reporters.containsKey(challengeResponse)) {
                resp.setCaption(invalidHandshakeCaption);
                resp.setMessage(invalidHandshakeMessage);
                resp.setResponse(ErrorReportResponseCode.INVALID_HANDSHAKE);
                return new ResponseEntity<>(resp, HttpStatus.OK);
            }
            reporters.remove(challengeResponse);
            StringWriter sWriter = new StringWriter();
            StreamResult sResult = new StreamResult(sWriter);
            sWriter.flush();
            marshaller.marshal(exception, sResult);
            Client c = iatRepositoryManager.getClient(exception.getProductCode());
            if (c.isKillFiled()) {
                resp.setCaption(killFiledCaption);
                resp.setMessage(killFiledMessage);
                resp.setResponse(ErrorReportResponseCode.KILL_FILED);
                return new ResponseEntity<>(resp, HttpStatus.OK);
            }
            User u = iatRepositoryManager.getUserByClientAndActivationKey(c, exception.getActivationKey());
            iatRepositoryManager.recordClientException(new ClientExceptionReport(c, u, sWriter.toString()));
            ClientErrorReport cer = new ClientErrorReport(c, u, exception.getVersion(), exception,
                    Calendar.getInstance());
            EmailParameters emailParams = new EmailParameters(errorReportRecipient,
                    "IAT Error for Client #" + Long.toString(c.getClientId()), "email/client-error-report.html");
            emailParams.addParameter("errorReport", cer);
            emailParams.addInlineImage("logo", logoClasspathLocation, PNG_MIME_TYPE);
            emailParams.addInlineImage("header", headerClasspathLocation, PNG_MIME_TYPE);
            mailService.sendEmail(emailParams);
            return new ResponseEntity<>(new ErrorReportResponse(ErrorReportResponseCode.SUCCESS), HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error processing client error report", ex);
            ErrorReportResponse resp = new ErrorReportResponse();
            resp.setCaption("Server Error");
            resp.setMessage(ex.toString());
            resp.setResponse(ErrorReportResponseCode.SERVER_ERROR);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/InvalidSaveFile", consumes = "text/xml")
    public @ResponseBody String corruptedSaveFileReport(@RequestBody CorruptedSaveFileReport report)
            throws javax.mail.MessagingException {
        Client c = iatRepositoryManager.getClient(report.getProductCode());
        c.setInvalidSaveFiles(c.getInvalidSaveFiles() + 1);
        if (c.getInvalidSaveFiles() >= 3) {
            c.setFrozen(true);
        }
        iatRepositoryManager.updateClient(c);
        report.setClientId(iatRepositoryManager.getClient(report.getProductCode()).getClientId());
        EmailParameters params = new EmailParameters(errorReportRecipient, "Invalid Save File",
                "email/invalid-save-file-report.html");
        params.addParameter("report", report);
        mailService.sendEmail(params);
        if (c.isFrozen()) {
            return "frozen";
        }
        return "warning";
    }

    @GetMapping(value = "/ExtraData", produces="text/plain")
    public @ResponseBody String recordExtraData(@RequestParam(name="ProductKey", required=false, defaultValue="") String productKey,
            @RequestParam(name="Email", required=false, defaultValue="") String email) {
        boolean emailExists = email.isEmpty() ? false : iatRepositoryManager.checkClientEmailExists(email);
        boolean productKeyExists = productKey.isEmpty() ? false : (iatRepositoryManager.getClient(productKey) != null);
        if (emailExists && productKeyExists) {
            return "Both";
        } else if (emailExists) {
            return "Email";
        } else if (productKeyExists) {
            return "ProductKey";
        } else {
            return "Neither";
        }
    }

    @Scheduled(initialDelay = 1_800_000L, fixedDelay = 1_800_000L)
    private static void cleanupReporters() {
        Enumeration<String> keys = reporters.keys();
        keys.asIterator().forEachRemaining(s -> {
            if (reporters.get(s) + 30_000L < System.currentTimeMillis())
                reporters.remove(s);
        });
    }
}
