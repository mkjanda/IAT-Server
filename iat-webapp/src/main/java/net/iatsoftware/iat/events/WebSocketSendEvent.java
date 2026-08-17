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

import net.iatsoftware.iat.messaging.Message;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketSendEvent extends WebSocketEvent {
    private final Message message;
    
    public WebSocketSendEvent(WebSocketSession session, Message message) {
        super(session, WebSocketEventType.DATA_SENT);
        this.message = message;
    }
    
    public Message getData() {
        return this.message;
    }
}
