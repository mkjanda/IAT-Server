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

import net.iatsoftware.iat.configfile.ConfigFile;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.events.BeginDeploymentEvent;
import net.iatsoftware.iat.events.ManifestReceivedEvent;
import net.iatsoftware.iat.events.WebSocketDataSent;
import net.iatsoftware.iat.generated.ResourceType;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.services.WebSocketService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.Unmarshaller;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.ByteArrayInputStream;
import java.lang.module.FindException;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.transform.stream.StreamSource;

@Controller
@RequestMapping("/DeploymentUpload")
public class DeploymentUploadController {
	private final String MANIFEST_UPLOAD_MILLIS = "MANIFEST_UPLOAD_MILLIS";
	private final String MANIFEST = "MANIFEST";

	@Inject
	Unmarshaller marshaller;
	@Inject
	IATRepositoryManager repositoryManager;
	@Inject
	@Named("ServerConfiguration")
	Properties serverConfiguration;
	@Inject
	ApplicationEventPublisher publisher;
	@Inject
	WebSocketService socketService;

	private static final ConcurrentHashMap<Long, Map<String, Object>> manifests = new ConcurrentHashMap<Long, Map<String, Object>>();

	private static Logger transactions = LogManager.getLogger("transactions");
	private static Logger critical = LogManager.getLogger("critical");

	@PostMapping("/DeploymentFiles/{upload}")
	public ResponseEntity<Envelope> deploymentUpload(@RequestHeader("deploymentId") Long deploymentId,
			@RequestHeader("sessionId") String sessId, @PathVariable("upload") String uploadContents,
			@RequestBody byte[] data) throws java.io.IOException {
		var deploymentSession = repositoryManager.getDeploymentSession(deploymentId);
		if (!deploymentSession.getWebSocketId().equals(sessId))
			throw new FindException("The supplied web socket session id mismatched.");
		var manifest = (Manifest) manifests.get(deploymentId).get(MANIFEST);
		var test = deploymentSession.getTest();
		if (uploadContents.equals("configuration")) {
			try {
				var cf = (ConfigFile) marshaller.unmarshal(new StreamSource(new ByteArrayInputStream(data))); 
				socketService.setSessionProperty(sessId, "configuration", cf);
				cf.setIATName(test.getTestName());
				
				var testResource = new TestResource(test, 0, "text/xml", data,
						ResourceType.TEST_CONFIGURATION);
				repositoryManager.addTestResource(testResource);
			} catch (java.io.IOException ex) {
				critical.error("Error unmarshalling config file.", ex);
			}
		} else {
			var offset = 0;
			var rType = uploadContents.equals("images") ? ResourceType.IMAGE : ResourceType.ITEM_SLIDE;
			var images = manifest.getFiles().stream().filter(f -> f.getResourceType().equals(rType))
					.collect(Collectors.toList());
			var configFile = (ConfigFile) socketService.getSessionProperty(sessId, "configuration");
			for (var img : images) {
				var fSize = img.getSize();
				var fData = new byte[fSize];
				System.arraycopy(data, offset, fData, 0, fSize);
				offset += fSize;
				var testResource = new TestResource(test, img.getMimeType(), fData, rType);
				repositoryManager.addTestResource(testResource);
				if (rType == ResourceType.IMAGE) {
					configFile.getDisplayItemList().getIATDisplayItem().stream()
							.filter(di -> di.getFilename().equals(img.getName()))
							.forEach(di -> di.setResourceId(testResource.getResourceId()));
				}
			}
		}
		if (uploadContents.equals("images"))
			this.publisher.publishEvent(new BeginDeploymentEvent(deploymentSession.getId()));
		return new ResponseEntity<>(new Envelope(new TransactionRequest(TransactionType.TRANSACTION_SUCCESS)),
				HttpStatus.OK);
	}

	@ExceptionHandler(java.io.IOException.class)
	public ResponseEntity<Integer> onException() {
		return new ResponseEntity<Integer>(0, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@EventListener
	public void manifestReceived(ManifestReceivedEvent evt) {
		try {
			var map = new HashMap<String, Object>();
			manifests.put(evt.getDeploymentID(), map);
			map.put(MANIFEST, evt.getManifest());
			map.put(MANIFEST_UPLOAD_MILLIS, System.currentTimeMillis());
			var deploymentSession = repositoryManager.getDeploymentSession(evt.getDeploymentID());
			deploymentSession = repositoryManager.updateDeploymentSession(deploymentSession);
			var trans = new TransactionRequest(TransactionType.DEPLOYMENT_FILE_MANIFEST_RECEIVED);
			trans.addLongValue("DeploymentId", evt.getDeploymentID());
			trans.addStringValue("SessionId", evt.getSessionId());
			transactions.info(String.format("Client(%d) %s: ", evt.getManifest().getClientId(),
					evt.getManifest().getProductKey()));
			this.publisher.publishEvent(new WebSocketDataSent(evt.getSessionId(), new Envelope(trans)));
		} catch (java.lang.Exception ex) {
			critical.error("Error recording file manifest", ex);
		}
	}

	@Scheduled(initialDelay = 1_800_000L, fixedDelay = 1_800_000L)
	private void cleanStaleDeployments() {
		manifests.keySet().stream()
				.filter(k -> (Long) ((Map<String, Object>) manifests.get(k)).get(MANIFEST_UPLOAD_MILLIS) -
						System.currentTimeMillis() > 1_800_000L)
				.forEach(k -> manifests.remove(k));

	}
}
