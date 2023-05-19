/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.admin;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.function.BinaryOperator;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Named;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.iatsoftware.iat.config.MyBeanFactory;
import net.iatsoftware.iat.deployment.JSKeys;
import net.iatsoftware.iat.entities.AdminTimer;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.EncCodeLine;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.RSAKeyData;
import net.iatsoftware.iat.entities.TestResultFragment;
import net.iatsoftware.iat.entities.TestSegment;
import net.iatsoftware.iat.entities.UniqueResponse;
import net.iatsoftware.iat.entities.UniqueResponseItem;
import net.iatsoftware.iat.events.WebSocketEvent;
import net.iatsoftware.iat.events.WebSocketEventType;
import net.iatsoftware.iat.generated.TokenType;
import net.iatsoftware.iat.messaging.AjaxResponse;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.resultdata.ResultTOC;
import net.iatsoftware.iat.resultdata.ResultTOCEntry;
import net.iatsoftware.iat.services.MailService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@EnableAsync
@PropertySource("classpath:iat.webapp.properties")
@RequestMapping(value = "/Admin")
public class AdminController {
	@SuppressWarnings("unused")
	private abstract class TokenException extends Exception {
		private static final long serialVersionUID = 1L;
		private final IAT test;

		public TokenException(IAT test) {
			this.test = test;
		}

		public abstract String getCaption();

		public IAT getTest() {
			return this.test;
		}
	}

	private class NoTokenException extends TokenException {
		private static final long serialVersionUID = 1L;

		public NoTokenException(IAT test) {
			super(test);
		}

		public String getCaption() {
			return tokenNotSuppliedCaption;
		}
	}

	@SuppressWarnings("unused")
	private class InvalidTokenNameException extends TokenException {
		private static final long serialVersionUID = 1L;
		private final String suppliedTokenName;

		public InvalidTokenNameException(IAT test, String suppliedTokenName) {
			super(test);
			this.suppliedTokenName = suppliedTokenName;
		}

		@Override
		public String getCaption() {
			return invalidTokenNameCaption;
		}

		public String getSuppliedTokenName() {
			return this.suppliedTokenName;
		}
	}

	@SuppressWarnings("unused")
	private class TokenValueException extends TokenException {
		private static final long serialVersionUID = 1L;

		private final int valueError;
		private final String tokenValue;
		public static final int EXCESSIVE_DATA = 1;
		public static final int MALFORMED_DATA = 2;

		public TokenValueException(IAT test, String tokenValue, int valueError) {
			super(test);
			this.valueError = valueError;
			this.tokenValue = tokenValue;
		}

		public String getTokenValue() {
			return this.tokenValue;
		}

		@Override
		public String getCaption() {
			if (valueError == EXCESSIVE_DATA) {
				return excessiveTokenDataCaption;
			} else if (valueError == MALFORMED_DATA) {
				return malformedTokenDataCaption;
			}
			return "";
		}
	}

	private static final Logger criticalLogger = LogManager.getLogger("critical");

	@Inject
	IATSessionManager sessionManager;

	@Inject
	IATRepositoryManager iatRepositoryManager;

	@Inject
	SchedulingTaskExecutor scheduler;

	@Named("ServerConfiguration")
	@Inject
	Properties serverConfiguration;

	@Inject
	MyBeanFactory beanFactory;

	@Inject
	Unmarshaller unmarshaller;

	@Inject
	Marshaller marshaller;

	@Inject
	MailService mailService;

	@Inject
	ApplicationEventPublisher publisher;

	@Inject
	ObjectMapper objectMapper;

	@Value("classpath:scripts/aes.js")
	Resource aesJs;

	@Value("${iat.webapp.test-resources-path}")
	private String testResourcesPath;

	@Value("${iat.webapp.path}")
	private String webappPath;

	@Value("${iat.webapp.invalid-token-name-caption}")
	public String invalidTokenNameCaption;

	@Value("${iat.webapp.token-not-supplied-caption}")
	public String tokenNotSuppliedCaption;

	@Value("${iat.webapp.excessive-token-data-caption}")
	public String excessiveTokenDataCaption;

	@Value("${iat.webapp.malformed-token-data-caption}")
	public String malformedTokenDataCaption;

	@GetMapping(value = "", params = { "IATName", "ClientID" }, produces="text/html")
	public ModelAndView startIATAdmin(@RequestParam(name = "IATName") String iatName,
			@RequestParam(name = "ClientID") Long clientId,
			@RequestParam(name = "HTTP_REFERER", required = false, defaultValue = "-") String httpReferer,
			@RequestParam Map<String, String> allParams, HttpServletRequest request) throws TokenException {
		IATSession sess = sessionManager.createSession();
		var test = iatRepositoryManager.getIATByNameAndClientID(iatName, clientId);
		if (test == null) {
			sessionManager.destroySession(sess.getId());
			ModelAndView mv = new ModelAndView(SessionProperties.GENERAL_ERROR);
			mv.addObject(SessionProperties.TITLE, SessionProperties.NO_SUCH_TEST);
			mv.addObject(SessionProperties.CAPTION, SessionProperties.NO_SUCH_TEST);
			mv.addObject(SessionProperties.PAGE, SessionProperties.NO_SUCH_TEST_PAGE);
			mv.addObject(SessionProperties.TEST_NAME_LABEL, iatName);
			mv.addObject(SessionProperties.TEST_LINK, request.getRequestURI());
			return mv;
		}
		Client c = test.getUser().getClient();
		if (c.isFrozen()) {
			sessionManager.destroySession(sess.getId());
			ModelAndView mv = new ModelAndView(SessionProperties.GENERAL_ERROR);
			mv.addObject(SessionProperties.TITLE, SessionProperties.CLIENT_FROZEN);
			mv.addObject(SessionProperties.CAPTION, SessionProperties.CLIENT_FROZEN);
			mv.addObject(SessionProperties.PAGE, SessionProperties.CLIENT_FROZEN_PAGE);
			sessionManager.destroySession(sess.getId());
			return mv;
		}
		if (!iatRepositoryManager.debitAdministration(test)) {
			sessionManager.destroySession(sess.getId());
			ModelAndView mv = new ModelAndView(SessionProperties.GENERAL_ERROR);
			mv.addObject(SessionProperties.TITLE, SessionProperties.NO_ADMINISTRATIONS);
			mv.addObject(SessionProperties.CAPTION, SessionProperties.NO_ADMINISTRATIONS);
			mv.addObject(SessionProperties.PAGE, SessionProperties.NO_ADMINISTRATIONS_PAGE);
			sessionManager.destroySession(sess.getId());
			return mv;
		}
		Long adminId = null;
		if (test.getTokenType() != TokenType.NONE) {
			String encTokenVal = allParams.get(test.getTokenName());
			try {
				if (encTokenVal == null)
					throw new NoTokenException(test);
				String tokenVal = URLDecoder.decode(encTokenVal, StandardCharsets.UTF_8);
				byte[] tokenData = checkToken(test, tokenVal);
				sess.setAttribute(SessionProperties.TOKEN_VALUE, tokenVal);
				adminId = iatRepositoryManager.createAdminTimer(test, sess.getId(), tokenData);
			} catch (TokenException ex) {
				sessionManager.destroySession(sess.getId());
				throw ex;
			}
		} else {
			adminId = iatRepositoryManager.createAdminTimer(test, sess.getId());
		}
		sess.setAttribute(SessionProperties.TEST, test);
		sess.setAttribute(SessionProperties.ADMIN_ID, adminId);
		sess.setAttribute(SessionProperties.HTTP_REFERER, httpReferer);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put(SessionProperties.IAT_SESSION_ID, sess.getId());
		List<Long> segmentList = iatRepositoryManager.getTestElems(test);
		sess.setAttribute(SessionProperties.SEGMENT_LIST, segmentList);
		sess.setAttribute(SessionProperties.TEST_SEGMENT, segmentList.get(0));
		sess.setAttribute(SessionProperties.ADMIN_PHASE, 0);
		model.put(SessionProperties.TEST, test);
		model.put(SessionProperties.CLIENT_ID, test.getClient().getClientId());
		model.put(SessionProperties.TEST_SEGMENT, segmentList.get(0));
		model.put(SessionProperties.ADMIN_PHASE, 0);
		if (segmentList.size() == 1) {
			model.put(SessionProperties.LAST_ADMIN_PHASE, "true");
		} else {
			model.put(SessionProperties.LAST_ADMIN_PHASE, "false");
		}
		return new ModelAndView("Admin/" + segmentList.get(0).toString(), model);
	}

	public String fetchToken(IAT test, HttpServletRequest request) {
		TokenType tokenType = test.getTokenType();
		String tokenName, tokenValue = null;
		if (tokenType != TokenType.NONE) {
			tokenName = test.getTokenName();
			tokenValue = request.getParameter(tokenName);
		}
		return tokenValue;
	}

	public String buildTestLink(IAT test) {
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		fmt.format("%s%s%s%s%d", webappPath, "?IATName=", test.getTestName(), "&ClientID=",
				test.getClient().getClientId());
		fmt.close();
		return sb.toString();
	}

	public String buildTestLink(IAT test, String tokenValue) {
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		fmt.format("%s%s%s%s%d%s%s", webappPath, "?IATName=", test.getTestName(), "&ClientID=",
				test.getClient().getClientId(), test.getTokenName(), tokenValue);
		fmt.close();
		return sb.toString();
	}

	private byte[] checkToken(IAT test, String tokenValue)
			throws NoTokenException, InvalidTokenNameException, TokenValueException {
		var tokenType = test.getTokenType();
		Pattern tokenPat;
		if (tokenType == TokenType.VALUE) {
			byte[] tokenData = tokenValue.getBytes(StandardCharsets.UTF_8);
			if (tokenData.length >= 1024) {
				throw new TokenValueException(test, tokenValue, TokenValueException.EXCESSIVE_DATA);
			}
			return tokenData;
		} else if (tokenType == TokenType.BASE_64) {
			tokenPat = Pattern.compile("[0-9A-Za-z/\\+=]+");
			Matcher m = tokenPat.matcher(tokenValue);
			if (!m.matches()) {
				throw new TokenValueException(test, tokenValue, TokenValueException.MALFORMED_DATA);
			} else {
				Base64.Decoder decoder = Base64.getDecoder();
				byte[] tokenData = decoder.decode(tokenValue);
				if (tokenData.length > 1024) {
					throw new TokenValueException(test, tokenValue, TokenValueException.EXCESSIVE_DATA);
				}
				return tokenData;
			}
		}
		return null;
	}

	public ModelAndView completeTest(IATSession sess) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		IAT test = (IAT) sess.getAttribute(SessionProperties.TEST);
		if (sess.checkAttribute("UniqueResponse")) {
			UniqueResponse ur = (UniqueResponse) sess.getAttribute("UniqueResponse");
			ur.setTaken(false);
			ur.setConsumed(true);
			iatRepositoryManager.updateUniqueResponse(ur);
		}
		String tokenName = (String) sess.getAttribute(SessionProperties.TOKEN_NAME);
		String tokenValue = (String) sess.getAttribute(SessionProperties.TOKEN_VALUE);
		String redirect = null;
		if (test.getTokenType() == TokenType.NONE)
			redirect = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString()).build().toString();
		else
			redirect = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString())
					.queryParam(tokenName, tokenValue).build().toString();
		WebSocketSession ws = (WebSocketSession) sess.getAttribute("WebSocket");
		try {
			ws.sendMessage(new TextMessage("{ \"result\" : \"" + redirect + "\""));
		} catch (java.io.IOException ex) {
			criticalLogger.error(ex);
		} finally {
			publisher.publishEvent(new WebSocketEvent(ws.getId(), WebSocketEventType.CLOSE_SOCKET));
		}
		this.sessionManager.destroySession(sess.getId());
		return null;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "", params = { "IATName", "ClientID", "target=adminV2", "!ABORT" })
	public ModelAndView submitIATAdminV2(@RequestParam("IATName") String iatName,
			@RequestParam("ClientID") long clientId, @RequestParam("TestSegment") Long testSegmentID,
			@RequestParam("NumItems") int numItems, @RequestParam("IATSESSIONID") String sessId,
			@RequestParam(value = "corrupted", required = false, defaultValue = "false") String corrupted,
			@RequestParam(value = "HTTP_REFERER", required = false, defaultValue = "-") String reportedHttpReferer,
			@RequestParam Map<String, String> parameterMap) {
		IAT test = iatRepositoryManager.getIATByNameAndClientID(iatName, clientId);
		IATSession sess = this.sessionManager.getSession(sessId);
		if (sess == null) {
			return new ModelAndView(new RedirectView("/IAT/html/AdministrationTimeout.html"));
		}
		String httpReferer = (String) sess.getAttribute(SessionProperties.HTTP_REFERER);
		Long adminID = (Long) sess.getAttribute(SessionProperties.ADMIN_ID);
		TestSegment ts = iatRepositoryManager.getTestSegmentByID(testSegmentID);
		List<Long> segmentList = (List<Long>) sess.getAttribute("SegmentList");
		int adminPhase = (Integer) sess.getAttribute("AdminPhase") + 1;
		boolean lastSegment = adminPhase == segmentList.size();
		iatRepositoryManager.refreshAdminTimer(adminID);
		if (ts.getElemName().equals(iatName.replace("[^A-Za-z0-9_\\-]", ""))) {
			IATResultRecorder irr = beanFactory.IATResultRecorder(ts, adminID, numItems, parameterMap,
					segmentList.size() == adminPhase);
			irr.run();
		} else {
			SurveyResultRecorder srr = beanFactory.surveyResultRecorder(ts, adminID, numItems, parameterMap,
					segmentList.size() == adminPhase);
			srr.run();
			if (srr.getSpecifierValues() != null) {
				sess.setAttribute(SessionProperties.SPECIFIER_VALUES, srr.getSpecifierValues());
			}
		}
		if (lastSegment) {
			return completeTest(sess);
		}

		Map<String, Object> model = new HashMap<>();
		sess.setAttribute(SessionProperties.TEST_SEGMENT, segmentList.get(adminPhase));
		sess.setAttribute(SessionProperties.ADMIN_PHASE, adminPhase);
		if (adminPhase == segmentList.size() - 1) {
			model.put("LastAdminPhase", "true");
		} else {
			model.put("LastAdminPhase", "false");
		}
		model.put("AdminPhase", adminPhase);
		model.put("IATSESSIONID", sess.getId());
		model.put("Test", test);
		model.put("ClientID", clientId);
		model.put("TestSegment", segmentList.get(adminPhase));
		model.put("referer", httpReferer);
		return new ModelAndView("Admin/" + segmentList.get(adminPhase).toString(), model);
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "", params = { "IATName", "ClientID", "!target", "!ABORT", "NumItems" })
	public ModelAndView submitIATAdminV1(@RequestParam(value = "IATName") String iatName,
			@CookieValue(value = "TestSegment", required = true) Long administeredElemID,
			@RequestParam(value = "ClientID") long clientID,
			@CookieValue(value = "IATSESSIONID", required = true) String sessId,
			@RequestParam(value = "NumItems", required = true) int numItems,
			@CookieValue(value = "AdminPhase") int submittedAdminPhase,
			@CookieValue(value = "CurrentIATID", required = false, defaultValue = "-1") long iatId,
			@RequestParam Map<String, String> parameterMap) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		IAT test = iatRepositoryManager.getIATByNameAndClientID(iatName, clientID);
		IATSession sess = sessionManager.getSession(sessId);
		if (sess == null) {
			return new ModelAndView(new RedirectView("/IAT/html/AdministrationTimeout.html"));
		}
		String httpReferer = (String) sess.getAttribute("HTTP_REFERER");
		Long adminID = (Long) sess.getAttribute("AdminID");
		iatRepositoryManager.refreshAdminTimer(adminID);
		int adminPhase = (Integer) sess.getAttribute("AdminPhase") + 1;
		if (isAdminCorrupted(request.getCookies(), test, adminPhase, RequestMethod.POST)) {
			ModelAndView mv = new ModelAndView("AbortTest");
			mv.addObject(SessionProperties.HTTP_REFERER, httpReferer);
		}

		TestSegment ts = iatRepositoryManager.getTestSegmentByID(administeredElemID);
		List<Long> segmentList = (List<Long>) sess.getAttribute("SegmentList");
		if (ts.getElemName().equals(iatName.replace("[^A-Za-z0-9_\\-]", ""))) {
			IATResultRecorder irr = beanFactory.IATResultRecorder(ts, adminID, numItems, parameterMap,
					segmentList.size() == adminPhase);
			this.scheduler.submit(irr);
		} else {
			SurveyResultRecorder srr = beanFactory.surveyResultRecorder(ts, adminID, numItems, parameterMap,
					segmentList.size() == adminPhase);
			srr.run();
			if (srr.getSpecifierValues() != null) {
				sess.setAttribute("SpecifierValues", srr.getSpecifierValues());
			}
		}
		if (adminPhase == segmentList.size()) {
			return completeTest(sess);
		}

		Map<String, Object> model = new HashMap<>();
		sess.setAttribute("TestSegment", segmentList.get(adminPhase));
		sess.setAttribute("AdminPhase", adminPhase);
		if (adminPhase == segmentList.size() - 1) {
			model.put("LastAdminPhase", "true");
		} else {
			model.put("LastAdminPhase", "false");
		}
		model.put("AdminPhase", adminPhase);
		model.put("IATSESSIONID", sess.getId());
		model.put("Test", test);
		model.put("ClientID", clientID);
		model.put("TestSegment", segmentList.get(adminPhase));
		model.put("referer", request.getHeader("referer"));
		return new ModelAndView("Admin/" + segmentList.get(adminPhase).toString(), model);
	}

	@PostMapping(value = "", params = { "IATName", "ClientID", "ABORT", "IATSESSIONID", "target=adminV2", })
	public ModelAndView abortTest(@RequestParam("IATName") String IATName, @RequestParam("ClientID") Long clientID,
			@RequestParam("IATSESSIONID") String sessId, @RequestParam("HTTP_REFERER") String referer,
			@RequestParam("TestURL") String restartLink) {
		this.sessionManager.destroySession(sessId);
		ModelAndView mv = new ModelAndView("AbortTest");
		mv.addObject("restartLink", restartLink);
		mv.addObject("referer", referer);
		return mv;
	}

	@RequestMapping(value = "/resources/{clientId}/{testName}/{resourceId}")
	public ResponseEntity<byte[]> getTestResource(@PathVariable(name = "clientId", required = true) Long clientId,
			@PathVariable(name = "testName", required = true) String testName,
			@PathVariable(name = "resourceId", required = true) Long resourceId) {
		var test = iatRepositoryManager.getIATByNameAndClientID(testName, clientId);
		var res = iatRepositoryManager.getTestResource(test, resourceId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(res.getMimeType()));
		return new ResponseEntity<>(res.getResourceBytes(), headers, HttpStatus.OK);
	}

	@GetMapping(value = "/Ajax/DynamicSpecifiers.json", produces = "application/json")
	@ResponseBody
	public Callable<Object> getDynamicSpecifiers(
			@CookieValue(value = "IATSESSIONID", required = false, defaultValue = "-") String cookieSessId,
			@RequestHeader(value = "IATSESSIONID", required = false, defaultValue = "-") String headerSessId) {
		return () -> {
			IATSession sess;
			if (cookieSessId.equals("-")) {
				sess = this.sessionManager.getSession(headerSessId);
			} else {
				sess = this.sessionManager.getSession(cookieSessId);
			}
			return sess.getAttribute("SpecifierValues");
		};
	}


	@PostMapping(value = "/Ajax/KeySet", consumes = "text/json", produces = "text/json")
	@ResponseBody
	public String getKeyJSON(@RequestHeader("IATSESSIONID") String sessId,
			@RequestBody List<String> encWords) throws java.io.IOException {
			IATSession sess = this.sessionManager.getSession(sessId);
			var rsaData = (RSAKeyData)sess.getAttribute("RSA");
			var cipherWords = new ArrayList<BigInteger>();
			for (var eWord: encWords) {
				byte[] kWord = rsaData.decrypt(new BigInteger(eWord, 16));
				byte[] zpkWord = new byte[kWord.length + 1];
				zpkWord[0] = 0;
				System.arraycopy(kWord, 0, zpkWord, 1, kWord.length);
				cipherWords.add(new BigInteger(zpkWord));
			}
			Long tsID = (Long) sess.getAttribute("TestSegment");
			TestSegment ts = iatRepositoryManager.getTestSegmentByID(tsID);
			StringReader sReader = new StringReader(ts.getJsKeyXml());
			StreamSource sSource = new StreamSource(sReader);
			JSKeys keySet = beanFactory.jsKeys((JSKeys)unmarshaller.unmarshal(sSource));
			return keySet.getEncryptedKeySet(cipherWords).getKeySetJSON();
	}

	@PostMapping(value = "/Ajax/AES", consumes = "text/xml", produces = "text/xml")
	@ResponseBody
	public ResponseEntity<AjaxResponse> getAesFile() throws java.io.IOException {
			ByteBuffer buff = ByteBuffer.allocate((int) aesJs.contentLength());
			int bytesRead = 0;
			while (bytesRead < aesJs.contentLength())
				bytesRead += aesJs.readableChannel().read(buff);
			return new ResponseEntity<>(new AjaxResponse(buff.asCharBuffer().toString()), HttpStatus.OK);
	}

	@GetMapping(value = "/Ajax/AES", produces = "text/javascript")
	@ResponseBody
	public ResponseEntity<String> getAes(@RequestParam("IATSESSIONID") String sessId) {
		try {
			var sess = sessionManager.getSession(sessId);
			var test = (IAT)sess.getAttribute(SessionProperties.TEST);
			return new ResponseEntity<>(test.getAESCode(), HttpStatus.OK);
		}
		catch (Exception ex) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "/Ajax/RSA", produces = "text/json")
	@ResponseBody
	public RSAKeyData getPublicKey(@RequestHeader("IATSESSIONID") String iatSessionId) {
		IATSession sess = this.sessionManager.getSession(iatSessionId);
		RSAKeyData keyData = iatRepositoryManager.getRandomRSAData();
		sess.setAttribute("RSA", keyData);
		return keyData;
	}

	@GetMapping(value = "/Ajax/Code", produces = "text/json")
	@ResponseBody
	public List<EncCodeLine> getCode(@RequestHeader(value = "IATSESSIONID") String sessId) {
		IATSession sess = this.sessionManager.getSession(sessId);
		Long tsID = (Long) sess.getAttribute("TestSegment");
		return iatRepositoryManager.getOrderedCodeLines(tsID);
	}

	@GetMapping(value = "/Ajax/EncryptedCode.xml", produces = "text/xml")
	@ResponseBody
	public AjaxResponse getEncryptedCode(@RequestParam("iatsession") String iatSessionId) {
		IATSession sess = this.sessionManager.getSession(iatSessionId);
		Long tsId = (Long) sess.getAttribute("TestSegment");
		return new AjaxResponse(iatRepositoryManager.getOrderedCodeLines(tsId));
	}

	@GetMapping(value = "/Ajax/VerifyUniqueResponse", consumes = "text/plain", produces = "text/plain")
	@ResponseBody
	public String verifyUniqueResponse(@RequestHeader("IATSESSIONID") String sessId, @RequestBody String response) {
		IATSession sess = this.sessionManager.getSession(sessId);
		if (sess == null) {
			return "abort";
		}
		Long adminID = (Long) sess.getAttribute("AdminID");
		if (adminID == null) {
			return "abort";
		}
		AdminTimer admin = iatRepositoryManager.getAdminTimerById(adminID);
		IAT test = admin.getTest();
		UniqueResponseItem uri = test.getUniqueResponseItem();
		UniqueResponse ur = (UniqueResponse) sess.getAttribute("UniqueResponse");
		if (ur != null) {
			if (ur.getValue().equals(URLDecoder.decode(response, StandardCharsets.UTF_8))) {
				return "success";
			} else {
				ur.free();
				iatRepositoryManager.updateUniqueResponse(ur);
			}
		}
		ur = iatRepositoryManager.getUniqueResponse(uri, URLDecoder.decode(response, StandardCharsets.UTF_8));
		if (ur == null) {
			return "invalid";
		}
		if (ur.getTaken()) {
			return "taken";
		}
		if (ur.getConsumed()) {
			return "consumed";
		}
		ur.setAdminTimer(admin);
		ur.setTaken(true);
		sess.setAttribute("UniqueResponse", ur);
		iatRepositoryManager.updateUniqueResponse(ur);
		return "success";
	}

	@Scheduled(initialDelay = 30_000L, fixedDelay = 5_000L)
	private void checkForCompletedTests() {
		Map<AdminTimer, List<TestResultFragment>> adminTimerMap = iatRepositoryManager.getCompletedResultSets();
		iatRepositoryManager.deleteAdminTimers(adminTimerMap.keySet());
		if (adminTimerMap.isEmpty()) {
			return;
		}
		adminTimerMap.keySet().forEach(timer -> {
			try {
				if (timer.getUniqueResponse() != null) {
					UniqueResponse ur = timer.getUniqueResponse();
					ur.setTaken(false);
					ur.setAdminTimer(null);
					ur.setConsumed(true);
					iatRepositoryManager.updateUniqueResponse(ur);
				}
				ResultTOC toc = new ResultTOC();
				long offset = 0;
				ByteArrayOutputStream bResultOut = new ByteArrayOutputStream();
				for (TestResultFragment resultFrag : adminTimerMap.get(timer)) {
					byte[] resultBytes = resultFrag.getResultFragment();
					byte[] key = resultFrag.getCipher();
					byte[] iv = resultFrag.getIV();
					bResultOut.write(key);
					bResultOut.write(iv);
					bResultOut.write(resultBytes);
					ResultTOCEntry tocEntry = new ResultTOCEntry(offset, key.length, offset + key.length, iv.length,
							offset + key.length + iv.length, resultBytes.length);
					offset += key.length + iv.length + resultBytes.length;
					toc.addEntry(tocEntry);
				}
				StringWriter sWriter = new StringWriter();
				StreamResult sResult = new StreamResult(sWriter);
				marshaller.marshal(toc, sResult);
				bResultOut.flush();
				iatRepositoryManager.storeResultSet(timer.getTest(), sWriter.toString(), bResultOut.toByteArray(),
						timer.getTesteeToken());
				sessionManager.destroySession(timer.getIATSESSIONID());
			} catch (java.io.IOException ex) {
				criticalLogger.error("Error recording result set", ex);
			}
		});
		adminTimerMap.clear();
	}

	public BinaryOperator<Cookie> getCookieComparator(final String cookieName) {
		return (c1, c2) -> {
			if (c1.getName().equals(cookieName)) {
				return c1;
			}
			if (c2.getName().equals(cookieName)) {
				return c2;
			}
			return null;
		};
	}

	public boolean isAdminCorrupted(Cookie[] cookies, IAT test, int adminPhase, RequestMethod verb) {
		if ((AdminVersion.compare(new AdminVersion(test.getVersion()), new AdminVersion("iat-1.0.4")) < 0)
				|| (cookies == null)) {
			return false;
		}
		String corruptedCookieName = Long.toString(test.getClient().getClientId()) + "-" + test.getTestName()
				+ "-corrupt";
		if (Arrays.stream(cookies)
				.anyMatch(c -> c.getName().equals(corruptedCookieName) || c.getName().equals("corrupted"))) {
			return true;
		}
		if (verb != RequestMethod.POST) {
			return false;
		}
		Optional<Cookie> optCookie = Arrays.stream(cookies).reduce(getCookieComparator("AdminPhase"));
		if (!optCookie.isPresent()) {
			return true;
		}
		int reportedAdminPhase = Integer.parseInt(optCookie.get().getValue());
		optCookie = Arrays.stream(cookies).reduce(getCookieComparator("CurrentIATID"));
		if (!optCookie.isPresent()) {
			return true;
		}
		long reportedTestId = Long.parseLong(optCookie.get().getValue());
		if (reportedTestId != test.getId()) {
			return false;
		} else if (adminPhase * 2 != reportedAdminPhase) {
			return true;
		}
		return false;
	}

	public ModelAndView buildInvalidAdminView(IAT test, String sessId, String tokenValue, String httpReferer,
			boolean isCorrupt) {
		TestAbortParams params = new TestAbortParams();
		params.setClientId(Long.toString(test.getClient().getClientId()));
		if (tokenValue != null) {
			params.setTokenName(test.getTokenName());
			params.setTokenValue(tokenValue);
		}
		params.setCorruptAdministration(isCorrupt);
		params.setMultipleAdministrations(!isCorrupt);
		params.setHttpReferer(httpReferer);
		if (AdminVersion.compare(new AdminVersion(test.getVersion()), new AdminVersion("iat-1.0.4")) < 0) {
			params.setCorruptCookieName(
					Long.toString(test.getClient().getClientId()) + "-" + test.getTestName() + "-" + "corrupt");
		} else {
			params.setCorruptCookieName("corrupted");
		}
		params.setSessionId(sessId);
		params.setServerPath(serverConfiguration.getProperty("path"));
		params.setClientId(Long.toString(test.getClient().getClientId()));
		params.setIatName(test.getTestName());
		params.setVersion(test.getVersion());
		ModelAndView mv = new ModelAndView("AbortTest");
		mv.addObject("abortParams", params);
		return mv;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(java.io.IOException.class)
	void handleIOException(java.io.IOException ex) {
		criticalLogger.error("Error in admin controller", ex);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	void handleGenericException(Exception ex) {
		criticalLogger.error("Error in admin controller", ex);
	}
}
