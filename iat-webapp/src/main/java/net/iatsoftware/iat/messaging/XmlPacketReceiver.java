/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

/**
 *
 * @author michael
 */


import java.util.concurrent.ConcurrentHashMap;



public class XmlPacketReceiver {

    private final ConcurrentHashMap<Integer, XmlPacket> packetMap = new ConcurrentHashMap<>();
    private int numPackets = -1;

    public XmlPacketReceiver(String sessionId) {
    }

    public void queuePacket(XmlPacket p) {
        packetMap.put(p.getOrdinal(), p);
        if (p.isLastPacket()) {
            numPackets = p.getOrdinal();
        }
    }

    public boolean isComplete() {
        return (numPackets == packetMap.size());
    }

    public String getPayload() {
        if (packetMap.size() == numPackets) {
            String msg = packetMap.values().stream().sorted((p1, p2) -> p1.getOrdinal() - p2.getOrdinal())
                    .map(t -> t.getStringData()).collect(StringBuffer::new, StringBuffer::append, StringBuffer::append).toString();
            packetMap.clear();
            numPackets = -1;
            return msg;
        } else {
            return null;
        }
    }
}
