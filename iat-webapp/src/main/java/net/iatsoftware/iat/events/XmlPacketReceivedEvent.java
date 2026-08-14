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
import org.springframework.web.socket.WebSocketSession;
    
public class XmlPacketReceivedEvent extends ApplicationEvent {
    private final XmlPacket packet;
    private final WebSocketSession session;
    public XmlPacketReceivedEvent(WebSocketSession sess, XmlPacket p) {
        super(sess);
        this.packet = p;
        this.session = sess;
    }
    
    public WebSocketSession getSession() {
        return this.session;
    }
    
    public XmlPacket getPacket() {
        return this.packet;
    }
}
