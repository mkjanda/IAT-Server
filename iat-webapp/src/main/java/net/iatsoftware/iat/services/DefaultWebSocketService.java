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
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.events.CommunicationEvent;
import net.iatsoftware.iat.events.ItemSlideManifestReceivedEvent;
import net.iatsoftware.iat.events.ManifestReceivedEvent;
import net.iatsoftware.iat.events.RSAKeyReceivedEvent;
import net.iatsoftware.iat.events.TokenDefinitionReceivedEvent;
import net.iatsoftware.iat.events.WebSocketDataReceived;
import net.iatsoftware.iat.events.WebSocketDataSent;
import net.iatsoftware.iat.events.WebSocketEvent;
import net.iatsoftware.iat.events.WebSocketEventType;
import net.iatsoftware.iat.events.WebSocketFinalDataSent;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.messaging.RSAKeyPair;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;
import net.iatsoftware.iat.messaging.TokenDefinition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;

@Service("WebSocketService")
public class DefaultWebSocketService implements WebSocketService {
    class WebSocketSessionWrapper {
        private volatile WebSocketSession sess;
        public WebSocketSessionWrapper(WebSocketSession sess) {
            this.sess = sess;
        }
        public WebSocketSession getSession() {
            return this.sess;
        }
    }
    private static final Logger logger = LogManager.getLogger();
    private static final Logger transLogger = LogManager.getLogger("transactions");
    private static final ConcurrentHashMap<String, OutboundWebsocketTransmission> outboundTransmissions = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, WebSocketSessionWrapper> sessionMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Long> sessionStartTimeMap = new ConcurrentHashMap<>();

    @Inject
    ApplicationEventPublisher publisher;
    @Inject
    Marshaller marshaller;
    @Inject
    Unmarshaller unmarshaller;
    @Inject
    MyBeanFactory beanFactory;

    @EventListener
    public void onApplicationEvent(WebSocketEvent e) {
        Envelope env = null;
        try {
            if (e instanceof WebSocketDataReceived) {
                try {
                    WebSocketDataReceived receivedData = (WebSocketDataReceived) e;
                    env = receivedData.getEnvelope();
                    Long deploymentID = (Long) getSessionProperty(e.getSessionId(), "DeploymentID");
                    if (deploymentID != null) {
                        Client c = (Client)getSessionProperty(e.getSessionId(), "Client");
                        String logMsgBase = "Client (" + Long.toString(c.getClientId()) + ") " + c.getProductKey() + ": ";
                        if (env.getMessage() instanceof RSAKeyPair) {
                            transLogger.info(logMsgBase, "RSA Key pair upload");
                            this.publisher.publishEvent(new RSAKeyReceivedEvent(e.getSessionId(), deploymentID, (RSAKeyPair) env.getMessage()));
                        } else if (env.getMessage() instanceof Manifest) {
                            transLogger.info(logMsgBase + "Manifest received message dispatching");
                            this.publisher.publishEvent(new ManifestReceivedEvent(e.getSessionId(), deploymentID, (Manifest) env.getMessage()));
                        } else if (env.getMessage() instanceof TransactionRequest) {
                            transLogger.info(logMsgBase + "Upload request message dispatching");
                            this.publisher.publishEvent(new CommunicationEvent(e.getSessionId(), env.getMessage()));
                        } else if (env.getMessage() instanceof TokenDefinition) {
                            transLogger.info(logMsgBase + "Token Definition Received");
                            this.publisher.publishEvent(new TokenDefinitionReceivedEvent(e.getSessionId(), deploymentID, (TokenDefinition) env.getMessage()));
                        } else {
                            throw new IllegalArgumentException("Unrecognized deployment message");
                        }
                    } else {
                        this.publisher.publishEvent(new CommunicationEvent(e.getSessionId(), env.getMessage()));
                    }
                } catch (Exception ex) {
                    OutboundWebsocketTransmission outTrans = outboundTransmissions.get(e.getSessionId());
                    logger.error("Error processing server-side inbound transmission", ex);
                    outTrans.sendFinalMessage(new Envelope(new ServerExceptionMessage("Error processing server-side inbound transmission", ex)));
                }
            } else if (e instanceof WebSocketFinalDataSent) {
                logger.error("Preparing to send final data.");
                WebSocketFinalDataSent sentData = (WebSocketFinalDataSent) e;
                String sessId = e.getSessionId();
                OutboundWebsocketTransmission outTrans = outboundTransmissions.get(sessId);
                if (outTrans == null)
                    return;
                outTrans.sendFinalMessage(sentData.getData());
            } else if (e instanceof WebSocketDataSent) {
                WebSocketDataSent sentData = (WebSocketDataSent) e;
                OutboundWebsocketTransmission outTrans = outboundTransmissions.get(e.getSessionId());
                if (outTrans == null)
                    return;
                outTrans.sendSingleMessage(sentData.getData());
            } else if (e instanceof WebSocketEvent) {
                if (e.getEventType() == WebSocketEventType.CLOSE_SOCKET) {
                    String sessId = e.getSessionId();
                    WebSocketSession sess = sessionMap.get(sessId).getSession();
                    if (sess != null) {
                        OutboundWebsocketTransmission outTrans = outboundTransmissions.get(sessId);
                        outTrans.closeOnComplete();
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Error processing client message", ex);
        }
    }

    @Override
    public void registerWebSocket(WebSocketSession sess) {
        sessionStartTimeMap.put(sess.getId(), System.currentTimeMillis());
        outboundTransmissions.put(sess.getId(), beanFactory.outboundWebsocketTransmission(sess));
        sessionMap.put(sess.getId(), new WebSocketSessionWrapper(sess));
    }
    
    @Override
    public WebSocketSession getSocketSession(String sessId) {
        return sessionMap.get(sessId).getSession();
    }

    /*
    @Override
    public void closeAndUnregisterWebSocket(String sessId) {
        OutboundWebsocketTransmission outTrans = outboundTransmissions.get(sessId);
        outTrans.closeOnComplete();
        unregisterWebSocket(sessId);
    }
     */
    @Override
    public void unregisterWebSocket(String sessId) {
        OutboundWebsocketTransmission outTrans = outboundTransmissions.get(sessId);
        sessionStartTimeMap.remove(sessId);
        outboundTransmissions.remove(sessId);
        sessionMap.remove(sessId);
        if (outTrans == null) {
            this.publisher.publishEvent(new WebSocketEvent(sessId, WebSocketEventType.CLOSE_SOCKET));
        } else {
            outTrans.closeOnComplete();
        }
    }

    @Override
    public Object getSessionProperty(String sessID, String property) {
        return sessionMap.get(sessID).getSession().getAttributes().get(property);
    }

    @Override
    public void setSessionProperty(String sessID, String property, Object o) {
        sessionMap.get(sessID).getSession().getAttributes().put(property, o);
    }

    @Scheduled(initialDelay = 3_600_000, fixedDelay = 3_600_000)
    private void removeExpiredSessions() {
        List<String> expiredSessIds = Collections.list(sessionStartTimeMap.keys()).stream().filter((s)
                -> System.currentTimeMillis() - 3600000L >= sessionStartTimeMap.get(s)).collect(Collectors.toList());
        for (String s : expiredSessIds) {
            try {
                sessionMap.get(s).getSession().close();
            } catch (java.io.IOException ex) {
                logger.error("Error closing web socket", ex);
            }
            sessionMap.remove(s);
            outboundTransmissions.remove(s);
            sessionStartTimeMap.remove(s);
        }
    }
}
