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

import jakarta.websocket.Session;

public class RestoreCompleteEvent extends DeploymentEvent {
    
    private final Session session;
    
    public RestoreCompleteEvent(Long deploymentSessionID, Session session) {
        super(deploymentSessionID);
        this.session = session;
    }
    
    public Session getSession() {
        return this.session;
    }
}
