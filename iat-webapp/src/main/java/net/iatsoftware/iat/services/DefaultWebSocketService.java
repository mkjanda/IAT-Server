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
import net.iatsoftware.iat.messaging.Message;
import net.iatsoftware.iat.communication.TransactionHandler;
import net.iatsoftware.iat.communication.WebSocketReplyChannel;
import net.iatsoftware.iat.communication.WebSocketSessionState;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.events.WebSocketDataReceived;
import net.iatsoftware.iat.events.WebSocketFinalSendEvent;
import net.iatsoftware.iat.events.WebSocketSendEvent;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.ActivationRequest;
import net.iatsoftware.iat.repositories.ClientRepositoryManager;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

import java.io.StringWriter;
import java.util.List;
import javax.xml.transform.stream.StreamResult;

import jakarta.inject.Inject;

@Service("WebSocketService")
public class DefaultWebSocketService implements WebSocketService {

    private static final Logger logger = LogManager.getLogger(DefaultWebSocketService.class);
    private static final Logger critical = LogManager.getLogger("critical");
    @Inject
    ApplicationEventPublisher publisher;
    @Inject ClientRepositoryManager repositoryManager;
    @Inject IATRepositoryManager iatRepositoryManager;
    @Inject List<TransactionHandler> handlers;
    @Inject MailService mailService;
    @Inject Marshaller marshaller;
    @Inject Unmarshaller unmarshaller;

    @EventListener
    public void onMessageReceived(WebSocketDataReceived e) {
        try {
        Message message = (Message)e.getMessage();
        String productKey = message.getProductKey();
        Client client = repositoryManager.getClientByProductKey(productKey);
        if (!e.getSession().getAttributes().containsKey("SessionState")) {
            e.getSession().getAttributes().put("SessionState", new WebSocketSessionState(e.getSession()));
        }
        SessionState session = (SessionState) e.getSession().getAttributes().get("SessionState");
        if (session.client() == null) {
            session.setClient(client);
        }
        client = session.client();
        session.setClientRepositoryManager(repositoryManager);
        session.setRepositoryManager(iatRepositoryManager);
        session.setMailService(mailService);
        session.setMarshaller(marshaller);
        session.setUnmarshaller(unmarshaller);
        var ctx = new TransactionContext(e.getSession(), message, new WebSocketReplyChannel(e.getSession(), this.publisher), session);
        if (client == null) {
            logger.error("Received message from unknown client with product key " + productKey);
            e.getSession().close();
            return;
        }
        if (!(message instanceof ActivationRequest) && client.getUsers().isEmpty()) {
            e.getSession().close();
            return;
        }
        if (client.isFrozen() || client.isDeleted() || client.isKillFiled()) {
            logger.error("Received message from frozen or deleted client with product key " + productKey);
            e.getSession().close();
            return;
        }
            handlers.forEach(h -> {
                if (h.supports(ctx)) {
                    h.handle(ctx);
                }
            });
        } catch (Exception ex) {
            critical.error("Error processing client message", ex);
        }
    }

    private TextMessage buildTextMessage(Message env) throws java.io.IOException {
        var stringWriter = new StringWriter();
        marshaller.marshal(env, new StreamResult(stringWriter));
        return new TextMessage(stringWriter.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    @EventListener
    public void sendMessage(WebSocketSendEvent e) {
        try {
            e.getSession().sendMessage(buildTextMessage(e.getData()));
        } catch (Exception ex) {
            critical.error("Error sending message to client", ex);
        }
    }

    @EventListener
    public void sendFinalMessage(WebSocketFinalSendEvent e) {
        try {
            e.getSession().sendMessage(buildTextMessage(e.getData()));
            e.getSession().close();
        } catch (Exception ex) {
            critical.error("Error sending final message to client", ex);
        }
    }

}
