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
    
    public DeploymentTransactionEvent(Long deploymentID) {
        super(deploymentID);
        this.deploymentID = deploymentID;
    }

    public Long getDeploymentID() {
        return this.deploymentID;
    }
}
