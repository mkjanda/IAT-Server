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

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.util.Base64;
import java.security.spec.RSAPublicKeySpec;
import java.security.PublicKey;
import javax.crypto.Cipher;
import java.util.Properties;
import java.util.Random;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "Handshake")
@XmlAccessorType(XmlAccessType.NONE)
public class Handshake extends net.iatsoftware.iat.generated.HandshakePojo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger();
    private static Cipher cipher;
    private static final Base64.Decoder b64Decoder = Base64.getDecoder();
    private static final Base64.Encoder b64Encoder = Base64.getEncoder();
    private String origValue;

    static {
        try {
            Properties handshakeProperties = PropertiesLoaderUtils.loadAllProperties("handshake.properties");
            byte[] buff;
            byte[] modulusBytes = b64Decoder.decode(handshakeProperties.getProperty("handshake.modulus"));
            if (modulusBytes[0] < 0) {
                buff = new byte[modulusBytes.length + 1];
                buff[0] = 0;
                System.arraycopy(modulusBytes, 0, buff, 1, modulusBytes.length);
                modulusBytes = buff;
            }
            byte[] exponentBytes = b64Decoder.decode(handshakeProperties.getProperty("handshake.exponent"));
            if (exponentBytes[0] < 0) {
                buff = new byte[exponentBytes.length + 1];
                buff[0] = 0;
                System.arraycopy(exponentBytes, 0, buff, 1, exponentBytes.length);
                exponentBytes = buff;
            }
            BigInteger modulus = new BigInteger(modulusBytes);
            BigInteger exponent = new BigInteger(exponentBytes);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        } catch (Exception ex) {
            logger.error("Error creating handshake cipher", ex);
        }
    }

    public Handshake() {
    }

    public static Handshake createOutHand() {
        try {
            Handshake hs = new Handshake();
            Random rand = new Random();
            byte[] randomBytes = new byte[200];
            rand.nextBytes(randomBytes);
            hs.origValue = b64Encoder.encodeToString(randomBytes);
            hs.setValue(b64Encoder.encodeToString(cipher.doFinal(randomBytes)));
            return hs;
        } catch (Exception ex) {
            logger.error("Error generating outgoing handshake", ex);
            return null;
        }
    }

    public boolean checkInHand(Handshake inHand) {
        return (inHand.getValue().equals(this.origValue));
    }
}
