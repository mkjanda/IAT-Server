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

import net.iatsoftware.iat.config.IatConfigurationProperties;
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
import javax.inject.Inject;

@Controller
@ClientControllerAnnotation
@RequestMapping("/ItemSlideDownload")
public class ItemSlideDownload {
    private static final Logger log = LogManager.getLogger();
    @Inject
    IATRepositoryManager iatRepositoryManager;
    @Inject
    IatConfigurationProperties serverConfiguration;

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
            return new ResponseEntity<>(new FileSystemResource(
                    new File(serverConfiguration.getItemSlideFileUri(downloadKey, clientID, iatName))
                            .getAbsolutePath()),
                    HttpStatus.OK);
        } catch (java.net.URISyntaxException ex) {
            log.error(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
