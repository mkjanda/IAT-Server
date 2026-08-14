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

import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketEvent extends ApplicationEvent {
    private final WebSocketSession session;
    private final WebSocketEventType eventType;
    
    public WebSocketEvent(WebSocketSession session, WebSocketEventType eventType) {
        super(session);
        this.session = session;
        this.eventType = eventType;
    }
    
    public WebSocketEventType getEventType() {
        return this.eventType;
    }
    
    public WebSocketSession getSession() {
        return this.session;
    }
}
