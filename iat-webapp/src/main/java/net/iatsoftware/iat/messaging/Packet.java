/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.generated.PacketType;

import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import java.util.Base64;

@XmlRootElement(name="Packet")
@XmlAccessorType(XmlAccessType.NONE)
public class Packet extends net.iatsoftware.iat.generated.PacketPojo {
    protected byte[] byteData = null;

    public Packet() {
        type = PacketType.NONE;
    }

    public Packet(byte[] packetData, PacketType type) {
        this.type = type;
        if (packetData != null) {
            byteData = new byte[packetData.length];
            System.arraycopy(packetData, 0, byteData, 0, byteData.length);
        } else {
            byteData = null;
            isNullPacket = true;
        }
        isErrorPacket = false;
        isLastPacket = false;
    }

    @Override
    public boolean doBeforeMarshal(Marshaller m) {
        if (byteData == null) {
            data = "";
            length = 0;
            if (!isErrorPacket) {
                isNullPacket = true;
            }
        } else {
            Base64.Encoder encoder = Base64.getEncoder();
            data = encoder.encodeToString(byteData);
            length = byteData.length;
            isNullPacket = false;
        }
        return true;
    }

    @Override
    public void doAfterUnmarshal(Unmarshaller um, Object parent) {
        Base64.Decoder decoder = Base64.getDecoder();
        byteData = decoder.decode(data);
    }

    public byte[] getByteData() {
        return byteData;
    }
}
