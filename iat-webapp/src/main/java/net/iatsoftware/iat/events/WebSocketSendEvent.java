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

import net.iatsoftware.iat.messaging.Envelope;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketSendEvent extends WebSocketEvent {
    private final Envelope envelope;
    
    public WebSocketSendEvent(WebSocketSession session, Envelope env) {
        super(session, WebSocketEventType.DATA_SENT);
        this.envelope = env;
    }
    
    public Envelope getData() {
        return this.envelope;
    }
}
