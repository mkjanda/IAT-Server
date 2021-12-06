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

public class DeploymentSessionTerminatedEvent extends DeploymentEvent {
    
    public DeploymentSessionTerminatedEvent(Long dsID) {
        super(dsID);
    }
}
