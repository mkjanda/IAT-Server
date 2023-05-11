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
import net.iatsoftware.iat.messaging.ServerExceptionMessage;

public class DeploymentCleanupEvent extends DeploymentEvent {
    private ServerExceptionMessage message = null;

    public DeploymentCleanupEvent(Long dsID, Exception ex) {
        super(dsID);
        message = new ServerExceptionMessage(ex.getMessage(), ex);
    }

    public DeploymentCleanupEvent(Long dsID, ServerException ex) {
        super(dsID);
        message = ex.getExceptionMessage();
    }

    public ServerExceptionMessage getExceptionMessage() {
        return message;
    }
}
