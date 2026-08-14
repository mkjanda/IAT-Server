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

import org.springframework.web.socket.WebSocketSession;

public class ItemSlidesDeploymentCompleteEvent extends DeploymentTransactionEvent {
    public ItemSlidesDeploymentCompleteEvent(WebSocketSession session, Long deploymentId) {
        super(session, deploymentId);
    }
    
}
