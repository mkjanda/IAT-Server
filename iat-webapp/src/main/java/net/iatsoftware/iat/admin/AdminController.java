/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.EncryptedResultSet;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.TestSegment;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.resultdata.ResultSet;
import net.iatsoftware.iat.resultdata.IATResult;
import net.iatsoftware.iat.resultdata.SurveyResult;
import net.iatsoftware.iat.services.MailService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.context.WebApplicationContext;

import java.time.Duration;


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
	WebApplicationContext context;

	@Inject
	IATRepositoryManager iatRepositoryManager;

	@Inject
	SchedulingTaskExecutor scheduler;

	@Named("ServerConfiguration")
	@Inject
	Properties serverConfiguration;

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

	static public final Cache<String, IATSession> sessions = Caffeine.newBuilder()
			.maximumSize(100_000)
			.expireAfterWrite(Duration.ofHours(1))
			.build();

	@GetMapping(value = "", params = { "IATName", "ClientID" }, produces = "text/html")
	public ModelAndView startIATAdmin(@RequestParam(name = "IATName") String iatName,
			@RequestParam(name = "ClientID") Long clientId,
			@RequestHeader(name = "HTTP_REFERER", required = false, defaultValue = "-") String httpReferer,
			@RequestParam Map<String, String> allParams, HttpServletRequest request) throws TokenException {
		var sess = new IATSession(String.valueOf(System.currentTimeMillis()));
		sessions.put(sess.getId(), sess);
		var test = iatRepositoryManager.getIATByNameAndClientID(iatName, clientId);
		if (test == null) {
			sessions.invalidate(sess.getId());
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
			sessions.invalidate(sess.getId());
			ModelAndView mv = new ModelAndView(SessionProperties.GENERAL_ERROR);
			mv.addObject(SessionProperties.TITLE, SessionProperties.CLIENT_FROZEN);
			mv.addObject(SessionProperties.CAPTION, SessionProperties.CLIENT_FROZEN);
			mv.addObject(SessionProperties.PAGE, SessionProperties.CLIENT_FROZEN_PAGE);
			return mv;
		}
		if (!iatRepositoryManager.debitAdministration(test)) {
			sessions.invalidate(sess.getId());
			ModelAndView mv = new ModelAndView(SessionProperties.GENERAL_ERROR);
			mv.addObject(SessionProperties.TITLE, SessionProperties.NO_ADMINISTRATIONS);
			mv.addObject(SessionProperties.CAPTION, SessionProperties.NO_ADMINISTRATIONS);
			mv.addObject(SessionProperties.PAGE, SessionProperties.NO_ADMINISTRATIONS_PAGE);
			return mv;
		}
		sess.setAttribute(SessionProperties.TEST, test);
		sess.setAttribute(SessionProperties.HTTP_REFERER, httpReferer);
		sess.setAttribute(SessionProperties.RESULTS, new ResultSet());
		Map<String, Object> model = new HashMap<String, Object>();
		List<TestSegment> segmentList = iatRepositoryManager.getTestElems(test);
		sess.setAttribute(SessionProperties.SEGMENT_LIST, segmentList);
		sess.setAttribute(SessionProperties.TEST_SEGMENT_ID, segmentList.get(0).getId());
		sess.setAttribute(SessionProperties.ADMIN_PHASE, 0);
		model.put(SessionProperties.IAT_SESSION_ID, sess.getId());
		model.put(SessionProperties.TEST, test);
		model.put(SessionProperties.CLIENT_ID, test.getClient().getClientId());
		model.put(SessionProperties.TEST_SEGMENT_ID, segmentList.get(0).getId());
		model.put(SessionProperties.ADMIN_PHASE, 0);
		model.put(SessionProperties.HTTP_REFERER, httpReferer);
		if (segmentList.size() == 1) {
			model.put(SessionProperties.LAST_ADMIN_PHASE, "true");
		} else {
			model.put(SessionProperties.LAST_ADMIN_PHASE, "false");
		}
		Long id = segmentList.get(0).getId();
		return new ModelAndView("Admin/" + id.toString(), model);
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "", params = { "IATName", "ClientID", "target=adminV2", "!ABORT" })
	public ModelAndView submitIATAdminV2(@RequestParam("IATName") String iatName,
			@RequestParam("ClientID") long clientId, @RequestParam("IATSESSIONID") String sessId,
			@RequestParam Map<String, String> parameterMap) { 
		IATSession sess = sessions.getIfPresent(sessId);
		if (sess == null) {
			var view = new ModelAndView("AdministrationTimeout");
			view.addObject(SessionProperties.HTTP_REFERER, "-");
			return view;
		}
		int adminPhase = (int) sess.getAttribute(SessionProperties.ADMIN_PHASE) + 1;
		var test = (IAT) sess.getAttribute(SessionProperties.TEST);
		String httpReferer = (String) sess.getAttribute(SessionProperties.HTTP_REFERER);
		List<TestSegment> segmentList = (List<TestSegment>) sess.getAttribute(SessionProperties.SEGMENT_LIST);
		var results = (ResultSet) sess.getAttribute(SessionProperties.RESULTS);
		if (segmentList.size() == adminPhase) {
			var iatResults = new IATResult();
			iatResults.parseResults(parameterMap);
			results.setIATResult(iatResults);
			try {
				var encResultSet = new EncryptedResultSet(marshaller, test, results);
				encResultSet.encryptResults();
				iatRepositoryManager.addResultSet(encResultSet);
			} catch (Exception e) {
				criticalLogger.error("Critical error marshalling results", e);
				var mv = new ModelAndView();
				mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
				return mv;
			}
			sessions.invalidate(sess.getId());
			return new ModelAndView(new RedirectView(test.getRedirectOnComplete()));
		} else {
			var surveyResult = new SurveyResult(segmentList.get(adminPhase - 1).getElemName(), parameterMap);
			results.getSurveyResult().add(surveyResult);
		}
		Map<String, Object> model = new HashMap<>();
		sess.setAttribute(SessionProperties.ADMIN_PHASE, adminPhase);
		model.put(SessionProperties.ADMIN_PHASE, adminPhase);
		model.put(SessionProperties.IAT_SESSION_ID, sess.getId());
		model.put(SessionProperties.TEST, test);
		model.put(SessionProperties.CLIENT_ID, clientId);
		model.put(SessionProperties.TEST_SEGMENT_ID, segmentList.get(adminPhase).getId());
		model.put(SessionProperties.HTTP_REFERER, httpReferer);
		Long testSegmentId = segmentList.get(adminPhase).getId();
		return new ModelAndView("Admin/" + testSegmentId.toString(), model);
	}
/*
	@SuppressWarnings("unchecked")
	@PostMapping(value = "", params = { "IATName", "ClientID", "!target", "!ABORT", "NumItems" })
	public ModelAndView submitIATAdminV1(@RequestParam(value = "IATName") String iatName,
			@CookieValue(value = "TestSegment", required = true) Long administeredElemID,
			@RequestParam(value = "ClientID") long clientID,
			@CookieValue(value = "IATSESSIONID", required = true) String sessId,
			@CookieValue(value = "AdminPhase") int submittedAdminPhase,
			@CookieValue(value = "CurrentIATID", required = false, defaultValue = "-1") long iatId,
			@RequestParam Map<String, String> parameterMap) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		IAT test = iatRepositoryManager.getIATByNameAndClientID(iatName, clientID);
		IATSession sess = sessions.getIfPresent(sessId);
		if (sess == null) {
			return new ModelAndView("AdministrationTimeout");
		}
		var segmentList = (List<TestSegment>) sess.getAttribute(SessionProperties.SEGMENT_LIST);
		var results = (ResultSet) sess.getAttribute(SessionProperties.RESULTS);
		long adminPhase = (long) sess.getAttribute("AdminPhase") + 1;
		if (segmentList.get(0).getElemName().equals(iatName.replace("[^A-Za-z0-9_\\-]", ""))) {
			var iatResults = new IATResult();
			iatResults.parseResults(parameterMap);
			results.setIATResult(iatResults);
		} else {
			var surveyResult = new SurveyResult(segmentList.get(0).getElemName(), parameterMap);
			results.getSurveyResult().add(surveyResult);
		}
		if (segmentList.size() == 0) {
			iatRepositoryManager.saveResultSet(test, results);	
			sessions.invalidate(sess.getId());
			return new ModelAndView(new RedirectView(test.getRedirectOnComplete()));
		}

		Map<String, Object> model = new HashMap<>();
		sess.setAttribute(SessionProperties.ADMIN_PHASE, adminPhase);
		if (adminPhase == segmentList.size() - 1) {
			model.put("LastAdminPhase", "true");
		} else {
			model.put("LastAdminPhase", "false");
		}
		model.put("AdminPhase", adminPhase);
		model.put("IATSESSIONID", sess.getId());
		model.put("Test", test);
		model.put("ClientID", clientID);
		model.put("TestSegmentId", segmentList.get(0).getId());
		model.put("referer", request.getHeader("referer"));
		Long testSegmentId = segmentList.get(0).getId();
		segmentList.removeFirst();
		return new ModelAndView("Admin/" + testSegmentId.toString(), model);
	}

	
	 * @PostMapping(value = "/Ajax/KeySet", consumes = "text/json", produces =
	 * "text/json")
	 * 
	 * @ResponseBody
	 * public String getKeyJSON(@RequestHeader("IATSESSIONID") String sessId,
	 * 
	 * @RequestBody List<String> encWords) throws java.io.IOException {
	 * IATSession sess = this.sessionManager.getSession(sessId);
	 * var rsaData = (RSAKeyData)sess.getAttribute("RSA");
	 * var cipherWords = new ArrayList<BigInteger>();
	 * for (var eWord: encWords) {
	 * byte[] kWord = rsaData.decrypt(new BigInteger(eWord, 16));
	 * byte[] zpkWord = new byte[kWord.length + 1];
	 * zpkWord[0] = 0;
	 * System.arraycopy(kWord, 0, zpkWord, 1, kWord.length);
	 * cipherWords.add(new BigInteger(zpkWord));
	 * }
	 * Long tsID = (Long) sess.getAttribute("TestSegment");
	 * TestSegment ts = iatRepositoryManager.getTestSegmentByID(tsID);
	 * StringReader sReader = new StringReader(ts.getJsKeyXml());
	 * StreamSource sSource = new StreamSource(sReader);
	 * JSKeys keySet = beanFactory.jsKeys((JSKeys)unmarshaller.unmarshal(sSource));
	 * return keySet.getEncryptedKeySet(cipherWords).getKeySetJSON();
	 * }
	 
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
			var test = (IAT) sess.getAttribute(SessionProperties.TEST);
			return new ResponseEntity<>(test.getAESCode(), HttpStatus.OK);
		} catch (Exception ex) {
			return ResponseEntity.internalServerError().build();
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
*/
	public ModelAndView buildInvalidAdminView(IAT test, String sessId, String tokenValue, String httpReferer,
			boolean isCorrupt) {
		TestAbortParams params = new TestAbortParams();
		params.setClientId(Long.toString(test.getClient().getClientId()));
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
