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
import net.iatsoftware.iat.entities.ResourceReference;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.events.DeploymentFailedEvent;
import net.iatsoftware.iat.events.TestResourcesRecordedEvent;
import net.iatsoftware.iat.events.ManifestReceivedEvent;
import net.iatsoftware.iat.events.WebSocketDataSent;
import net.iatsoftware.iat.generated.ResourceType;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.services.WebSocketService;

import org.springframework.stereotype.Controller;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.module.FindException;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;

@Controller
@RequestMapping("/DeploymentUpload")
public class DeploymentUploadController {

	@Inject
	IATRepositoryManager repositoryManager;
	@Inject
	@Named("ServerConfiguration")
	Properties serverConfiguration;
	@Inject
	ApplicationEventPublisher publisher;
	@Inject
	WebSocketService socketService;

	@PostMapping(value = "/DeploymentFiles")
	@ResponseBody
	public Callable<ResponseEntity<Envelope>> receiveDeploymentUpload(@RequestHeader("deploymentId") Long deploymentId,
			@RequestHeader("sessionId") String sessId, @RequestBody byte[] data) {
		return () -> {
			var ds = repositoryManager.getDeploymentSession(deploymentId);
			var test = ds.getTest();
			try {
				if (!ds.getWebSocketId().equals(sessId))
					throw new FindException("The supplied web socket session id mismatched.");
				var manifest = repositoryManager.getTestManifest(test);
				int offset = 0;
				var testResources = repositoryManager.getTestResources(test, ResourceType.DEPLOYMENT_FILE);
				testResources.addAll(repositoryManager.getTestResources(test, ResourceType.DEPLOYMENT_IMAGE));
				for (var file : manifest.getFiles().stream()
						.filter(f -> f.getResourceType().equals(ResourceType.DEPLOYMENT_FILE) || 
						f.getResourceType().equals(ResourceType.DEPLOYMENT_IMAGE))
						.collect(Collectors.toList())) {
					var resource = testResources.stream().filter(res -> res.getPath().equals(file.getPath())).findFirst();
					byte[] fileData = new byte[(int) file.getSize()];
					System.arraycopy(data, offset, fileData, 0, fileData.length);
					resource.get().setResource(fileData);
					repositoryManager.updateTestResource(resource.get());
				}
			} catch (Exception ex) {
				this.publisher.publishEvent(new DeploymentFailedEvent(sessId, deploymentId,
						new ServerExceptionMessage("Error uploading file manifest.", ex)));
				return new ResponseEntity<>(
						new Envelope(new ServerExceptionMessage("Error storing file manifest for (Client "
								+ test.getClient() + ", Test " + test.getTestName() + ")", ex)),
						HttpStatus.OK);
			}
			publisher.publishEvent(new TestResourcesRecordedEvent(sessId, deploymentId, ResourceType.DEPLOYMENT_FILE));
			return new ResponseEntity<>(new Envelope(new TransactionRequest(TransactionType.TRANSACTION_SUCCESS)),
					HttpStatus.OK);
		};
	}

	@PostMapping(value = "/ItemSlideFiles")
	public Callable<ResponseEntity<Envelope>> receiveItemSlideUpload(@RequestHeader("deploymentId") Long deploymentId,
			@RequestHeader("sessionId") String sessionId, @RequestBody byte[] data) {
		return () -> {
			DeploymentSession ds = repositoryManager.getDeploymentSession(deploymentId);
			var test = ds.getTest();
			try {
				if (!ds.getWebSocketId().equals(sessionId))
					throw new FindException("The supplied web socket session id mismatched.");
				var manifest = repositoryManager.getTestManifest(test);
				int offset = 0;
				var resources = repositoryManager.getTestResources(test, ResourceType.ITEM_SLIDE);
				for (var file : manifest.getFiles().stream()
						.filter(f -> f.getResourceType().equals(ResourceType.ITEM_SLIDE))
						.collect(Collectors.toList())) {
					byte[] fileData = new byte[(int) file.getSize()];
					System.arraycopy(data, offset, fileData, 0, fileData.length);
					var resource = resources.stream().filter(r -> r.getPath().equals(file.getPath())).findFirst();
					resource.get().setResource(fileData);
					repositoryManager.updateTestResource(resource.get());
				}
			} catch (Exception ex) {
				this.publisher.publishEvent(new DeploymentFailedEvent(sessionId, deploymentId,
						new ServerExceptionMessage("Error uploading file manifest.", ex)));
				return new ResponseEntity<>(
					new Envelope(new ServerExceptionMessage("Error storing file manifest for (Client "
						+ test.getClient() + ", Test " + test.getTestName() + ")", ex)),
						HttpStatus.OK);
			}
			publisher.publishEvent(new TestResourcesRecordedEvent(sessionId, deploymentId, ResourceType.ITEM_SLIDE));
			return new ResponseEntity<>(new Envelope(new TransactionRequest(TransactionType.TRANSACTION_SUCCESS)),
					HttpStatus.OK);
		};
	}

	@EventListener
	public void deploymentManifestReceived(ManifestReceivedEvent evt) {
		var ds = repositoryManager.getDeploymentSession(evt.getDeploymentID());
		for (var file : evt.getManifest().getFiles()) {
			ResourceType rType;
			if (Pattern.matches("^image/", file.getMimeType()))
				rType = ResourceType.DEPLOYMENT_IMAGE;
			else
				rType = ResourceType.DEPLOYMENT_FILE;
			var res = new TestResource(ds.getTest(), file.getPath(), file.getMimeType(), rType);
			res.setName(file.getName());
			res.setSize((int) file.getSize());
			res.setResourceType(file.getResourceType());
			repositoryManager.addTestResource(res);
			if (file.getReferenceId().size() > 0) {
				file.getReferenceId().forEach((refId) -> {
					var ref = new ResourceReference(res, refId);
					repositoryManager.addResourceReference(ref);
					res.getReferences().add(ref);
				});
				repositoryManager.updateTestResource(res);
			}
		}
		var trans = new TransactionRequest(TransactionType.DEPLOYMENT_FILE_MANIFEST_RECEIVED);
		trans.addLongValue("DeploymentId", evt.getDeploymentID());
		trans.addStringValue("SessionId", evt.getSessionId());
		this.publisher.publishEvent(new WebSocketDataSent(evt.getSessionId(), new Envelope(trans)));
	}
}
