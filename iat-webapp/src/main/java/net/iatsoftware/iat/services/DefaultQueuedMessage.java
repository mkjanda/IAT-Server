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

import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.Packet;

import java.util.concurrent.ConcurrentLinkedQueue;

public class DefaultQueuedMessage implements QueuedMessage {
    private final ConcurrentLinkedQueue<Packet> packetQueue = new ConcurrentLinkedQueue<>();
    private final boolean closeOnComplete;
    private boolean complete;
    
    public DefaultQueuedMessage(boolean closeOnComplete) {
        this.closeOnComplete = closeOnComplete;
    }
    
    @Override
    public boolean isCloseOnComplete() {
        return this.closeOnComplete;
    }
    
    @Override
    public void queuePacket(Packet packet) {
        this.packetQueue.add(packet);
    }
    
    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public Envelope getMessage() {
        Packet p = null;
        if ((p = this.packetQueue.poll()) != null) {
            if (p.isIsLastPacket() || (p.isIsErrorPacket() || (p.isIsNullPacket())))
                complete = true;
            return new Envelope(p);
        }
        return null;
    }
}
