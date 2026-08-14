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
import org.springframework.web.socket.WebSocketSession;



public abstract class DeploymentTransactionEvent extends ApplicationEvent {
            
    private final Long deploymentID;
    private final WebSocketSession session; 
    
    public DeploymentTransactionEvent(WebSocketSession session, Long deploymentID) {
        super(session);
        this.deploymentID = deploymentID;
        this.session = session;
    }
    
    public WebSocketSession getSession() {
        return this.session;
    }
    
    public Long getDeploymentID() {
        return this.deploymentID;
    }
}
