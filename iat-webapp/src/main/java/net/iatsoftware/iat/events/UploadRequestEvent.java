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
import org.springframework.web.socket.WebSocketSession;

public class UploadRequestEvent extends DeploymentTransactionEvent {
    public UploadRequestEvent(WebSocketSession session, Long deploymentSessionID) {
        super(session, deploymentSessionID);
    }
}
