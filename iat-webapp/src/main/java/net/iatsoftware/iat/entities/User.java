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

import net.iatsoftware.iat.messaging.ActivationRequest;

import java.util.Calendar;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "users", indexes
        = @Index(name = "client_user", columnList = "ClientID, user_num", unique = true))
@XmlRootElement(name = "UserInfo")
@XmlAccessorType(XmlAccessType.NONE)
public class User extends net.iatsoftware.iat.generated.UserInfoPojo implements java.io.Serializable {
    private static final Random random = new Random();
    private static final long serialVersionUID = 1L;
    private static final int[] productKeyModuli = new int[]{22, 6, 17, 30, 5, 15, 24, 2, 19, 35, 27, 2, 19, 23, 25, 20, 12, 4, 19, 31};
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private long id;
    private Calendar activationDate;
    private String activationKey, verificationKey;
    private Client client;
    private int userNum;
    private boolean emailVerified = false;
    private static final Random rand = new Random();

    public User() {
    }

    public User(ActivationRequest req, int userNum, Client c) {
        super.setTitle(req.getTitle());
        super.setFName(req.getFName());
        super.setLName(req.getLName());
        super.setEMail(req.getEMail().toLowerCase());
        this.activationKey = generateActivationKey(c.getProductKey());
        byte[] bytes = new byte[24];
        rand.nextBytes(bytes);
        this.verificationKey = Base64.getEncoder().encodeToString(bytes) + "." + Long.toString(c.getClientId());
        List<User> users = c.getUsers();
        if (users != null) {
            while (users.stream().map(u -> u.getActivationKey()).collect(Collectors.toList()).contains(this.activationKey)) {
                this.activationKey = generateActivationKey(c.getProductKey());
            }
            while (users.stream().map(u -> u.getVerificationKey()).collect(Collectors.toList()).contains(this.verificationKey)) {
                rand.nextBytes(bytes);
                this.verificationKey = Base64.getEncoder().encodeToString(bytes) + "." + Long.toString(c.getClientId());
            }
        }
        this.activationKey = generateActivationKey(c.getProductKey());
        this.userNum = userNum;
        this.client = c;
    }

    public static String encodeKey(String key) {
        String str = encoder.encodeToString(key.getBytes(StandardCharsets.UTF_8));
        return str.replace("=", "_1").replace("+", "_2").replace("/", "_3");
    }

    public static String decodeKey(String key) {
        String str = key.replace("_1", "=").replace("_2", "+").replace("_3", "/");
        return new String(decoder.decode(str), StandardCharsets.UTF_8);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getUserId() {
        return this.id;
    }

    public void setUserId(Long val) {
        this.id = val;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ClientID")
    public Client getClient() {
        return this.client;
    }

    public void setClient(Client val) {
        this.client = val;
    }

    @Basic
    @Column(name = "user_num")
    public int getUserNum() {
        return this.userNum;
    }

    public void setUserNum(int val) {
        this.userNum = val;
    }

    @Basic
    @Column(name = "title")
    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Basic
    @Column(name = "fname")
    @Override
    public String getFName() {
        return super.getFName();
    }

    @Basic
    @Column(name = "lname")
    @Override
    public String getLName() {
        return super.getLName();
    }

    @Transient
    public String getFullName() {
        return this.title + " " + this.fName + " " + this.lName;
    }

    @Basic
    @Column(name = "email")
    @Override
    public String getEMail() {
        return super.getEMail().toLowerCase();
    }

    @Override
    public void setEMail(String val) {
        super.setEMail(val.toLowerCase());
    }

    @Basic
    @Column(name = "email_verified")
    public boolean isEMailVerified() {
        return this.emailVerified;
    }

    public void setEMailVerified(boolean val) {
        this.emailVerified = val;
    }

    @Basic
    @Column(name = "verification_code")
    public String getVerificationKey() {
        return this.verificationKey;
    }

    public void setVerificationKey(String val) {
        this.verificationKey = val;
    }

    @Basic
    @Column(name = "activation_key", nullable = true)
    public String getActivationKey() {
        return this.activationKey;
    }
    public void setActivationKey(String val) {
        this.activationKey = val;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="activation_date")
    public Calendar getActivationDate() {
        return this.activationDate;
    }
    public void setActivationDate(Calendar val) {
        this.activationDate = val;
    }

    private String generateActivationKey(String productKey) {
        StringBuilder key = new StringBuilder();
        for (int ctr = 0; ctr < productKey.length(); ctr += 2) {
            int pVal = Integer.parseInt(productKey.substring(ctr, ctr + 2), 36);
            int aVal = pVal + productKeyModuli[ctr >> 1] + random.nextInt(36 * 35) * pVal;
            StringBuilder nextPart = new StringBuilder(Integer.toString(aVal, 36).toUpperCase());
            while (nextPart.length() < 4) {
                nextPart.append("0" + nextPart);
            }
            key.append(nextPart);
        }
        return key.toString();
    }

    public void generateActivationKey() {
        activationKey = generateActivationKey(client.getProductKey());
    }
}
