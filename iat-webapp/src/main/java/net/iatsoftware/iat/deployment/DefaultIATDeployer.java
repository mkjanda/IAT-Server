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
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.events.DeploymentFailedEvent;
import net.iatsoftware.iat.events.WebSocketDataSent;
import net.iatsoftware.iat.events.WebSocketFinalDataSent;
import net.iatsoftware.iat.services.MailService;
import net.iatsoftware.iat.services.WebSocketService;

import javax.inject.Inject;

public class DefaultIATDeployer extends DefaultBaseIATDeployer implements IATDeployer {

    @Inject
    MailService mailService;
    @Inject
    WebSocketService webSocketService;

    public DefaultIATDeployer(Long clientId, Long deploymentId, Long testId, String session) {
        super(clientId, deploymentId, testId, session);
    }

    @Override
    protected void onFailure(String sessionId, ServerExceptionMessage ex) {
        try {
            this.iatRepositoryManager.deleteIAT(this.testId);
        } catch (org.springframework.orm.ObjectOptimisticLockingFailureException failureEx) {
            this.eventPublisher.publishEvent(new WebSocketDataSent(sessionId,
                    new Envelope(new ServerExceptionMessage("Error halting deployment", failureEx))));
        } catch (Exception ex2) {
        }
        if (ex != null) {
            criticalLogger.error("Error deploying iat {}", ex);
            this.eventPublisher.publishEvent(new WebSocketDataSent(sessionId, new Envelope(ex)));
        }
        this.eventPublisher.publishEvent(new WebSocketFinalDataSent(sessionId,
                new Envelope(new TransactionRequest(TransactionType.TRANSACTION_FAIL))));
    }

    @Override
    protected void onSuccess(String sessId) {
        this.iatRepositoryManager.finalizeDeployment(deploymentSessionId);
        this.eventPublisher.publishEvent(new WebSocketFinalDataSent(sessId,
                new Envelope(new TransactionRequest(TransactionType.TRANSACTION_SUCCESS))));
    }

    @Override
    public void generateTest() {
        try {
            IAT test = iatRepositoryManager.getIAT(this.testId);
            doDeploy(test);
        } catch (DeploymentTerminationException ex) {
            criticalLogger.error("Error generating IAT", ex);
        } catch (Exception ex) {
            criticalLogger.error("Error generating IAT", ex);
            this.eventPublisher.publishEvent(new DeploymentFailedEvent(sessionId, this.deploymentSessionId,
                    new ServerExceptionMessage("Deployment Error", ex)));
        }
    }
}
