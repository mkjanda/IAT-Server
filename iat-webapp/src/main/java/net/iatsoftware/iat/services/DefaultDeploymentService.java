/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.services;

/**
 *
 * @author Michael Janda
 */
import net.iatsoftware.iat.config.MyBeanFactory;
import net.iatsoftware.iat.config.IatConfigurationProperties;
import net.iatsoftware.iat.deployment.IATDeployer;
import net.iatsoftware.iat.deployment.IATRedeployer;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.User;
import net.iatsoftware.iat.events.AbortDeploymentEvent;
import net.iatsoftware.iat.events.DeploymentDescriptorMismatch;
import net.iatsoftware.iat.events.DeploymentFailedEvent;
import net.iatsoftware.iat.events.DeploymentSuccessEvent;
import net.iatsoftware.iat.events.DeploymentCleanupEvent;
import net.iatsoftware.iat.events.RSAKeyReceivedEvent;
import net.iatsoftware.iat.events.TokenDefinitionReceivedEvent;
import net.iatsoftware.iat.events.UploadRequestEvent;
import net.iatsoftware.iat.events.WebSocketDataSent;
import net.iatsoftware.iat.events.WebSocketFinalDataSent;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;

@Service
@EnableAsync
public class DefaultDeploymentService implements DeploymentService {

    private static final Logger logger = LogManager.getLogger();
    private static final ConcurrentHashMap<DeploymentSession, IATDeployer> IATDeploymentMap = new ConcurrentHashMap<>();
    @Inject
    IATRepositoryManager iatRepositoryManager;
    @Inject
    MyBeanFactory iatServerBeanFactory;
    @Inject
    ApplicationEventPublisher publisher;
    @Inject
    IatConfigurationProperties serverConfiguration;
    @Inject
    WebSocketService webSocketService;

    @Override
    public Calendar beginNewDeployment(Client c, User u, String testName, String sessID) {
        IAT test = new IAT(c, u, testName, serverConfiguration.getAdminVersion().toString(),
                serverConfiguration.getDataFormat(), Calendar.getInstance());
        DeploymentSession ds = new DeploymentSession(c, u, test, sessID);
        iatRepositoryManager.storeDeploymentSession(ds);
        IATDeployer deployment = iatServerBeanFactory.IATDeployer(c.getClientId(), ds.getId(), test.getId(), sessID);
        IATDeploymentMap.put(ds, deployment);
        webSocketService.setSessionProperty(sessID, "DeploymentID", ds.getId());
        webSocketService.setSessionProperty(sessID, "IATName", testName);
        return ds.getDeploymentStart();
    }

    @Override
    public Calendar beginNewRedeployment(Client c, User u, String testName, IAT oldTest, String sessID)
            throws java.io.IOException, java.net.URISyntaxException, java.nio.file.NoSuchFileException {
        DeploymentSession ds = null;
        IAT test = new IAT(c, u, testName, serverConfiguration.getAdminVersion().toString(),
                serverConfiguration.getDataFormat(), oldTest.getUploadTimestamp());
        ds = new DeploymentSession(c, u, test, sessID);
        test.setNumAdministrations(oldTest.getNumAdministrations());
        IATRedeployer redeployer = null;
        try {
            iatRepositoryManager.storeDeploymentSession(ds);
            redeployer = iatServerBeanFactory.IATRedeployer(c.getClientId(), ds.getId(), test.getId(), oldTest.getId(),
                    sessID);

            IATDeploymentMap.put(ds, redeployer);
            webSocketService.setSessionProperty(sessID, "DeploymentID", ds.getId());
            webSocketService.setSessionProperty(sessID, "ReplacementTest", test);
            iatRepositoryManager.copyRSAKey(test.getId(), oldTest.getId());
            return ds.getDeploymentStart();
        } catch (org.hibernate.exception.ConstraintViolationException ex) {
            redeployer.setFailed(sessID, new ServerExceptionMessage("Constraint violation creating redeployer", ex));
            throw ex;
        }
    }

    @Override
    public void completeDeployment(DeploymentSession ds) {
        IATDeploymentMap.remove(ds);
        iatRepositoryManager.finalizeDeployment(dsID);
    }

    @EventListener
    public void processUploadRequest(UploadRequestEvent e) {
        try {
            var deployer = IATDeploymentMap.get(IATDeploymentMap.keySet().stream()
                    .filter(key -> key.getId() == e.getDeploymentID()).findFirst().get());
            deployer.requestUpload(e.getSessionId());
        } catch (Exception ex) {
            reportException("Error processing upload request", ex, e.getSessionId());
        }
    }

    @EventListener
    public void onRSAKeyReceived(RSAKeyReceivedEvent e) {
        try {
            var deployer = IATDeploymentMap.get(IATDeploymentMap.keySet().stream()
                    .filter(key -> key.getId() == e.getDeploymentID()).findFirst().get());
            deployer.storeRSAKeys(e.getAdminKey(), e.getDataKey());
            this.publisher.publishEvent(new WebSocketDataSent(e.getSessionId(),
                    new Envelope(new TransactionRequest(TransactionType.ENCRYPTION_KEYS_RECEIVED))));
        } catch (Exception ex) {
            reportException("Error recording encryption keys", ex, e.getSessionId());
        }
    }

    @EventListener
    public void onDeploymentFailed(DeploymentFailedEvent e) {
            var test = iatRepositoryManager.getIAT(e.getTestId());
            IATDeploymentMap.remove(test.getDeploymentSession());
            iatRepositoryManager.deleteIAT(e.getTestId());
            this.publisher.publishEvent(new WebSocketFinalDataSent(e.getSessionId(), new Envelope(e.getFailureCause())));
    }

    @EventListener
    public void onDeploymentDescriptorMismatch(DeploymentDescriptorMismatch e) {
        try {
            var deploymentSession = IATDeploymentMap.keySet().stream().filter(key -> key.getId() == e.getDeploymentID())
                    .findFirst().get();
            var deployer = IATDeploymentMap.get(deploymentSession);
            ((IATRedeployer) deployer).onDescriptorMismatch(e.getSessionId());
            IATDeploymentMap.remove(deploymentSession);
        } catch (Exception ex) {
            reportException("Error handling deployment descriptor mismatch", ex, e.getSessionId());
        }
    }

    @EventListener
    public void onDeploymentSuccess(DeploymentSuccessEvent e) {
        try {
            var test = iatRepositoryManager.getIAT(e.getTestId());
            IATDeploymentMap.remove(test.getDeploymentSession());
            iatRepositoryManager.deleteDeploymentSession(test);
            this.publisher.publishEvent(new WebSocketFinalDataSent(e.getSessionId(), new Envelope(new TransactionRequest(TransactionType.TRANSACTION_SUCCESS)));
        } catch (Exception ex) {
            reportException("Error processing \"Test Deployment Complete\" event", ex, e.getSessionId());
        }
    }

    @EventListener
    public void onAbortDeployment(AbortDeploymentEvent e) {
        try {
            IATDeployer deployer = IATDeploymentMap.remove(IATDeploymentMap.keySet().stream()
                    .filter(key -> key.getId() == e.getDeploymentID()).findFirst().get());
            if (deployer == null) {
                this.publisher.publishEvent(new WebSocketFinalDataSent(e.getSessionId(),
                        new Envelope(new TransactionRequest(TransactionType.DEPLOYMENT_ABORT_FAILED))));
                return;
            }
            this.publisher.publishEvent(new WebSocketFinalDataSent(e.getSessionId(),
                    new Envelope(new TransactionRequest(TransactionType.TRANSACTION_SUCCESS))));
        } catch (Exception ex) {
            reportException("Error aborting test deployment", ex, e.getSessionId());
        }
    }

    @EventListener
    public void onTokenDefinitionReceived(TokenDefinitionReceivedEvent e) {
        try {
            var deployer = IATDeploymentMap.get(IATDeploymentMap.keySet().stream()
                    .filter(key -> key.getId() == e.getDeploymentID()).findFirst().get());
            deployer.storeTokenDefinition(e.getTokenType(), e.getTokenName());
            this.publisher.publishEvent(new WebSocketDataSent(e.getSessionId(),
                    new Envelope(new TransactionRequest(TransactionType.TOKEN_DEFINITION_RECEIVED))));
        } catch (Exception ex) {
            reportException("Error reccording token descriptor", ex, e.getSessionId());
        }
    }

    @EventListener
    public void onDeploymentCleanup(DeploymentCleanupEvent evt) {
        var ds = iatRepositoryManager.getDeploymentSession(evt.getDeploymentSessionID());
        iatRepositoryManager.deleteIAT(ds.getTest());
        IATDeploymentMap.remove(ds);
    }

    @Scheduled(initialDelay = 60_000L, fixedRate = 5_000)
    private void cleanupAbandonedDeployments() {
        long timeout = System.currentTimeMillis() - DeploymentSession.DEPLOYMENT_TIMEOUT;
        for (var ds : IATDeploymentMap.keySet().stream().filter(k -> k.getDeploymentStart().getTimeInMillis() < timeout)
                .collect(Collectors.toList())) {
            iatRepositoryManager.deleteIAT(ds.getTest().getId());
            IATDeploymentMap.remove(ds);
        }
    }

    private void reportException(String msg, Exception ex, String sessId) {
        logger.error(msg, ex);
        this.publisher.publishEvent(new WebSocketDataSent(sessId, new Envelope(new ServerExceptionMessage(msg, ex))));
        this.publisher.publishEvent(new WebSocketFinalDataSent(sessId,
                new Envelope(new TransactionRequest(TransactionType.TRANSACTION_FAIL))));
    }
}
