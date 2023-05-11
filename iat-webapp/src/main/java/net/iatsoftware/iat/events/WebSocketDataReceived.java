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

public class WebSocketDataReceived extends WebSocketEvent {
    private final Envelope envelope;
    
    public WebSocketDataReceived(String sessId, Envelope env) {
        super(sessId, WebSocketEventType.DATA_RECEIVED);
        this.envelope = env;
    }
    
    public Envelope getEnvelope() {
        return this.envelope;
    }
}
