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

import net.iatsoftware.iat.messaging.ServerException;

public class DeploymentFailedEvent extends DeploymentCompleteEvent {
    private final ServerException failureCause;
    
    public DeploymentFailedEvent(String sess, long deploymentId, ServerException failureCause) {
        super(sess, deploymentId, DeploymentCompleteEvent.EResult.failure);
        this.failureCause = failureCause;
    }
    
    public ServerException getFailureCause() {
        return this.failureCause;
    }
}
