/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.services;

/**
 *
 * @author michael
 */

import net.iatsoftware.iat.config.MyBeanFactory;
import net.iatsoftware.iat.events.WebSocketDataReceived;
import net.iatsoftware.iat.events.WebSocketFinalDataSent;
import net.iatsoftware.iat.events.XmlPacketReceivedEvent;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;
import net.iatsoftware.iat.messaging.XmlPacketReceiver;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.xml.transform.stream.StreamSource;

@Service("PacketReceiptService")
public class DefaultPacketReceiptService implements PacketReceiptService {

    private static final ConcurrentHashMap<String, XmlPacketReceiver> receiverMap = new ConcurrentHashMap<>();

    @Inject
    ApplicationEventPublisher publisher;
    @Inject
    Unmarshaller unmarshaller;
    @Inject MyBeanFactory beanFactory;

    @EventListener
    public void onPacketReceived(XmlPacketReceivedEvent e) {
        try {
        XmlPacketReceiver receiver = receiverMap.get(e.getSessionId());
        receiver.queuePacket(e.getPacket());
        if (receiver.isComplete()) {
            StreamSource source = new StreamSource(new StringReader(receiver.getPayload()));
            Envelope env = (Envelope)unmarshaller.unmarshal(source);
            publisher.publishEvent(new WebSocketDataReceived(e.getSessionId(), env));
        }
        }
        catch (java.io.IOException ex) {
            publisher.publishEvent(new WebSocketFinalDataSent(e.getSessionId(), new Envelope(new ServerExceptionMessage("Error processing inbound message", ex))));
        }
    }
    
    
    @Override
    public void registerSession(String sessId) {
        receiverMap.put(sessId, new XmlPacketReceiver(sessId));
    }
    
    @Override
    public void unregisterSession(String sessId) {
        receiverMap.remove(sessId);
    }
}
