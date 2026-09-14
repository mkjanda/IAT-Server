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

import net.iatsoftware.iat.entities.EncryptedResultSet;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.configfile.ConfigFile;
import net.iatsoftware.iat.resultdata.TestResults;
import net.iatsoftware.iat.repositories.ClientRepositoryManager;
import net.iatsoftware.iat.resultdata.ResultSetDescriptor;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Cache;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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
    public static final Cache<String, Long> authTokenCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(2))
            .maximumSize(1000)
            .build();
    private static final Logger logger = LogManager.getLogger();

    @GetMapping(value = "/Results", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<TestResults> downloadResults(@RequestParam("iatName") String iatName, @RequestParam("clientId") long clientId,
            @RequestParam("authToken") Long authToken) throws Exception {
        var client = clientRepositoryManager.getClientById(clientId);        
        var expectedAuthToken = authTokenCache.getIfPresent(client.getProductKey());
        if (expectedAuthToken == null || !expectedAuthToken.equals(authToken))
            return ResponseEntity.badRequest().build();
        var test = repositoryManager.getIATByNameAndClientID(iatName, clientId);
        if (test == null) 
            return ResponseEntity.badRequest().build();
        TestResults testResults = new TestResults();
        var configFileResource = new ByteArrayInputStream(repositoryManager.getTestResource(test, 0L).getResourceBytes());
        var configFile = (ConfigFile)unmarshaller.unmarshal(new StreamSource(configFileResource));
        List<EncryptedResultSet> resultSets = repositoryManager.getResults(clientId, iatName);
        ResultSetDescriptor rsd = new ResultSetDescriptor(test, configFile, test.getDataKey(), resultSets.size()); 
        testResults.setDescriptor(rsd);
        testResults.getEncryptedResultSet().addAll(resultSets);
        return new ResponseEntity<TestResults>(testResults, HttpStatus.OK);
    }

    @GetMapping(value="/ItemSlides")
    public ResponseEntity<byte[]> downloadItemSlides(@RequestParam("testName") String testName, @RequestParam("clientId") long clientId, 
            @RequestParam("authToken") Long authToken) {
        var client = clientRepositoryManager.getClientById(clientId);        
        var expectedAuthToken = ResultRetrievalController.authTokenCache.getIfPresent(client.getProductKey());
        if (expectedAuthToken == null || !expectedAuthToken.equals(authToken))
            return ResponseEntity.badRequest().build();
        var test = repositoryManager.getIATByNameAndClientID(testName, clientId);
        if (test == null) 
            return ResponseEntity.badRequest().build();
        var slideManifest = repositoryManager.getItemSlides(test);
        var outStream = new ByteArrayOutputStream();
        try {
            for (byte[] b  : slideManifest) {
                outStream.write(b);
            }
        } catch (java.io.IOException ex) {
            logger.error("Error writing item slides to output stream", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return new ResponseEntity<>(outStream.toByteArray(), HttpStatus.OK);
    }
}
