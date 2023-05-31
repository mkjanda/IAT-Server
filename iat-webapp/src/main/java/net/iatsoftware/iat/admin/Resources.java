package net.iatsoftware.iat.admin;

import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;

@Controller
@RequestMapping("/resource")
public class Resources {
    public static final String HEADER = "HEADER";
    public static final String SCRIPT = "SCRIPT";
    private static final Logger logger = LogManager.getLogger("critical");

    @Inject IATRepositoryManager repository;

    @GetMapping("/{clientId}/{testName}/{resourceId}")
    public ResponseEntity<byte[]> getScript(@PathVariable(name="clientId") long clientId, @PathVariable("testName") String testName,
            @PathVariable(name="resourceId") Long resourceId) {
        var iat = repository.getIATByNameAndClientID(testName, clientId);
        var res = repository.getTestResource(iat, resourceId);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(res.getMimeType()));
        return new ResponseEntity<>(res.getResourceBytes(), headers, HttpStatus.OK);
    }

    @GetMapping("/{clientId}/{testName}/{resourceId}/img")
    public ResponseEntity<byte[]> getImage(@PathVariable(name="clientId") long clientId, @PathVariable("testName") String testName,
            @PathVariable(name="resourceId") int resourceId) {
        var iat = repository.getIATByNameAndClientID(testName, clientId);
        var res = repository.getTestImage(iat, resourceId);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(res.getMimeType()));
        return new ResponseEntity<>(res.getResourceBytes(), headers, HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, jakarta.persistence.NoResultException.class,  jakarta.persistence.NonUniqueResultException.class})
    public void handleException(Exception ex) {
        logger.error(ex);
    }
}
