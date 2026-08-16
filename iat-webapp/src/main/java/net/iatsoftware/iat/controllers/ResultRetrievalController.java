/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.controllers;

/**
 *
 * @author michael
 *
 * 
 */

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.ResultSet;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.resultdata.ResultSetEntry;
import net.iatsoftware.iat.resultdata.ResultTOC;
import net.iatsoftware.iat.resultdata.TestResults;
import net.iatsoftware.iat.repositories.ClientRepositoryManager;
import net.iatsoftware.iat.resultdata.ResultSetDescriptor;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.io.StringWriter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

@Controller
@ClientControllerAnnotation
@RequestMapping("/Download")
public class ResultRetrievalController {
    @Inject Marshaller marshaller;
    @Inject Unmarshaller unmarshaller;
    @Inject IATRepositoryManager repositoryManager;
    @Inject ClientRepositoryManager clientRepositoryManager;
    @Inject
    @Named("ServerConfiguration")
    Properties serverConfiguration;

    private static final Logger logger = LogManager.getLogger();

    @GetMapping(value = "")
    @ResponseBody
    public ResponseEntity<byte[]> downloadResults(@RequestParam("TestName") String testName, @RequestParam("ClientId") long clientId,
            @RequestParam("AuthToken") String authToken) throws Exception {
        if (!clientRepositoryManager.authTokenValid(clientId, authToken)) {
            return new ResponseEntity<>((byte[]) null, HttpStatus.BAD_REQUEST);
        }
        IAT test = repositoryManager.getIATByNameAndClientID(testName, clientId);
        if (test == null) {
            return new ResponseEntity<>((byte[]) null, HttpStatus.BAD_REQUEST);
        }
        TestResults testResults = new TestResults();
        var configFileResource = repositoryManager.getTestResource(test, 0L);
        var configFileString = new String(configFileResource.getResourceBytes(), java.nio.charset.StandardCharsets.UTF_8);
        ResultSetDescriptor rsd = new ResultSetDescriptor(); 

        rsd.setTest(test);
        rsd.setConfigFile(configFileString);
        rsd.setNumResults((int)repositoryManager.getNumResults(clientId, testName));
        rsd.setRSAKey(test.getDataKey());

        testResults.setDescriptor(rsd);
        List<ResultSet> resultSets = repositoryManager.getResults(clientId, testName);
        testResults.setNumResultSets(resultSets.size());
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        for (ResultSet rs : resultSets) {
            StringReader sReader = new StringReader(rs.getToc());
            StreamSource sSource = new StreamSource(sReader);
            ResultTOC toc = (ResultTOC) unmarshaller.unmarshal(sSource);
            ResultSetEntry rse = new ResultSetEntry();
            rse.setTOC(toc);
            rse.setAdminTime(df.format(rs.getAdminTime().getTime()));
            rse.setResultData(Base64.getEncoder().encodeToString(rs.getResults()));
            rse.setResultId(rs.getId());
            testResults.getResultSet().add(rse);
        }
        StringWriter sWriter = new StringWriter();
        var bOut = new ByteArrayOutputStream();
        StreamResult sResult = new StreamResult(sWriter);
        marshaller.marshal(testResults, sResult);
        bOut.write(sWriter.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
        test.setResultRetrievalTokenAge(Calendar.getInstance());
        repositoryManager.updateIAT(test);
        return new ResponseEntity<byte[]>(bOut.toByteArray(), HttpStatus.OK);
    }

    @GetMapping(value="/ItemSlides")
    public ResponseEntity<byte[]> downloadItemSlides(@RequestParam("TestName") String testName, @RequestParam("ClientId") long clientId, 
            @RequestParam("AuthToken") String authToken) {
        if (!clientRepositoryManager.authTokenValid(clientId, authToken)) {
            return new ResponseEntity<>((byte[]) null, HttpStatus.BAD_REQUEST);
        }
        var test = repositoryManager.getIATByNameAndClientID(testName, clientId);
        if (test == null) {
            return new ResponseEntity<>((byte[]) null, HttpStatus.BAD_REQUEST);
        }
        var slideManifest = repositoryManager.getItemSlides(test);
        var outStream = new ByteArrayOutputStream();
        try {
            for (byte[] b  : slideManifest) {
                outStream.write(b);
            }
        } catch (java.io.IOException ex) {
            logger.error("Error writing item slides to output stream", ex);
            return new ResponseEntity<>((byte[]) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(outStream.toByteArray(), HttpStatus.OK);
    }

    @Scheduled(initialDelay = 300_000L, fixedDelay = 60_000L)
    private void cleanupResultFiles() {
        List<IAT> expiredIATResults = repositoryManager.getExpiredTestResults(300_000L);
        for (IAT test : expiredIATResults) {
            try {
                Files.delete(Paths.get(new URI(String.format("%s/%s-%d", serverConfiguration.getProperty("result-data"), test.getTestName(), test.getClient().getClientId()))));
                test.setResultRetrievalToken(null);
                test.setResultRetrievalTokenAge(null);
                repositoryManager.updateIAT(test);
            } catch (java.io.IOException | java.net.URISyntaxException ex) {
                logger.error("Error deleting result data file", ex);
            }
        }
    }
}
