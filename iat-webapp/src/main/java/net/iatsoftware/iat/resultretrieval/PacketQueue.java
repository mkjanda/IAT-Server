/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultretrieval;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.messaging.Packet;

public interface PacketQueue extends Runnable {
    void startQueue(String sessionId);
    void queueData(byte[] data);
    void queuePacket(Packet p);
    void stopQueue();
}