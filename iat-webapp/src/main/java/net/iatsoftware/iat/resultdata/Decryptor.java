/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultdata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import javax.crypto.Cipher;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import javax.crypto.SecretKey;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 *
 * @author Michael Janda
 */
@XmlAccessorType(XmlAccessType.NONE)
public abstract class Decryptor {
    private static final Logger logger = LogManager.getLogger();
    private static SecretKeyFactory desKeyFactory = null;
    private Cipher keyCipher = null;
    
    static {
        try {
            desKeyFactory = SecretKeyFactory.getInstance("DES");
        }
        catch (java.security.NoSuchAlgorithmException ex) {
            logger.error("Error instantiating key factory for restful result set decryption.", ex);
        }
    }
    
    public Decryptor() {}
    
    public void initDecryptor(Cipher privKeyCipher) {
        this.keyCipher = privKeyCipher;
    }
    
    protected byte[] decryptData(byte[] key, byte[] iv, byte[] data) 
        throws java.security.InvalidKeyException, java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException,
            java.security.spec.InvalidKeySpecException, java.security.InvalidAlgorithmParameterException, java.io.IOException,
            javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
        if (this.keyCipher == null)
            throw new RuntimeException("Result decryptor has not been initialized");
        byte[] keyBytes = this.keyCipher.doFinal(key);
        byte[] ivBytes = this.keyCipher.doFinal(iv);
        
        SecretKey dataKey = desKeyFactory.generateSecret(new DESKeySpec(keyBytes));
        Cipher dataCipher = Cipher.getInstance("DES/CBC/ISO10126PADDING");
        dataCipher.init(Cipher.DECRYPT_MODE, dataKey, new IvParameterSpec(ivBytes));
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        CipherOutputStream cStream = new CipherOutputStream(bOut, dataCipher);
        cStream.write(data);
        cStream.flush();
        cStream.close();
        return bOut.toByteArray();
    }
}
