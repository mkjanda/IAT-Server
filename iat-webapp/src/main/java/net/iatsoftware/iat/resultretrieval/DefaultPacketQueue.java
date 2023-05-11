/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultretrieval;

/**
 *
 * @author Michael Janda
 */
/*
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Date;
import java.util.concurrent.Future;
import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.iatsoftware.iat.messaging.Packet;
import net.iatsoftware.iat.generated.PacketType;
import net.iatsoftware.iat.services.QueuedMessage;
import net.iatsoftware.iat.services.WebSocketService;

@Component(value="DefaultPacketQueue")
@Scope(value = "prototype")
public class DefaultPacketQueue implements PacketQueue {

    private static final Logger logger = LogManager.getLogger();
    private final ConcurrentLinkedQueue<Packet> packetQueue = new ConcurrentLinkedQueue<>();
    private Future<?> packetFuture;
    private final byte[] queuedData = new byte[5020];
    private int queuedDataLength = 0;
    private String sessionId;
    private final PacketType packetType;
    private QueuedMessage queuedMessage = null;
    private final boolean closeOnComplete;

    @Inject
    ThreadPoolTaskScheduler scheduler;
    @Inject
    ApplicationEventPublisher publisher;
    @Inject
    WebSocketService webSocketService;
    
    public DefaultPacketQueue(PacketType pType, boolean closeOnComplete) {
        this.packetType = pType;
        this.closeOnComplete = closeOnComplete;
    }

    @Override
    public void startQueue(String sessionId) {
        this.sessionId = sessionId;
        this.packetFuture = this.scheduler.scheduleWithFixedDelay(this, new Date(System.currentTimeMillis() + 100L), 250);
        this.queuedMessage = webSocketService.beginOutboundTransmission(this.sessionId, this.closeOnComplete);
    }

    @Override
    synchronized public void queueData(byte[] data) {
        int dataNdx = 0;
        while (queuedDataLength + (data.length - dataNdx) >= queuedData.length) {
            System.arraycopy(data, dataNdx, queuedData, queuedDataLength, queuedData.length - queuedDataLength);
            synchronized (this) {
                packetQueue.add(new Packet(queuedData, packetType));
            }
            dataNdx += queuedData.length - queuedDataLength;
            queuedDataLength = 0;
        }
        System.arraycopy(data, dataNdx, queuedData, queuedDataLength, data.length - dataNdx);
        queuedDataLength += data.length - dataNdx;
    }

    @Override
    public void queuePacket(Packet p) {
        packetQueue.add(p);
    }

    @Override
    public void stopQueue() {
        Packet p;
        if (queuedDataLength > 0) {
            byte[] data = new byte[queuedDataLength];
            System.arraycopy(queuedData, 0, data, 0, queuedDataLength);
            p = new Packet(data, packetType);
        } else {
            p = new Packet(null, packetType);
        }
        p.setIsLastPacket(true);
        packetQueue.add(p);
    }

    @Async
    @Override
    public void run() {
        while (packetQueue.size() > 0) {
            Packet p = packetQueue.remove();
            this.queuedMessage.queuePacket(p);
            if (p.isIsLastPacket()) {
                this.packetFuture.cancel(false);
            }
        }
    }
}
*/