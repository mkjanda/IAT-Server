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


import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.messaging.UpdateNotification;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.net.URI;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Controller
@ClientControllerAnnotation
@RequestMapping("/ClientSoftwareUpdate")
public class ClientSoftwareUpdate {

    private static final Logger logger = LogManager.getLogger();
	private static final Logger critical = LogManager.getLogger("critical");

    @Inject
    @Named("ServerConfiguration")
    Properties serverConfiguration;

	private URI updateUri = null;

    @PostConstruct
	public void postConstruct() {
		try {
			updateUri = new URI(serverConfiguration.getProperty("client-software-updates"));
		}
		catch (java.net.URISyntaxException ex) {
			critical.error("Cannot instantiate URI", ex);
		}
	}


    @GetMapping(value = "/Manifest", produces = "text/xml")
    @ResponseBody
    public ResponseEntity<Manifest> getUpdateManifest(
            @RequestParam(value = "Version", required = false, defaultValue = "1-0-0-0") String clientVersion)
            throws java.net.URISyntaxException {
        logger.info("Retrieving update manifest");
        return new ResponseEntity<>(
                Manifest.getUpdateManifest(new File(updateUri), clientVersion.replace("-", ".")),
                HttpStatus.OK);
    }

    @GetMapping(value = "/Download")
    @ResponseBody
    public byte[] getDownload(@RequestParam(value = "Version", required = false, defaultValue = "1-0-0-0") String version)
            throws java.io.IOException, java.net.URISyntaxException {
        return Manifest.getUpdate(new File(updateUri), version.replace("-", "."));
    }


    @GetMapping(value = "/UpdateNotification", produces = "text/xml")
    @ResponseBody
    public ResponseEntity<UpdateNotification> getUpdateNotifications(@RequestParam(name = "Version") String version) throws java.net.URISyntaxException {
        return new ResponseEntity<>(
                Manifest.getUpdateNotification(new File(updateUri), version.replace("-", ".")),
                HttpStatus.OK);
    }
}
