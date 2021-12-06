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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("FinalMessage")
@Scope("prototype")
public class FinalMessage extends SingleMessage  {
    public FinalMessage(Envelope msg) {
        super(msg);
    }

    @Override
    public boolean isCloseOnComplete() {
        return true;
    }
}
