/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.events;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.messaging.ServerExceptionMessage;

public class DeploymentFailedEvent extends DeploymentCompleteEvent {
    private final ServerExceptionMessage failureCause;
    private Long testId = -1L;
    
    public DeploymentFailedEvent(String sess, long deploymentId, ServerExceptionMessage failureCause) {
        super(sess, deploymentId, DeploymentCompleteEvent.EResult.failure);
        this.failureCause = failureCause;
    }

    public DeploymentFailedEvent(String sess, long deploymentId, ServerExceptionMessage failureCause, Long testId) {
        super(sess, deploymentId, DeploymentCompleteEvent.EResult.failure);
        this.failureCause = failureCause;
        this.testId = testId;
    }
    
    public Long getTestId() {
        return this.testId;
    }


    public ServerExceptionMessage getFailureCause() {
        return this.failureCause;
    }
}
