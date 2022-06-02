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
import net.iatsoftware.iat.events.DeploymentSuccessEvent;
import net.iatsoftware.iat.events.DeploymentFailedEvent;
import net.iatsoftware.iat.events.TestDeploymentCompleteEvent;
import net.iatsoftware.iat.events.WebSocketFinalDataSent;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;
import net.iatsoftware.iat.messaging.TransactionRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.transform.stream.StreamSource;

@Component(value = "DefaultIATRedeployer")
@Scope(value = "prototype")
public class DefaultIATRedeployer extends DefaultBaseIATDeployer implements IATRedeployer {

    private final Long oldTestID;

    public DefaultIATRedeployer(Long clientID, Long deploymentID, Long replacementTestID, Long testID, String sessID) {
        super(clientID, deploymentID, replacementTestID, sessID);
        this.oldTestID = testID;
    }

    @Override
    protected void onSuccess(String sessionId) {
        iatRepositoryManager.reassociateResults(this.testId, this.oldTestID);
        var test = this.iatRepositoryManager.getIAT(this.testId);
        iatRepositoryManager.finalizeDeployment(deploymentSessionId);
        test.setRedeployed(true);
        iatRepositoryManager.updateIAT(test);
        this.eventPublisher.publishEvent(new DeploymentSuccessEvent(sessionId, deploymentSessionId));
    }

    @Override
    protected void onFailure(String sessionId, ServerExceptionMessage ex) {
        this.eventPublisher.publishEvent(new DeploymentFailedEvent(sessionId, deploymentSessionId, ex, testId));
    }

    @Override
    public void onDescriptorMismatch(String sessionId) {
        this.iatRepositoryManager.deleteIAT(testId);
        this.eventPublisher.publishEvent(new WebSocketFinalDataSent(sessionId,
                new Envelope(new TransactionRequest(TransactionType.DEPLOYMENT_DESCRIPTOR_MISMATCH))));
    }

    @Override
    public void generateTest(String sessId) {
        final String sessionId = sessId;
        this.scheduler.submit(() -> {
            try {
                IAT oldIAT = iatRepositoryManager.getIAT(this.oldTestID);
                IAT test = iatRepositoryManager.getIAT(this.testId);
                loadTransformSources(test);
                digestTestSegment(IATSource, compiledXSLT.getIATDescriptorX());
                if (SurveySources != null) {
                    for (StreamSource s : SurveySources) {
                        digestTestSegment(s, compiledXSLT.getSurveyDescriptorX());
                    }
                }
                byte[] digest = this.DeploymentDescriptor.digest();
                byte[] oldDigest = oldIAT.getDeploymentDescriptor();
                if (digest.length != oldDigest.length) {
                    onDescriptorMismatch(sessionId);
                    return;
                }
                for (int ctr = 0; ctr < digest.length; ctr++) {
                    if (digest[ctr] != oldDigest[ctr]) {
                        {
                            onDescriptorMismatch(sessionId);
                            return;
                        }
                    }
                }
                doDeploy(test);
                this.eventPublisher.publishEvent(new TestDeploymentCompleteEvent(sessionId, this.deploymentSessionId));

            } catch (DeploymentTerminationException ex) {
                criticalLogger.error("Error deploying IAT", ex);
                this.eventPublisher.publishEvent(new DeploymentFailedEvent(sessionId, this.deploymentSessionId,
                        new ServerExceptionMessage("Deployment Error", ex)));
            }
        });
    }
}
