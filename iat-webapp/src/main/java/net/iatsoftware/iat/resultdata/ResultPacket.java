/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultdata;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.generated.PacketType;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import java.util.Calendar;

@XmlRootElement(name="ResultPacket")
@XmlAccessorType(XmlAccessType.NONE)
public class ResultPacket extends net.iatsoftware.iat.generated.ResultPacketPojo {
    public ResultPacket(){}

    public ResultPacket(Long resultID, ResultTOC toc, byte[] resultData, Calendar timestamp, String token) {
        this.byteData = resultData;
        this.type = PacketType.RESULT_DATA;
        this.tableOfContents = toc;
        this.resultID =  resultID;
        this.timestamp = timestamp.toString();
        this.isErrorPacket = this.isNullPacket = this.isLastPacket = false;
        this.token = token;
    }
}
