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

import org.springframework.web.socket.WebSocketSession;

public class WebSocketDataReceived extends WebSocketEvent {
    private final Object message;
    
    
    public WebSocketDataReceived(WebSocketSession session, Object message) {
        super(session, WebSocketEventType.DATA_RECEIVED);
        this.message = message;
    }

    public Object getMessage() {
        return this.message;
    }
}
