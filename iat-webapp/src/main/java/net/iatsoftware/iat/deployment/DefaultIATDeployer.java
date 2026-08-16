/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.deployment;

/**
 *
 * @author Michael Janda
 */
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.events.DeploymentFailedEvent;
import net.iatsoftware.iat.events.WebSocketSendEvent;
import net.iatsoftware.iat.events.WebSocketFinalSendEvent;
import net.iatsoftware.iat.services.MailService;
import net.iatsoftware.iat.services.WebSocketService;

import org.springframework.web.socket.WebSocketSession;

import jakarta.inject.Inject;

public class DefaultIATDeployer extends DefaultBaseIATDeployer implements IATDeployer {

    @Inject
    MailService mailService;
    @Inject
    WebSocketService webSocketService;



    @Override
    public void generateTest() {
        try {
            IAT test = iatRepositoryManager.getIAT(this.testId);
            doDeploy(test);
        } catch (DeploymentTerminationException ex) {
            criticalLogger.error("Error generating IAT", ex);
        } catch (Exception ex) {
            criticalLogger.error("Error generating IAT", ex);
       //     webSocketService.sendEvent(new WebSocketFinalSendEvent(this.session, new Envelope(new ServerExceptionMessage("Error generating IAT: " + ex.getMessage()))));}
        }
    }
}
