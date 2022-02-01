/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.deployment;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.config.IatConfigurationProperties;
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.events.DeploymentFailedEvent;
import net.iatsoftware.iat.events.DeploymentFilesRecordedEvent;
import net.iatsoftware.iat.events.ItemSlideManifestReceivedEvent;
import net.iatsoftware.iat.events.ManifestReceivedEvent;
import net.iatsoftware.iat.events.WebSocketDataSent;
import net.iatsoftware.iat.generated.ResourceType;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.messaging.ServerException;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.inject.Inject;

@Controller
@RequestMapping("/DeploymentUpload")
public class DeploymentUploadController {

    interface Deployment {
        Long getStartTime();
        String getSessionId();
        Manifest getManifest();
    }

    private static final ConcurrentHashMap<Long, Deployment> deployments = new ConcurrentHashMap<>();
    @Inject
    IATRepositoryManager repositoryManager;
    @Inject
    IatConfigurationProperties serverConfiguration;
    @Inject
    ApplicationEventPublisher publisher;
    @Inject
    WebSocketService socketService;

    @PostMapping(value = "/DeploymentFiles", params = { "deploymentId", "sessionId"})
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
                for (var file : manifest.getFiles().stream().filter(f -> f.getResourceType().equals(ResourceType.DEPLOYMENT_FILE)).collect(Collectors.toList())) {
                    byte[] fileData  = new byte[(int)file.getSize()];
                    System.arraycopy(data, offset, fileData, 0, fileData.length);
                    TestResource tr = new TestResource(test, file.getPath(), file.getMimeType(), fileData);
                    repositoryManager.addTestResource(tr);
                }
            } catch (Exception ex) {
                this.publisher.publishEvent(new DeploymentFailedEvent(sessId, deploymentId, new ServerException("Error uploading file manifest.", ex)));
                return new ResponseEntity<>(new Envelope(new ServerException("Error storing file manifest for (Client " + test.getClient() + ", Test " + test.getTestName() + ")", ex)),  HttpStatus.OK);
            }
            publisher.publishEvent(new DeploymentFilesRecordedEvent(sessId, deploymentId));
            return new ResponseEntity<>(new Envelope(new TransactionRequest(TransactionType.TRANSACTION_SUCCESS)), HttpStatus.OK);
        };
    }

    @PostMapping(value = "/ItemSlideFiles", params = { "deploymentId", "sessionId"})
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
                        for (var file : manifest.getFiles().stream().filter(f -> f.getResourceType().equals(ResourceType.DEPLOYMENT_FILE)).collect(Collectors.toList())) {
                            byte[] fileData  = new byte[(int)file.getSize()];
                            System.arraycopy(data, offset, fileData, 0, fileData.length);
                            TestResource tr = new TestResource(test, file.getPath(), file.getMimeType(), fileData);
                            repositoryManager.addTestResource(tr);
                        }
                    } catch (Exception ex) {
                        this.publisher.publishEvent(new DeploymentFailedEvent(sessionId, deploymentId, new ServerException("Error uploading file manifest.", ex)));
                        return new ResponseEntity<>(new Envelope(new ServerException("Error storing file manifest for (Client " + test.getClient() + ", Test " + test.getTestName() + ")", ex)),  HttpStatus.OK);
                    }
                    publisher.publishEvent(new DeploymentFilesRecordedEvent(sessionId, deploymentId));
                    return new ResponseEntity<>(new Envelope(new TransactionRequest(TransactionType.TRANSACTION_SUCCESS)), HttpStatus.OK);
                };
    }
    

    @EventListener
    public void deploymentManifestReceived(ManifestReceivedEvent evt) {
        if (evt.getManifest().getFiles().stream().noneMatch(f -> f.getResourceType().equals(ResourceType.DEPLOYMENT_FILE)))
            return;
        deployments.put(evt.getDeploymentID(), new Deployment() {
            public Long getStartTime() {
                return System.currentTimeMillis();
            }
            public String getSessionId() {
                return evt.getSessionId();
            }
            public Manifest getManifest() {
                return evt.getManifest();
            }
        });
       var ds = repositoryManager.getDeploymentSession(evt.getDeploymentID());
       for (var file : evt.getManifest().getTestResources().getFile()) {
            var res = new TestResource(ds.getTest(), file.getPath(), file.getMimeType());
           repositoryManager.addTestResource(res);;
       }
        var trans = new TransactionRequest(TransactionType.DEPLOYMENT_FILE_MANIFEST_RECEIVED);
        trans.addLongValue("deploymentId", evt.getDeploymentID());
        trans.addStringValue("sessionId", evt.getSessionId());
        this.publisher.publishEvent(new WebSocketDataSent(evt.getSessionId(), new Envelope(trans)));
    }

    @EventListener
    public void itemSlideManifestReceivedEvent(ItemSlideManifestReceivedEvent evt) {
        if (evt.getManifest().getFiles().stream().noneMatch(f -> f.getResourceType().equals(ResourceType.ITEM_SLIDE)))
            return;
        deployments.put(evt.getDeploymentID(), new Deployment() {
            public Long getStartTime() {
                return System.currentTimeMillis();
            }
            public String getSessionId() {
                return evt.getSessionId();
            }
            public Manifest getManifest() {
                return evt.getManifest();
            }
        });
        var trans = new TransactionRequest(TransactionType.ITEM_SLIDE_MANIFEST_RECEIVED);
        trans.addLongValue("deploymentId", evt.getDeploymentID());
        trans.addStringValue("sessionId", evt.getSessionId());
        this.publisher.publishEvent(new WebSocketDataSent(evt.getSessionId(), new Envelope(trans)));
    }
}
