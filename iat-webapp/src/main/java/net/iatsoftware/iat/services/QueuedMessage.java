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

import net.iatsoftware.iat.messaging.Packet;

public interface QueuedMessage extends OutboundMessage {
    void queuePacket(Packet p);
}
