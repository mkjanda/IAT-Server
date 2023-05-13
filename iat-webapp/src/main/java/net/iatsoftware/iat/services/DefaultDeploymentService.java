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
import net.iatsoftware.iat.config.IATDeployerFactory;
import net.iatsoftware.iat.deployment.IATDeployer;
import net.iatsoftware.iat.deployment.IATRedeployer;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.User;
import net.iatsoftware.iat.events.AbortDeploymentEvent;
import net.iatsoftware.iat.events.BeginDeploymentEvent;
import net.iatsoftware.iat.events.DeploymentDescriptorMismatch;
import net.iatsoftware.iat.events.DeploymentFailedEvent;
import net.iatsoftware.iat.events.DeploymentSuccessEvent;
import net.iatsoftware.iat.events.DeploymentCleanupEvent;
import net.iatsoftware.iat.events.RSAKeyReceivedEvent;
import net.iatsoftware.iat.events.TestResourcesRecordedEvent;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Named;

@Service
@EnableAsync
public class DefaultDeploymentService implements DeploymentService {

    private static final Logger critical = LogManager.getLogger("critical");
    private static final ConcurrentHashMap<Long, IATDeployer> IATDeploymentMap = new ConcurrentHashMap<>();
    @Inject
    IATRepositoryManager iatRepositoryManager;
    @Inject
    MyBeanFactory iatServerBeanFactory;
    @Inject
    ApplicationEventPublisher publisher;
    @Inject
    @Named("ServerConfiguration")
    Properties serverConfiguration;
    @Inject
    WebSocketService webSocketService;
    @Inject
    IATDeployerFactory deployerFactory;

    @Override
    public Calendar beginNewDeployment(Client c, User u, String testName, String sessID) {
        try {
            IAT test = new IAT(c, u, testName, serverConfiguration.getProperty("admin-version"),
                    Integer.parseInt(serverConfiguration.getProperty("data-format-version")),
                    Calendar.getInstance());
            DeploymentSession ds = new DeploymentSession(c, u, test, sessID);
            iatRepositoryManager.addTest(test);
            iatRepositoryManager.storeDeploymentSession(ds);
            IATDeployer deployment = deployerFactory.createDeployer(c.getClientId(), ds.getId(), test.getId(), sessID);
            IATDeploymentMap.put(ds.getId(), deployment);
            webSocketService.setSessionProperty(sessID, "DeploymentID", ds.getId());
            webSocketService.setSessionProperty(sessID, "IATName", testName);
            return ds.getDeploymentStart();
        } catch (javax.xml.bind.JAXBException ex) {
            this.publisher.publishEvent(new DeploymentFailedEvent(sessID, -1,
                    new ServerExceptionMessage("Failed to marshal manifest object", ex)));
            var calendar = Calendar.getInstance();
            calendar.setTimeInMillis(0);
            return calendar;
        }
    }

    @Override
    public Calendar beginNewRedeployment(Client c, User u, String testName, IAT oldTest, String sessID)
            throws java.io.IOException, java.net.URISyntaxException, java.nio.file.NoSuchFileException {
        DeploymentSession ds = null;
        IAT test = null;
        try {
            test = new IAT(c, u, testName, serverConfiguration.getProperty("admin-version"),
                    Integer.parseInt(serverConfiguration.getProperty("data-format-version")),
                    oldTest.getUploadTimestamp());
            iatRepositoryManager.addTest(test);
            ds = new DeploymentSession(c, u, test, sessID);
            ds.setTest(test);
            iatRepositoryManager.storeDeploymentSession(ds);
            test.setNumAdministrations(oldTest.getNumAdministrations());
        } catch (javax.xml.bind.JAXBException ex) {
            this.publisher.publishEvent(new DeploymentFailedEvent(sessID, -1,
                    new ServerExceptionMessage("Failed to marshal manifest object", ex)));
            var calendar = Calendar.getInstance();
            calendar.setTimeInMillis(0);
            return calendar;
        }
        IATRedeployer redeployer = null;
        try {
            iatRepositoryManager.storeDeploymentSession(ds);
            redeployer = deployerFactory.createRedeployer(c.getClientId(), ds.getId(), test.getId(), oldTest.getId(),
                    sessID);

            IATDeploymentMap.put(ds.getId(), redeployer);
            webSocketService.setSessionProperty(sessID, "DeploymentID", ds.getTest().getId());
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
        IATDeploymentMap.remove(ds.getId());
        iatRepositoryManager.finalizeDeployment(ds.getId());
    }

    @EventListener
    public void processUploadRequest(UploadRequestEvent e) {
        try {
            IATDeploymentMap.get(e.getDeploymentID()).requestUpload(e.getSessionId());
        } catch (Exception ex) {
            reportException("Error processing upload request", ex, e.getSessionId());
        }
    }

    @EventListener
    public void onRSAKeyReceived(RSAKeyReceivedEvent e) {
        try {
            IATDeploymentMap.get(e.getDeploymentID()).storeRSAKeys(e.getAdminKey(), e.getDataKey());
            this.publisher.publishEvent(new WebSocketDataSent(e.getSessionId(),
                    new Envelope(new TransactionRequest(TransactionType.ENCRYPTION_KEYS_RECEIVED))));
        } catch (Exception ex) {
            reportException("Error recording encryption keys", ex, e.getSessionId());
        }
    }

    @EventListener
    public void beginDeployment(BeginDeploymentEvent evt) {
        var deployer = IATDeploymentMap.get(evt.getDeploymentSessionID());
        if (deployer == null)
            return;
        deployer.generateTest();
    }

    @EventListener
    public void onDeploymentFailed(DeploymentFailedEvent e) {
        critical.error(e.getFailureCause().getExceptionMessage() + "\n"
                + e.getFailureCause().getStackTraceElement().stream()
                        .reduce(new StringBuffer(), (sb1, sb2) -> sb1.append("\n").append(sb2),
                                (sb1, sb2) -> sb1.append("\n").append(sb2)));
        IATDeploymentMap.remove(e.getDeploymentID());
        iatRepositoryManager.deleteIAT(e.getTestId());
        this.publisher.publishEvent(new WebSocketFinalDataSent(e.getSessionId(), new Envelope(e.getFailureCause())));
    }

    @EventListener
    public void onDeploymentDescriptorMismatch(DeploymentDescriptorMismatch e) {
        try {
            var deployer = IATDeploymentMap.get(e.getDeploymentID());
            ((IATRedeployer) deployer).onDescriptorMismatch(e.getSessionId());
            IATDeploymentMap.remove(e.getDeploymentID());
        } catch (Exception ex) {
            reportException("Error handling deployment descriptor mismatch", ex, e.getSessionId());
        }
    }

    @EventListener
    public void onDeploymentSuccess(DeploymentSuccessEvent e) {
        try {
            var test = iatRepositoryManager.getIAT(e.getTestId());
            IATDeploymentMap.remove(e.getDeploymentID());
            iatRepositoryManager.deleteDeploymentSession(test);
            this.publisher.publishEvent(new WebSocketFinalDataSent(e.getSessionId(),
                    new Envelope(new TransactionRequest(TransactionType.TRANSACTION_SUCCESS))));
        } catch (Exception ex) {
            reportException("Error processing \"Test Deployment Complete\" event", ex, e.getSessionId());
        }
    }

    @EventListener
    public void onAbortDeployment(AbortDeploymentEvent e) {
        try {
            var deployer = IATDeploymentMap.remove(e.getDeploymentID());
            if (deployer != null) {
                deployer.abort();
                iatRepositoryManager.deleteIAT(deployer.getTestId());
            }
            this.publisher.publishEvent(new WebSocketFinalDataSent(e.getSessionId(),
                    new Envelope(new TransactionRequest(TransactionType.DEPLOYMENT_HALTED))));
        } catch (Exception ex) {
    //        reportException("Error aborting test deployment", ex, e.getSessionId());
        }
    }

    @EventListener
    public void onTokenDefinitionReceived(TokenDefinitionReceivedEvent e) {
        try {
            var deployer = IATDeploymentMap.get(e.getDeploymentID());
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
        IATDeploymentMap.remove(ds.getId());
    }

    @Scheduled(initialDelay = 60_000L, fixedRate = 5_000)
    private void cleanupAbandonedDeployments() {
        long timeout = System.currentTimeMillis() - DeploymentSession.DEPLOYMENT_TIMEOUT;
        var removing = new ArrayList<Long>();
        IATDeploymentMap.keys().asIterator().forEachRemaining(dsId -> {
            var ds = iatRepositoryManager.getDeploymentSession(dsId);
            if (ds == null)
                removing.add(dsId);
            else if (ds.getDeploymentStart().before(timeout))
                removing.add(dsId);
        });
        removing.forEach(dsId -> {
            var ds = iatRepositoryManager.getDeploymentSession(dsId);
            if (ds != null)
                iatRepositoryManager.deleteIAT(ds.getTest());
            IATDeploymentMap.remove(dsId);
        });
    }

    private void reportException(String msg, Exception ex, String sessId) {
        critical.error(msg, ex);
        this.publisher.publishEvent(new WebSocketDataSent(sessId, new Envelope(new ServerExceptionMessage(msg, ex))));
        this.publisher.publishEvent(new WebSocketFinalDataSent(sessId,
                new Envelope(new TransactionRequest(TransactionType.TRANSACTION_FAIL))));
    }
}
