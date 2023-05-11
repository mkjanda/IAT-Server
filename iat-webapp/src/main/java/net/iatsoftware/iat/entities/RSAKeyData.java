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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigInteger;
import java.util.Calendar;
import java.security.SecureRandom;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "rsa_key_data")
@JsonAutoDetect(fieldVisibility=Visibility.NONE, getterVisibility=Visibility.PUBLIC_ONLY, 
    isGetterVisibility=Visibility.PUBLIC_ONLY, setterVisibility=Visibility.NONE)

public class RSAKeyData implements java.io.Serializable {
    private static final long serialVersionUID = 1;
    private long id;
    private byte[] privKey, pubKey, modulus;
    private Calendar generationTimestamp = Calendar.getInstance();
    private static SecureRandom random = new SecureRandom();
    private static int[] primes = new int[] { 62791, 62801, 62819, 62827, 62851, 62861, 62869, 62873, 62897, 62903,
            62921, 62927, 62929, 62939, 62969, 62971, 62981, 62983, 62987, 62989, 63029, 63031, 63059, 63067, 63073,
            63079, 63097, 63103, 63113, 63127, 63131, 63149, 63179, 63197, 63199, 63211, 63241, 63247, 63277, 63281,
            63299, 63311, 63313, 63317, 63331, 63337, 63347, 63353, 63361, 63367, 63377, 63389, 63391, 63397, 63409,
            63419, 63421, 63439, 63443, 63463, 63467, 63473, 63487, 63493, 63499, 63521, 63527, 63533, 63541, 63559,
            63577, 63587, 63589, 63599, 63601, 63607, 63611, 63617, 63629, 63647, 63649, 63659, 63667, 63671, 63689,
            63691, 63697, 63703, 63709, 63719, 63727, 63737, 63743, 63761, 63773, 63781, 63793, 63799, 63803, 63809,
            63823, 63839, 63841, 63853, 63857, 63863, 63901, 63907, 63913, 63929, 63949, 63977, 63997, 64007, 64013,
            64019, 64033, 64037, 64063, 64067, 64081, 64091, 64109, 64123, 64151, 64153, 64157, 64171, 64187, 64189,
            64217, 64223, 64231, 64237, 64271, 64279, 64283, 64301, 64303, 64319, 64327, 64333, 64373, 64381, 64399,
            64403, 64433, 64439, 64451, 64453, 64483, 64489, 64499, 64513, 64553, 64567, 64577, 64579, 64591, 64601,
            64609, 64613, 64621, 64627, 64633, 64661, 64663, 64667, 64679, 64693, 64709, 64717, 64747, 64763, 64781,
            64783, 64793, 64811, 64817, 64849, 64853, 64871, 64877, 64879, 64891, 64901, 64919, 64921, 64927, 64937,
            64951, 64969, 64997, 65003, 65011, 65027, 65029, 65033, 65053, 65063, 65071, 65089, 65099, 65101, 65111,
            65119, 65123, 65129, 65141, 65147, 65167, 65171, 65173, 65179, 65183, 65203, 65213, 65239, 65257, 65267,
            65269, 65287, 65293, 65309, 65323, 65327, 65353, 65357, 65371, 65381, 65393, 65407, 65413, 65419, 65423,
            65437, 65447, 65449, 65479, 65497, 65519, 65521, 65537, 65539, 65543, 65551, 65557, 65563, 65579, 65581,
            65587, 65599, 65609, 65617, 65629, 65633, 65647, 65651, 65657, 65677, 65687, 65699, 65701, 65707, 65713,
            65717, 65719, 65729, 65731, 65761, 65777, 65789, 65809, 65827, 65831, 65837, 65839, 65843, 65851, 65867,
            65881, 65899, 65921, 65927, 65929, 65951, 65957, 65963, 65981, 65983, 65993, 66029, 66037, 66041, 66047,
            66067, 66071, 66083, 66089, 66103, 66107, 66109, 66137, 66161, 66169, 66173, 66179, 66191, 66221, 66239,
            66271, 66293, 66301, 66337, 66343, 66347, 66359, 66361, 66373, 66377, 66383, 66403, 66413, 66431, 66449,
            66457, 66463, 66467, 66491, 66499, 66509, 66523, 66529, 66533, 66541, 66553, 66569, 66571, 66587, 66593,
            66601, 66617, 66629, 66643, 66653, 66683, 66697, 66701, 66713, 66721 };
    int ordinal;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KeyID")
    @JsonIgnore
    public long getId() {
        return this.id;
    }

    public void setId(long val) {
        this.id = val;
    }

    @Basic
    @Column(name = "priv_key")
    @JsonIgnore
    public byte[] getPrivKey() {
        return this.privKey;
    }

    public void setPrivKey(byte[] val) {
        this.privKey = val;
    }

    @Basic
    @Column(name = "pub_key")
    @JsonIgnore
    public byte[] getPubKey() {
        return this.pubKey;
    }

    public void setPubKey(byte[] val) {
        this.pubKey = val;
    }

    @Basic
    @Column(name = "modulus")
    @JsonIgnore
    public byte[] getModulus() {
        return this.modulus;
    }

    public void setModulus(byte[] val) {
        this.modulus = val;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    @Column(name = "generation_timestamp")
    @JsonIgnore
    public Calendar getGenerationTimestamp() {
        return generationTimestamp;
    }
    public void setGenerationTimestamp(Calendar val) {
        generationTimestamp = val;
    }

    @Transient
    public String getPublicKeyHex() {
        return "0x00" + new BigInteger(pubKey).toString(16);
    }

    @Transient
    public String getModulusHex() {
        return "0x00" + new BigInteger(modulus).toString(16);
    }

    @Transient
    public static RSAKeyData generateKey() {
        BigInteger p = BigInteger.probablePrime(256, random);
        BigInteger q = BigInteger.probablePrime(256, random);
        BigInteger modulus = p.multiply(q);
        BigInteger publicKey = new BigInteger("65537", 10);
        int ctr = 0;
        BigInteger totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        while (totient.mod(publicKey).equals(BigInteger.ZERO)) {
            if (ctr >= primes.length)
                return generateKey();
            publicKey = new BigInteger(Integer.toString(primes[ctr++]));
        }
        BigInteger privateKey = publicKey.modInverse(totient);
        RSAKeyData key = new RSAKeyData();
        key.setPrivKey(privateKey.toByteArray());
        key.setPubKey(publicKey.toByteArray());
        key.setModulus(modulus.toByteArray());
        return key;
    }

    @Transient
    public byte[] decrypt(BigInteger encData)  {
        BigInteger biModulus = new BigInteger(modulus);
        BigInteger biPrivExp = new BigInteger(privKey);
        return encData.modPow(biPrivExp, biModulus).toByteArray();
    }
}
