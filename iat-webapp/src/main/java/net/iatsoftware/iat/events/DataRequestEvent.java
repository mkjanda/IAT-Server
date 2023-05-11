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

public class DataRequestEvent extends ApplicationEvent {
    private final DataRequestEventType request;
    private final Long clientID;
    private final String testName, sessionId;
    
    public DataRequestEvent(String sessId, Long clientID, String testName, DataRequestEventType request) {
        super(sessId);
        this.sessionId = sessId;
        this.clientID = clientID;
        this.testName = testName;
        this.request = request;
    }
    
    public DataRequestEventType getRequest() {
        return this.request;
    }
    
    public Long getClientID() {
        return this.clientID;
    }
    
    public String getTestName() {
        return this.testName;
    }
    
    public String getSessionId() {
        return this.sessionId;
    }
}
