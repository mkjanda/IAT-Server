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
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.net.URI;
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
    public ResponseEntity<FileSystemResource> downloadSlides(@RequestParam("DownloadKey") String downloadKey,
            @RequestParam("ClientID") long clientID, @RequestParam("IATName") String iatName) {
        try {
            IAT test = iatRepositoryManager.getIATByNameAndClientID(iatName, clientID);
            if (test.getItemSlideDownloadKey() == null)
                return null;
            if (!test.getItemSlideDownloadKey().equals(downloadKey))
                return null;
            test.setItemSlideDownloadKey(null);
            var slideUri = new URI(String.format("%s/%s.%d.%s.slides", serverConfiguration.getProperty("item-slide-directory"),
                downloadKey, clientID, iatName));
            return new ResponseEntity<>(new FileSystemResource(new File(slideUri).getAbsolutePath()), HttpStatus.OK);
        } catch (java.net.URISyntaxException ex) {
            log.error(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
