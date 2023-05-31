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


import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="XmlPacket")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlPacket extends net.iatsoftware.iat.generated.XmlPacketPojo {
    private static final int maxPacketSize = 5020;
    
    public XmlPacket() {
    }
    
    protected XmlPacket(int ordinal, String data, boolean isLastPacket) {
        this.ordinal = ordinal;
        this.stringData = data;
        this.lastPacket = isLastPacket;
    }
    
    @Override
    public void doAfterUnmarshal(Unmarshaller um, Object parent) {
        if (stringData.length() != length)
            this.stringData = stringData.substring("<![CDATA[".length(), length);
    }
    
    @Override
    public boolean doBeforeMarshal(Marshaller m) {
        this.length = stringData.length();
        return true;
    }
    
    static public List<XmlPacket> createPacketList(String data) {
        List<XmlPacket> packetList = new ArrayList<>();
        String dataCpy = data;
        int pCtr = 0;
        while (dataCpy.length() > maxPacketSize) {
            packetList.add(new XmlPacket(++pCtr, dataCpy.substring(0, maxPacketSize), false));
            dataCpy = dataCpy.substring(maxPacketSize);
        }
        packetList.add(new XmlPacket(++pCtr, dataCpy, true));
        return packetList;
    }
}
