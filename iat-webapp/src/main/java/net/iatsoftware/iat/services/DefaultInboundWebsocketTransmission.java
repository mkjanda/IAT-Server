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
/*
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.XmlPacket;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.Scope;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.inject.Inject;
import javax.xml.transform.stream.StreamSource;

@Component("InboundWebsocketTransmission")
@Scope("prototype")
public class DefaultInboundWebsocketTransmission implements InboundWebsocketTransmission {
    private static final Logger logger = LogManager.getLogger();
    private final ConcurrentLinkedQueue<XmlPacket> packetQueue = new ConcurrentLinkedQueue();
    
    @Inject Unmarshaller unmarshaller;
    
    public DefaultInboundWebsocketTransmission(){}
    
    @Override
    public void queueInboundTransmission(XmlPacket p) {
        packetQueue.add(p);
    }
    
    @Override
    public Envelope processInboundQueue() throws org.springframework.oxm.UnmarshallingFailureException,
            java.io.IOException, java.lang.ClassCastException {
                String data = "";
        try {
            XmlPacket p = packetQueue.remove();
            while (!p.isLastPacket()) {
                data += p.getStringData();
                p = packetQueue.remove();
            }
            data += p.getStringData();
            StringReader sReader = new StringReader(data);
            StreamSource source = new StreamSource(sReader);
            return (Envelope) unmarshaller.unmarshal(source);
        } catch (org.springframework.oxm.UnmarshallingFailureException | java.io.IOException | java.lang.ClassCastException ex) {
            logger.error("data :" + data, ex);
            throw ex;
        }
    }
}
*/