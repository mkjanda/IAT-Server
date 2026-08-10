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

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Named;

@Controller
@ClientControllerAnnotation
@RequestMapping("/ItemSlides")
public class ItemSlideDownload {
    private static final Logger log = LogManager.getLogger();
    @Inject
    IATRepositoryManager iatRepositoryManager;
    @Inject
    @Named("ServerConfiguration")
    Properties serverConfiguration;

    @RequestMapping(value = "", method = RequestMethod.GET, params = { "DownloadKey", "ClientID", "IATName" })
    public ResponseEntity<byte[]> downloadSlides(@RequestParam("DownloadKey") String downloadKey,
            @RequestParam("ClientID") long clientID, @RequestParam("IATName") String iatName) {
        IAT test = iatRepositoryManager.getIATByNameAndClientID(iatName, clientID);
        if (test.getItemSlideDownloadKey() == null)
            return null;
        if (!test.getItemSlideDownloadKey().equals(downloadKey))
            return null;
        test.setItemSlideDownloadKey(null);
        var memStream = new ByteArrayOutputStream();
        List<byte[]> slides = iatRepositoryManager.getItemSlides(test);
        final java.io.IOException[] exception = { null };
        slides.stream().forEach(b -> {
            try {
                memStream.write(b);
            } catch (java.io.IOException ex) {
                exception[0] = ex;
            }
        });
        if (exception[0] != null) {
            log.error(exception[0]);
            return new ResponseEntity<>(new byte[1], HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(memStream.toByteArray(), HttpStatus.OK);
    }
}
