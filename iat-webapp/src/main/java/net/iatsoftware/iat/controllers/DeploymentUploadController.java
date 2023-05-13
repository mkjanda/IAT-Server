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

import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.entities.ResourceReference;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.events.BeginDeploymentEvent;
import net.iatsoftware.iat.events.DeploymentFailedEvent;
import net.iatsoftware.iat.events.ManifestReceivedEvent;
import net.iatsoftware.iat.events.WebSocketDataSent;
import net.iatsoftware.iat.generated.ResourceType;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.lang.module.FindException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Named;

@Controller
@RequestMapping("/DeploymentUpload")
public class DeploymentUploadController {
	private final String MANIFEST_UPLOAD_MILLIS = "MANIFEST_UPLOAD_MILLIS";
	private final String MANIFEST = "MANIFEST";


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

	@PostMapping(value = "/DeploymentFiles")
	@ResponseBody
	public ResponseEntity<Envelope> receiveDeploymentUpload(@RequestHeader("deploymentId") Long deploymentId,
			@RequestHeader("sessionId") String sessId, @RequestBody byte[] data) {
		var manifest = (Manifest)manifests.get(deploymentId).get(MANIFEST);
		var ds = repositoryManager.getDeploymentSession(deploymentId);
		var test = ds.getTest();
		try {
			if (!ds.getWebSocketId().equals(sessId))
				throw new FindException("The supplied web socket session id mismatched.");
			var offset = 0;
			var fSize = manifest.getFiles().stream().filter(f -> f.getResourceType().equals(ResourceType.TEST_CONFIGURATION)).findFirst().get().getSize();
			byte []fData = new byte[fSize];
			System.arraycopy(data, offset, fData, 0, fSize);
			offset += fSize;
			var testResource = new TestResource(test, 0, "text/xml", fData, ResourceType.TEST_CONFIGURATION);
			this.publisher.publishEvent(new BeginDeploymentEvent(ds.getId()));
			var images = manifest.getFiles().stream().filter(f -> f.getResourceType().equals(ResourceType.IMAGE)).collect(Collectors.toList());
			for (var img : images) {
				fSize = img.getSize();
				fData = new byte[fSize];
				System.arraycopy(data, offset, fData, 0, fSize);
				offset += fSize;
				testResource = new TestResource(test, img.getMimeType(), fData, ResourceType.IMAGE);
				repositoryManager.addTestResource(testResource);
			};
		} catch (javax.persistence.NoResultException | javax.persistence.NonUniqueResultException ex) {
			this.publisher.publishEvent(new DeploymentFailedEvent(sessId, deploymentId,
					new ServerExceptionMessage("Error uploading file manifest.", ex)));
			return new ResponseEntity<>(
					new Envelope(new ServerExceptionMessage("Error storing file manifest for (Client "
							+ test.getClient() + ", Test " + test.getTestName() + ")", ex)),
							HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PostMapping(value = "/ItemSlideFiles")
	public ResponseEntity<Envelope> receiveItemSlideUpload(@RequestHeader("deploymentId") Long deploymentId,
		@RequestHeader("sessionId") String sessId, @RequestBody byte[] data) {
			var manifest = (Manifest)manifests.get(deploymentId).get(MANIFEST);
			var ds = repositoryManager.getDeploymentSession(deploymentId);
			var test = ds.getTest();
			try {
				if (!ds.getWebSocketId().equals(sessId))
					throw new FindException("The supplied web socket session id mismatched.");
				var offset = 0;
				var slides = manifest.getFiles().stream().filter(f -> f.getResourceType()
					.equals(ResourceType.ITEM_SLIDE)).collect(Collectors.toList());
				for (var slide : slides) {
					var fSize = slide.getSize();
					var fData = new byte[fSize];
					System.arraycopy(data, offset, fData, 0, fSize);
					offset += fSize;
					var testResource = new TestResource(test, slide.getMimeType(), fData, ResourceType.ITEM_SLIDE);
					repositoryManager.addTestResource(testResource);
				};
			} catch (javax.persistence.NoResultException | javax.persistence.NonUniqueResultException ex) {
				this.publisher.publishEvent(new DeploymentFailedEvent(sessId, deploymentId,
					new ServerExceptionMessage("Error uploading file manifest.", ex)));
				return new ResponseEntity<>(
					new Envelope(new ServerExceptionMessage("Error storing file manifest for (Client "
						+ test.getClient() + ", Test " + test.getTestName() + ")", ex)),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(null, HttpStatus.OK);
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
		manifests.keySet().stream().filter(k -> (Long)((Map<String, Object>)manifests.get(k)).get(MANIFEST_UPLOAD_MILLIS) - 
			System.currentTimeMillis() > 1_800_000L).forEach(k -> manifests.remove(k));
		
	}
}
