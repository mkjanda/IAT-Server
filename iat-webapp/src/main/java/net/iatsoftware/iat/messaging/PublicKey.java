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

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import java.util.Base64;

@XmlRootElement(name = "PublicKey")
@XmlAccessorType(XmlAccessType.NONE)
public class PublicKey extends net.iatsoftware.iat.generated.GPublicKey {

    private static final Base64.Decoder decoder = Base64.getDecoder();
    private byte[] ModulusBytes = null, ExponentBytes = null;

    public PublicKey() {
    }

    public PublicKey(String mod, String exp) {
        modulus = mod;
        exponent = exp;
    }

    public byte[] getModulusBytes() {
        if (ModulusBytes == null) {
            byte[] bytes = decoder.decode(modulus);
            ModulusBytes = new byte[bytes.length + 1];
            ModulusBytes[0] = 0;
            System.arraycopy(bytes, 0, ModulusBytes, 1, bytes.length);
        }
        return ModulusBytes;
    }

    public byte[] getExponentBytes() {
        if (ExponentBytes == null) {
            byte[] bytes = decoder.decode(modulus);
            ExponentBytes = new byte[bytes.length + 1];
            ExponentBytes[0] = 0;
            System.arraycopy(bytes, 0, ExponentBytes, 1, bytes.length);
        }
        return ExponentBytes;
    }
}
