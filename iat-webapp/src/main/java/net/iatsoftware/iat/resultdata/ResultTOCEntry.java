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

import java.io.IOException;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;


@XmlRootElement(name="ResultTOCEntry")
@XmlAccessorType(XmlAccessType.NONE)
public class ResultTOCEntry extends net.iatsoftware.iat.generated.GResultTOCEntry implements java.io.Serializable {
    final static private long serialVersionUID = 1L;
    
    public ResultTOCEntry() {}
    
    public ResultTOCEntry(long keyOffset, int keyLength, long ivOffset, int ivLength, long dataOffset, int dataLength) {
        this.keyOffset = keyOffset;
        this.keyLength = keyLength;
        this.ivOffset = ivOffset;
        this.ivLength = ivLength;
        this.dataOffset = dataOffset;
        this.dataLength = dataLength;
    }
    
    
    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.writeLong(keyOffset);
        out.writeInt(keyLength);
        out.writeLong(ivOffset);
        out.writeInt(ivLength);
        out.writeLong(dataOffset);
        out.writeInt(dataLength);
    }
    
    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        keyOffset = in.readLong();
        keyLength = in.readInt();
        ivOffset = in.readLong();
        ivLength = in.readInt();
        dataOffset = in.readLong();
        dataLength = in.readInt();
    }
    
}
