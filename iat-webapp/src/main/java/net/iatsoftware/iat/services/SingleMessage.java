/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.services;

/**
 *
 * @author michael
 */
import net.iatsoftware.iat.messaging.Envelope;

public class SingleMessage implements OutboundMessage {
    private final Envelope msg;
    private boolean complete = false;
    
    public SingleMessage(Envelope msg) {
        this.msg = msg;
    }

    @Override
    public boolean isCloseOnComplete() {
        return false;
    }

    @Override
    public boolean isComplete()
    {
        return complete;
    }
    
    @Override
    public Envelope getMessage() 
    {
        complete = true;
        return msg;
    }
}
