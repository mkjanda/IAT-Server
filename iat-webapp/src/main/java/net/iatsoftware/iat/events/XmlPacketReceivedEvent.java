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

import net.iatsoftware.iat.messaging.XmlPacket;

import org.springframework.context.ApplicationEvent;

public class XmlPacketReceivedEvent extends ApplicationEvent {
    private final XmlPacket packet;
    private final String sessionId;
    public XmlPacketReceivedEvent(String sessId, XmlPacket p) {
        super(sessId);
        this.packet = p;
        this.sessionId = sessId;
    }
    
    public String getSessionId() {
        return this.sessionId;
    }
    
    public XmlPacket getPacket() {
        return this.packet;
    }
}
