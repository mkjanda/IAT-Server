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

import org.springframework.context.ApplicationEvent;

public abstract class DeploymentEvent extends ApplicationEvent {
    
    private final Long deploymentSessionID;
    
    public DeploymentEvent(Long deploymentSession) {
        super(deploymentSession);
        this.deploymentSessionID = deploymentSession;
    }
    
    public Long getDeploymentSessionID() {
        return deploymentSessionID;
    }
}
