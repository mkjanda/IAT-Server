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


public abstract class DeploymentTransactionEvent extends ApplicationEvent {
            
    private final Long deploymentID;
    private final String sessionId;
    
    public DeploymentTransactionEvent(String sessionId, Long deploymentID) {
        super(sessionId);
        this.deploymentID = deploymentID;
        this.sessionId = sessionId;
    }
    
    public String getSessionId() {
        return this.sessionId;
    }
    
    public long getDeploymentID() {
        return this.deploymentID;
    }
}
