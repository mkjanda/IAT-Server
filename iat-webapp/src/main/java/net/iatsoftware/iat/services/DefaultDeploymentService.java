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

import net.iatsoftware.iat.communication.ReplyChannel;
import net.iatsoftware.iat.communication.SessionState;
import net.iatsoftware.iat.deployment.DefaultIATDeployer;
import net.iatsoftware.iat.deployment.IATDeployer;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.User;
import net.iatsoftware.iat.events.BeginDeploymentEvent;
import net.iatsoftware.iat.events.DeploymentFailedEvent;
import net.iatsoftware.iat.events.DeploymentSuccessEvent;
import net.iatsoftware.iat.events.DeploymentCleanupEvent;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.Properties;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Service
@EnableAsync
public class DefaultDeploymentService implements DeploymentService {

    private static final Logger critical = LogManager.getLogger("critical");
    @Inject
    IATRepositoryManager iatRepositoryManager;
    @Inject
    ApplicationEventPublisher publisher;
    @Inject
    @Named("ServerConfiguration")
    Properties serverConfiguration;
    @Inject
    WebApplicationContext context;
    @Inject
    ObjectFactory<DefaultIATDeployer> deployerFactory;

    private final Cache<Long, IATDeployer> deploymentCache = Caffeine.newBuilder()
            .expireAfterAccess(DeploymentSession.DEPLOYMENT_TIMEOUT, java.util.concurrent.TimeUnit.MILLISECONDS)
            .removalListener((Long key, IATDeployer deployer, RemovalCause cause) -> {
                if (deployer != null) {
                    critical.error("Deployment session " + key + " removed from cache due to " + cause);
                    deployer.abort();
                    iatRepositoryManager.deleteDeploymentSession(deployer.getDeploymentId());
                }
            }).build();
            
    @Override
    public IATDeployer getDeployer(long deploymentId) {
        return deploymentCache.getIfPresent(deploymentId);
    }           

    @Override
    public void setWebSocketSessionState(long deploymentId, SessionState state) {
        IATDeployer deployer = deploymentCache.getIfPresent(deploymentId);
        if (deployer != null) {
            deployer.setSession(state);
        }
    }


    @Override
    public long beginNewDeployment(Client c, User u, String testName, SessionState session, ReplyChannel replyChannel) throws java.io.IOException, java.net.URISyntaxException {
        try {
            IAT test = new IAT(c, u, testName, serverConfiguration.getProperty("admin-version"),
                    Integer.parseInt(serverConfiguration.getProperty("data-format-version")),
                    Calendar.getInstance());
            DeploymentSession ds = new DeploymentSession(c, u, test);
            iatRepositoryManager.addTest(test);
            session.setTest(test);
            iatRepositoryManager.storeDeploymentSession(ds);
            var deployment = deployerFactory.getObject();        
            deployment.setClientId(c.getClientId());
            deployment.setDeploymentId(ds.getId());
            deployment.setTestId(test.getId());
            deployment.setSession(session);
            deployment.setReplyChannel(replyChannel);
            session.setDeploymentId(ds.getId());
            session.setIatName(testName);
            deploymentCache.put(ds.getId(), deployment);
            return ds.getId();
        } catch (jakarta.xml.bind.JAXBException ex) {
            return -1;
        }
    }



    @Override
    public void completeDeployment(DeploymentSession ds) {
        iatRepositoryManager.finalizeDeployment(ds.getId());
        deploymentCache.invalidate(ds.getId());
    }

    @EventListener
    public void beginDeployment(BeginDeploymentEvent evt) {
        var deployer = deploymentCache.getIfPresent(evt.getDeploymentSessionID());
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
        var deployer = deploymentCache.getIfPresent(e.getDeploymentID());
        deployer.replyChannel().send(new TransactionRequest(TransactionType.TRANSACTION_FAIL));
        deploymentCache.invalidate(e.getDeploymentID());
    }

    @EventListener
    public void onDeploymentSuccess(DeploymentSuccessEvent e) {
        var test = iatRepositoryManager.getIAT(e.getTestId());
        deploymentCache.invalidate(e.getDeploymentID());
        iatRepositoryManager.deleteDeploymentSession(test);
        var deployer = deploymentCache.getIfPresent(e.getDeploymentID());
        deployer.replyChannel().send(new TransactionRequest(TransactionType.TRANSACTION_SUCCESS));
    }

    @EventListener
    public void onDeploymentCleanup(DeploymentCleanupEvent evt) {
        var ds = iatRepositoryManager.getDeploymentSession(evt.getDeploymentSessionID());
        iatRepositoryManager.deleteIAT(ds.getTest());
        deploymentCache.invalidate(ds.getId());
    }
}
