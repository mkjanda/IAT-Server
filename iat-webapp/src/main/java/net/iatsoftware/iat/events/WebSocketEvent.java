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

public class WebSocketEvent extends ApplicationEvent {
    private final String sessionId;
    private final WebSocketEventType eventType;
    
    public WebSocketEvent(String sessId, WebSocketEventType eventType) {
        super(sessId);
        this.sessionId = sessId;
        this.eventType = eventType;
    }
    
    public WebSocketEventType getEventType() {
        return this.eventType;
    }
    
    public String getSessionId() {
        return this.sessionId;
    }
}
