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

import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import java.util.Base64;

@XmlRootElement(name="EncryptedDESCipher")
@XmlAccessorType(XmlAccessType.NONE)
public class EncryptedDESCipher extends net.iatsoftware.iat.generated.EncryptedDESCipherPojo {
    private final Base64.Encoder encoder = Base64.getEncoder();
    private final byte[] cipherBytes, ivBytes;

    public EncryptedDESCipher() {
        cipherBytes = ivBytes = null;
        cipher = iv = "";
    }

    public EncryptedDESCipher(byte[] cBytes, byte[] iBytes) {
        cipherBytes = cBytes;
        ivBytes = iBytes;
    }

    @Override
    public boolean doBeforeMarshal(Marshaller m) {
        cipher = encoder.encodeToString(cipherBytes);
        iv = encoder.encodeToString(ivBytes);
        return true;
    }
}
