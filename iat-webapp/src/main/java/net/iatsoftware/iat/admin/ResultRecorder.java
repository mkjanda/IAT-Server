/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.admin;

/**
 *
 * @author Michael Janda
 */
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.Key;
import javax.crypto.spec.IvParameterSpec;
import java.security.spec.RSAPublicKeySpec;
import java.math.BigInteger;
import javax.crypto.CipherOutputStream;
import javax.inject.Inject;
import java.util.Random;

@Component
@Scope(value = "prototype")
public abstract class ResultRecorder {

    protected static final Logger logger = LogManager.getLogger("critical");

    @Inject
    SecretKeyFactory secretKeyFactory;
    @Inject
    KeyFactory keyFactory;
    @Inject
    IATRepositoryManager iatRepositoryManager;

    private byte[] encDESBytes, encIVBytes, encData;
    protected IAT test;
    protected boolean lastFragment;

    public ResultRecorder(IAT test, boolean lastFragment) {
        this.test = test;
        this.lastFragment = lastFragment;
    }

    protected byte[] getEncDESBytes() {
        return encDESBytes;
    }

    protected byte[] getEncIVBytes() {
        return encIVBytes;
    }

    protected byte[] getEncData() {
        return encData;
    }

    protected void encryptResults(byte[] data) {
        try {
            Random rand = new Random();
            byte[] desKeyBytes = new byte[8];
            rand.nextBytes(desKeyBytes);
            DESKeySpec desKeySpec = new DESKeySpec(desKeyBytes);
            SecretKey desKey = secretKeyFactory.generateSecret(desKeySpec);
            Cipher c = Cipher.getInstance("DES/CBC/ISO10126PADDING");
            byte[] ivBytes = new byte[8];
            rand.nextBytes(ivBytes);
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            c.init(Cipher.ENCRYPT_MODE, desKey, ivSpec);
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            CipherOutputStream cOut = new CipherOutputStream(bOut, c);
            cOut.write(data);
            cOut.close();
            encData = bOut.toByteArray();

            PartiallyEncryptedRSAKey testsKey = iatRepositoryManager.getEncryptionKey(test);
            BigInteger modulus = new BigInteger(testsKey.getModulusBytes());
            BigInteger exponent = new BigInteger(testsKey.getExponentBytes());
            RSAPublicKeySpec rsaKeySpec = new RSAPublicKeySpec(modulus, exponent);
            Key rsaKey = keyFactory.generatePublic(rsaKeySpec);
            c = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            c.init(Cipher.ENCRYPT_MODE, rsaKey);
            encDESBytes = c.doFinal(desKeyBytes);
            encIVBytes = c.doFinal(ivBytes);
        } catch (Exception ex) {
            logger.error("Error encrypting result data", ex);
        }
    }
}
