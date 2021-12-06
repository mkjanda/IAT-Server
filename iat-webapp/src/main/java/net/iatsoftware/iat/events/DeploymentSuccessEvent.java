/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.events;

/**
 *
 * @author michael
 */
public class DeploymentSuccessEvent extends DeploymentTransactionEvent {
    public DeploymentSuccessEvent(String sessId, long deploymentId) {
        super(sessId, deploymentId);
    }
}
