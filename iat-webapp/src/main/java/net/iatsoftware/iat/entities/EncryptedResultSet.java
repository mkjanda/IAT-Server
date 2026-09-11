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

import net.iatsoftware.iat.resultdata.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.oxm.Marshaller;

import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Lob;
import jakarta.persistence.Index;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import javax.xml.transform.stream.StreamResult;

import java.util.Calendar;

import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Entity
@XmlType(name = "EncryptedResultSet")
@XmlAccessorType(XmlAccessType.NONE)   
@Table(name = "results", indexes = {
        @Index(name = "test_id", columnList = "TestID")
})
public class EncryptedResultSet extends net.iatsoftware.iat.generated.GEncryptedResultSet
        implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private static final Base64.Encoder base64Encoder = Base64.getEncoder();
    private long id;
    private Marshaller marshaller;
    private IAT test = null;
    private byte[] resultBytes, encryptedCipherBytes, tagBytes, nonceBytes;
    private Calendar adminTime;
    private static final SecureRandom random = new SecureRandom();
    private static final Logger logger = LogManager.getLogger();

    public EncryptedResultSet() {
    }

    public EncryptedResultSet(Marshaller marshaller, IAT test, ResultSet results) {
        try {
        this.adminTime = Calendar.getInstance();
        this.marshaller = marshaller;
        var bOut = new ByteArrayOutputStream();
        var result = new StreamResult(bOut);
        this.marshaller.marshal(results, result);
        this.resultBytes = bOut.toByteArray();
        this.test = test;
        } catch (Exception e) {
            logger.error("Error marshalling results", e);
            throw new RuntimeException("Error marshalling results", e);
        }
    }

    @Transient
    public void encryptResults() {
        try {
            var aesBytes = new byte[32];
            random.nextBytes(aesBytes);
            this.nonceBytes = new byte[12];
            random.nextBytes(this.nonceBytes);
            var key = new SecretKeySpec(aesBytes, "AES");
            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, this.nonceBytes);
            var cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);
            byte[] ciphertextandtag = cipher.doFinal(this.resultBytes);
            this.resultBytes = new byte[ciphertextandtag.length - 16];
            this.tagBytes = new byte[16];
            var rBytes = new byte[ciphertextandtag.length - 16];
            System.arraycopy(ciphertextandtag, 0, rBytes, 0, rBytes.length);
            System.arraycopy(ciphertextandtag, rBytes.length, tagBytes, 0, 16);
            this.setResultBytes(rBytes);
            this.setEncryptedCipherBytes(this.getTest().getDataKey().publicEncrypt(aesBytes));
            this.setTagBytes(this.getTest().getDataKey().publicEncrypt(this.tagBytes));
            this.setNonceBytes(this.getTest().getDataKey().publicEncrypt(this.nonceBytes));
        } catch (Exception e) {
            logger.error("Error encrypting results", e);
            throw new RuntimeException("Error encrypting results", e);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ResultID")
    public long getId() {
        return this.id;
    }

    public void setId(long val) {
        this.id = val;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "TestID")
    public IAT getTest() {
        return this.test;
    }

    public void setTest(IAT val) {
        this.test = val;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "admin_time")
    public Calendar getAdminTime() {
        return this.adminTime;
    }

    public void setAdminTime(Calendar val) {
        this.adminTime = val;
    }

    @Lob
    @Column(name = "results")
    public byte[] getResultBytes() {
        return this.resultBytes;
    }

    public void setResultBytes(byte[] val) {
        this.resultBytes = val;
    }

    @Basic
    @Column(name = "encrypted_cipher")
    public byte[] getEncryptedCipherBytes() {
        return this.encryptedCipherBytes;
    }

    public void setEncryptedCipherBytes(byte[] val) {
        this.encryptedCipherBytes = val;
    }

    @Basic
    @Column(name = "tag")
    public byte[] getTagBytes() {
        return this.tagBytes;
    }

    public void setTagBytes(byte[] val) {
        this.tagBytes = val;
    }

    @Basic
    @Column(name = "nonce")
    public byte[] getNonceBytes() {
        return this.nonceBytes;
    }

    public void setNonceBytes(byte[] val) {
        this.nonceBytes = val;
    }

    @Transient
    public boolean beforeMarshal(Marshaller m) {
        setNonce(base64Encoder.encodeToString(this.nonceBytes));
        setEncryptedCipher(base64Encoder.encodeToString(this.encryptedCipherBytes));
        setTag(base64Encoder.encodeToString(this.tagBytes));
        setResults(base64Encoder.encodeToString(this.resultBytes));
        return true;
    }

}
