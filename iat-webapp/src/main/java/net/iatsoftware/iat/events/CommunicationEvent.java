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

import org.springframework.context.ApplicationEvent;

public class CommunicationEvent extends ApplicationEvent {
    final private String sessID;
    final private Message message;
    
    public CommunicationEvent(String sessID, Message msg) {
        super(sessID);
        this.sessID = sessID;
        this.message = msg;
    }
    
    public Message getMessage() {
        return message;
    }

    public String getSessionId() {
        return this.sessID;
    }
}
