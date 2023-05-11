/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.services;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.messaging.Envelope;

public interface OutboundWebsocketTransmission {
    void sendFinalMessage(Envelope msg);
    void sendSingleMessage(Envelope msg);
    void closeOnComplete();
}
