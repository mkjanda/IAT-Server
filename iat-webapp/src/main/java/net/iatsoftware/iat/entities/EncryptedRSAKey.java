/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.entities;

/**
 *
 * @author Michael Janda
 */


import java.math.BigInteger;
import java.util.Base64;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;

@XmlRootElement(name = "EncryptedRSAKey")
@XmlAccessorType(XmlAccessType.NONE)
@Entity
@Table(name = "test_encryption_keys", indexes = {
    @Index(name = "encryption_keys_test_id", columnList = "TestID")
})
public class EncryptedRSAKey extends net.iatsoftware.iat.generated.GEncryptedRSAKey implements java.io.Serializable {
    private static final long serialVersionUID = 1;
    private long id;
    private IAT test;
    private byte[] exponentBytes, modulusBytes, keyBytes;
    @Override 
    public void doAfterUnmarshal(Unmarshaller um, Object parent) {
        Base64.Decoder decoder = Base64.getDecoder();
        
        byte[] data = decoder.decode(this.encryptedKey);
        this.keyBytes = new byte[data.length + 1];
        this.keyBytes[0] = 0;
        System.arraycopy(data, 0, this.keyBytes, 1, data.length);

        data = decoder.decode(this.exponent);
        this.exponentBytes = new byte[data.length + 1];
        this.exponentBytes[0] = 0;
        System.arraycopy(data, 0, this.exponentBytes, 1, data.length);
        
        
        data = decoder.decode(this.modulus);
        this.modulusBytes = new byte[data.length + 1];
        this.modulusBytes[0] = 0;
        System.arraycopy(data, 0, this.modulusBytes, 1, data.length);

    }
    
    @Override
    public boolean doBeforeMarshal(Marshaller m) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] data = new byte[exponentBytes.length - 1];
        System.arraycopy(this.exponentBytes, 1, data, 0, data.length);
        this.exponent = encoder.encodeToString(data);
        data = new byte[modulusBytes.length - 1];
        System.arraycopy(this.modulusBytes, 1, data, 0, data.length);
        this.modulus = encoder.encodeToString(data);
        this.encryptedKey = encoder.encodeToString(this.keyBytes);
        return true;
    }

 
    public EncryptedRSAKey() {
    }

    static public EncryptedRSAKey createNullKey() {
        EncryptedRSAKey key = new EncryptedRSAKey();
        key.exponent = "NULL";
        key.modulus = "NULL";
        key.encryptedKey = "NULL";
        return key;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KeyID")
    public long getId() {
        return this.id;
    }

    public void setId(long val) {
        this.id = val;
    }

    @OneToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "TestID")
    public IAT getTest() {
        return this.test;
    }

    public void setTest(IAT val) {
        this.test = val;
    }

    @Basic
    @Column(name = "modulus")
    public byte[] getModulusBytes() {
        if (modulusBytes == null) {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] modBytes = decoder.decode(this.modulus);
            this.modulusBytes = new byte[modBytes.length + 1];
            this.modulusBytes[0] = 0;
            System.arraycopy(modBytes, 0, this.modulusBytes, 1, modBytes.length);
        }
        return modulusBytes;
    }

    public void setModulusBytes(byte[] val) {
        modulusBytes = val;
    }

    @Basic
    @Column(name = "exponent")
    public byte[] getExponentBytes() {
        if (this.exponentBytes == null) {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] expBytes = decoder.decode(this.exponent);
            this.exponentBytes = new byte[expBytes.length + 1];
            this.exponentBytes[0] = 0;
            System.arraycopy(expBytes, 0, this.exponentBytes, 1, expBytes.length);
        }
        return this.exponentBytes;
    }

    public void setExponentBytes(byte[] val) {
        this.exponentBytes = val;
    }

    @Basic
    @Column(name = "encrypted_key")
    public byte[] getEncryptedKeyBytes() {
        if (this.keyBytes == null) {
            Base64.Decoder decoder = Base64.getDecoder();
            this.keyBytes = decoder.decode(this.encryptedKey);
        }
        return this.keyBytes;
    }

    public void setEncryptedKeyBytes(byte[] val) {
        this.keyBytes = val;
    }

    @Transient 
    public byte[] publicEncrypt(byte[] data) {
        BigInteger bi = new BigInteger(this.modulusBytes);
        BigInteger exp = new BigInteger(this.exponentBytes);
        BigInteger message = new BigInteger(data);
        BigInteger encrypted = message.modPow(exp, bi);
        return encrypted.toByteArray();
    }
}
