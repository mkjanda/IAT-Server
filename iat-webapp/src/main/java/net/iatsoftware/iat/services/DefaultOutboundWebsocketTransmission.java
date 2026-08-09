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
import net.iatsoftware.iat.config.MyBeanFactory;
import net.iatsoftware.iat.events.OutboundMessageEvent;
import net.iatsoftware.iat.events.OutboundFinalMessageEvent;
import net.iatsoftware.iat.messaging.Envelope;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.oxm.Marshaller;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;

import java.io.StringWriter;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;
import javax.xml.transform.stream.StreamResult;

@Service("OutboundWebsocketTransmission")
public class DefaultOutboundWebsocketTransmission implements OutboundWebsocketTransmission {

    private static final Logger logger = LogManager.getLogger(DefaultOutboundWebsocketTransmission.class);

    @Inject
    Marshaller marshaller;
    @Inject
    WebSocketService webSocketService;



    

    @EventListener
    private void sendMessage(OutboundMessageEvent e) {
        try {
            StringWriter sWriter = new StringWriter();
            StreamResult sResult = new StreamResult(sWriter);
            this.marshaller.marshal(new Envelope(e.getMessage()), sResult);
            e.getSession().sendMessage(new TextMessage(sWriter.toString()));
        } catch (java.io.IOException ex) {
            logger.error("Web socket error", ex);
        } catch (org.springframework.oxm.MarshallingFailureException ex) {
            logger.error("Marshalling error", ex);
        }
    }

    @EventListener
    private void sendFinalMessage(OutboundFinalMessageEvent e) {
        sendMessage(e);
        webSocketService.unregisterWebSocket(e.session().getId();
    }

    private void startTransmission() {
        if (!sendingMessages.compareAndSet(false, true)) {
            return;
        }
        this.scheduler.submit(() -> {
            try {
                sendMessages();
                if (this.closing.get()) {
                    this.session.close();
                }
            } catch (java.io.IOException ex) {
                logger.error("Web socket error", ex);
            }
            this.sendingMessages.set(false);
            if (this.messageQueue.peek() != null) {
                startTransmission();
            }
        });
    }

    @Override
    public void sendFinalMessage(Envelope env) {
        this.queueMessage(new FinalMessage(env));
    }

    @Override
    public void sendSingleMessage(Envelope env) {
        this.queueMessage(new SingleMessage(env));
    }

    private void sendMessages() throws java.io.IOException {
        boolean close = this.closeOnComplete.get();
        OutboundMessage msg = this.messageQueue.poll();
        while (msg != null) {
            this.closing.set(close || msg.isCloseOnComplete());
            Envelope env = msg.getMessage();
            if (env == null) {
                continue;
            }
            StringWriter sWriter = new StringWriter();
            StreamResult sResult = new StreamResult(sWriter);
            this.marshaller.marshal(env, sResult);
            final String msgText = sWriter.toString();
            this.session.sendMessage(new TextMessage(msgText));
            msg = this.messageQueue.poll();
        }
    }
}
