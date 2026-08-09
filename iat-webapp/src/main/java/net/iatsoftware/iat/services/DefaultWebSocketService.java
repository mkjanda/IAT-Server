/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.services;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.communication.SessionState;
import net.iatsoftware.iat.communication.TransactionContext;
import net.iatsoftware.iat.communication.TransactionHandler;
import net.iatsoftware.iat.communication.WebSocketReplyChannel;
import net.iatsoftware.iat.communication.WebSocketSessionState;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.events.WebSocketDataReceived;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.ActivationRequest;
import net.iatsoftware.iat.repositories.ClientRepositoryManager;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.inject.Inject;

@Service("WebSocketService")
public class DefaultWebSocketService implements WebSocketService {

    private static final Logger logger = LogManager.getLogger(DefaultWebSocketService.class);

    @Inject
    ApplicationEventPublisher publisher;
    @Inject ClientRepositoryManager repositoryManager;
    @Inject IATRepositoryManager iatRepositoryManager;
    @Inject List<TransactionHandler> handlers;
    @Inject MailService mailService;

    @EventListener
    public void onMessageReceived(WebSocketDataReceived e) {
        try {
        Envelope env = e.getEnvelope();
        String productKey = env.getMessage().getProductKey();
        Client client = repositoryManager.getClientByProductKey(productKey);
        if (!e.session().getAttributes().containsKey("SessionState")) {
            e.session().getAttributes().put("SessionState", new WebSocketSessionState(e.session()));
        }
        SessionState session = (SessionState) e.session().getAttributes().get("SessionState");
        session.setClient(client);
        session.setClientRepositoryManager(repositoryManager);
        session.setRepositoryManager(iatRepositoryManager);
        session.setMailService(mailService);
        var ctx = new TransactionContext(e.session(), e.getEnvelope().getMessage(), new WebSocketReplyChannel(e.session(), this.publisher), session);
        if (client == null) {
            logger.error("Received message from unknown client with product key " + productKey);
            e.session().close();
            return;
        }
        if (!(env.getMessage() instanceof ActivationRequest) && client.getUsers().isEmpty()) {
            e.session().close();
            return;
        }
        if (client.isFrozen() || client.isDeleted() || client.isKillFiled()) {
            logger.error("Received message from frozen or deleted client with product key " + productKey);
            e.session().close();
            return;
        }
            handlers.forEach(h -> {
                if (h.supports(ctx)) {
                    h.handle(ctx);
                }
            });
        } catch (Exception ex) {
            logger.error("Error processing client message", ex);
        }
    }
}
