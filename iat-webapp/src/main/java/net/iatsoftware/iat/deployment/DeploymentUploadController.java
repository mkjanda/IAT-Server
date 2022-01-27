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
import net.iatsoftware.iat.entities.ResourceReference;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.events.DeploymentFailedEvent;
import net.iatsoftware.iat.events.DeploymentFilesRecordedEvent;
import net.iatsoftware.iat.events.ItemSlideManifestReceivedEvent;
import net.iatsoftware.iat.events.ItemSlidesDeploymentCompleteEvent;
import net.iatsoftware.iat.events.ManifestReceivedEvent;
import net.iatsoftware.iat.events.WebSocketDataSent;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.ItemSlideManifest;
import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.messaging.ServerException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.module.FindException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;

@Controller
@RequestMapping("/DeploymentUpload")
public class DeploymentUploadController {

    private static final Logger critical = LogManager.getLogger("critical");

    interface Deployment {
        Long getStartTime();
        String getSessionId();
    }
    interface DeploymentFiles extends Deployment {
        Manifest getManifest();
    }
    interface ItemSlides extends Deployment {
        ItemSlideManifest getManifest();
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
    public Callable<ResponseEntity<Byte>> receiveDeploymentUpload(@RequestHeader("deploymentId") Long deploymentId,
            @RequestHeader("sessionId") String sessId, @RequestBody byte[] data) {
        return () -> {
            try {
                DeploymentSession ds = repositoryManager.getDeploymentSession(deploymentId);
                if (!ds.getWebSocketId().equals(sessId))
                    throw new FindException("The supplied web socket session id mismatched.");
                var test = ds.getTest();
                var manifest = ((DeploymentFiles)deployments.get(deploymentId)).getManifest();
                int offset = 0;
                for (var f : manifest.getFiles()) {
                    byte[] fileData = new byte[(int) f.getSize()];
                    System.arraycopy(data, offset, fileData, 0, fileData.length);
                    TestResource tr;
                    if (f.getAddendum() == null) {
                        tr = new TestResource(test, f.getName(), f.getMimeType(), fileData);
                    } else {
                        tr = new TestResource(test, f.getName(), f.getMimeType(), fileData, f.getAddendum());
                    }
                    repositoryManager.addTestResource(tr);
                }
            } catch (org.hibernate.exception.ConstraintViolationException ex) {
                this.publisher.publishEvent(new DeploymentFailedEvent(sessId, deploymentId, new ServerException("Error uploading file manifest.", ex)));
                critical.error(ex);
                return new ResponseEntity<>((byte)0, HttpStatus.OK);
            } catch (Exception ex) {
                this.publisher.publishEvent(new DeploymentFailedEvent(sessId, deploymentId, new ServerException("Error uploading file manifest.", ex)));
                critical.error(ex);
                return new ResponseEntity<>((byte)0, HttpStatus.OK);
            }
            publisher.publishEvent(new DeploymentFilesRecordedEvent(sessId, deploymentId));
            return new ResponseEntity<>((byte)1, HttpStatus.OK);
        };
    }

    @PostMapping(value = "/ItemSlidesUpload", params = { "deploymentId", "sessionId"})
    public Callable<ResponseEntity<Byte>> receiveItemSlideUpload(@RequestHeader("deploymentId") Long deploymentId,
            @RequestHeader("sessionId") String sessionId, @RequestBody byte[] data) {
        return () -> {
            try {
                DeploymentSession ds = repositoryManager.getDeploymentSession(deploymentId);
                if (!ds.getWebSocketId().equals(sessionId))
                    throw new FindException("The supplied web socket session id mismatched.");
                var test = ds.getTest();
                var manifest = ((ItemSlides)deployments.get(deploymentId)).getManifest();
                int offset = 0;
                int ctr = 0;
                for (var f : manifest.getManifest().getFiles()) {
                    byte[] fileData = new byte[(int) f.getSize()];
                    System.arraycopy(data, offset, fileData, 0, fileData.length);
                    TestResource tr;
                    if (f.getAddendum() == null) {
                        tr = new TestResource(test, f.getName(), f.getMimeType(), fileData);
                    } else {
                        tr = new TestResource(test, f.getName(), f.getMimeType(), fileData, f.getAddendum());
                    }
                    repositoryManager.addTestResource(tr);
                    var references = manifest.getFileReferences().getFileReference().get(ctr++);
                    for (var ref : references.getReferenceUriOriginalString()) {
                        repositoryManager.addResourceReference(new ResourceReference(tr.getResourceId(), ref));
                    }
                }
            } catch (org.hibernate.exception.ConstraintViolationException ex) {
                this.publisher.publishEvent(new DeploymentFailedEvent(sessionId, deploymentId, new ServerException("Error uploading file manifest.", ex)));
                critical.error(ex);
                return new ResponseEntity<>((byte)0, HttpStatus.OK);
            } catch (Exception ex) {
                this.publisher.publishEvent(new DeploymentFailedEvent(sessionId, deploymentId, new ServerException("Error uploading file manifest.", ex)));
                critical.error(ex);
                return new ResponseEntity<>((byte)0, HttpStatus.OK);
            }
            publisher.publishEvent(new ItemSlidesDeploymentCompleteEvent(sessionId, deploymentId));
            return new ResponseEntity<>((byte)1, HttpStatus.OK);
        };
    }
    

    @EventListener
    public void deploymentManifestReceived(ManifestReceivedEvent evt) {
        deployments.put(evt.getDeploymentID(), new DeploymentFiles() {
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
        var trans = new TransactionRequest(TransactionType.DEPLOYMENT_FILE_MANIFEST_RECEIVED);
        trans.addLongValue("deploymentId", evt.getDeploymentID());
        trans.addStringValue("sessionId", evt.getSessionId());
        this.publisher.publishEvent(new WebSocketDataSent(evt.getSessionId(), new Envelope(trans)));
    }

    @EventListener
    public void itemSlideManifestReceivedEvent(ItemSlideManifestReceivedEvent evt) {
        deployments.put(evt.getDeploymentID(), new ItemSlides() {
            public Long getStartTime() {
                return System.currentTimeMillis();
            }
            public String getSessionId() {
                return evt.getSessionId();
            }
            public ItemSlideManifest getManifest() {
                return evt.getManifest();
            }
        });
        var trans = new TransactionRequest(TransactionType.ITEM_SLIDE_MANIFEST_RECEIVED);
        trans.addLongValue("deploymentId", evt.getDeploymentID());
        trans.addStringValue("sessionId", evt.getSessionId());
        this.publisher.publishEvent(new WebSocketDataSent(evt.getSessionId(), new Envelope(trans)));
    }
}
