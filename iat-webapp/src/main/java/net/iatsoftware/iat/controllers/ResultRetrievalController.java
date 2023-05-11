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

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.messaging.ResultRequest;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Named;

@Controller
@ClientControllerAnnotation
@RequestMapping("/RetrieveResults")
public class ResultRetrievalController {

    @Inject
    IATRepositoryManager repositoryManager;
    @Inject
    @Named("ServerConfiguration")
    Properties serverConfiguration;

    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final Logger logger = LogManager.getLogger();

    @PostMapping(value = "", consumes = "text/json")
    @ResponseBody
    public ResponseEntity<FileSystemResource> downloadResults(@RequestBody ResultRequest request) throws java.net.URISyntaxException {
        IAT test = repositoryManager.getIATByNameAndClientID(request.getTestName(), request.getClientId());
        byte[] offeredAuthToken = decoder.decode(request.getAuthToken());
        byte[] authToken = test.getResultRetrievalToken();
        if (offeredAuthToken.length != authToken.length) {
            return new ResponseEntity<>((FileSystemResource) null, HttpStatus.BAD_REQUEST);
        }
        for (int ctr = 0; ctr < authToken.length; ctr++) {
            if (offeredAuthToken[ctr] != authToken[ctr]) {
                return new ResponseEntity<>((FileSystemResource) null, HttpStatus.BAD_REQUEST);
            }
        }
        URI resultFileURI = new URI(String.format("%s/%s-%d", serverConfiguration.getProperty("result-data"), 
            test.getTestName(), request.getClientId()));
        return new ResponseEntity<>(new FileSystemResource(Paths.get(resultFileURI)), HttpStatus.OK);
    }

    @Scheduled(initialDelay = 300_000L, fixedDelay = 60_000L)
    private void cleanupResultFiles() {
        List<IAT> expiredIATResults = repositoryManager.getExpiredTestResults(300_000L);
        for (IAT test : expiredIATResults) {
            try {
                Files.delete(Paths.get(new URI(String.format("%s/%s-%d", test.getTestName(), test.getClient().getClientId()))));
                test.setResultRetrievalToken(null);
                test.setResultRetrievalTokenAge(null);
                repositoryManager.updateIAT(test);
            } catch (java.io.IOException | java.net.URISyntaxException ex) {
                logger.error("Error deleting result data file", ex);
            }
        }
    }
}
