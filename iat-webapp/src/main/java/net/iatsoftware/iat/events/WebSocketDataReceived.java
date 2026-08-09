/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.events;

import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.messaging.Envelope;

public class WebSocketDataReceived extends WebSocketEvent {
    private final Envelope envelope;
    private final WebSocketSession session;

    
    public WebSocketDataReceived(WebSocketSession sess, Envelope env) {
        super(sess.getId(), WebSocketEventType.DATA_RECEIVED);
        this.envelope = env;
        this.session = sess;
    }
    
    public Envelope getEnvelope() {
        return this.envelope;
    }

    public WebSocketSession session() {
        return this.session;
    }
}
