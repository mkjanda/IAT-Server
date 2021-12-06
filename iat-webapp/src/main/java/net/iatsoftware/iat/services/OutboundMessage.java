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

public interface OutboundMessage {
    Envelope getMessage();
    boolean isComplete();
    boolean isCloseOnComplete();
}
