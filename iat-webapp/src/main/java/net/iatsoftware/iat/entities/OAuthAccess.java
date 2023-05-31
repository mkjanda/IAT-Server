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

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Calendar;

@Entity
@Table(name="oauth_access", indexes = @Index(name="oauth_access_ndx", columnList="access_token"))
public class OAuthAccess implements java.io.Serializable {
    private static final long serialVersionUID = -1;
    private long id;
    private Client client = null;
    private IAT iat = null;
    private String accessToken, refreshToken, authToken;
    private Calendar accessExpires, refreshExpires;
    private boolean bAuthTokenConsumed = false;
    
    public static final int AUTH_TOKEN_NOT_FOUND = -1;
    public static final int INVALID_CLIENT_ID = -2;
    public static final int MISMATCHED_CLIENT_ID = -3;
    public static final int AUTH_TOKEN_CONSUMED = -4;
    public static final int INVALID_CLIENT_SECRET = -5;
    public static final int AUTH_TOKEN_VALID = -6;
    public static final int ACCESS_TOKEN_VALID = -7;
    public static final int ACCESS_TOKEN_EXPIRED = -8;
    public static final int ACCESS_TOKEN_MISMATCH = -9;
    public static final int ACCESS_TOKEN_NOT_FOUND = -10;
    public static final int NO_SUCH_REFRESH_TOKEN = -11;
    public static final int REFRESH_TOKEN_VALID = -12;
    
    public static final int accessExpiration = 1200;
    public static final int refreshExpiration = 86400;
    
    
    public OAuthAccess() {
        accessExpires = Calendar.getInstance();
        refreshExpires = Calendar.getInstance();
        accessToken = refreshToken = authToken = "";
    }
    
    public OAuthAccess(Client c, IAT test, String authToken, String accessToken, String refreshToken) {
        this.client = c;
        this.iat = test;
        this.authToken = authToken;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessExpires = Calendar.getInstance();
        this.refreshExpires = Calendar.getInstance();
        this.refreshExpires.add(Calendar.DAY_OF_YEAR, 14);
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="AccessID")
    public long getAccessId() {
        return this.id;
    }
    public void setAccessId(long val) {
        this.id = val;
    }
    
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="ClientID", referencedColumnName="ClientID")
    public Client getClient() {
        return this.client;
    }
    public void setClient(Client val) {
        this.client = val;
    }
    
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="TestID", referencedColumnName="TestID")
    public IAT getTest() {
        return this.iat;
    }
    public void setTest(IAT val) {
        this.iat = val;
    }
    
    @Basic
    @Column(name="access_token")
    public String getAccessToken() {
        return this.accessToken;
    }
    public void setAccessToken(String val) {
        this.accessToken = val;
    }
    
    @Basic
    @Column(name="refresh_token")
    public String getRefreshToken() {
        return this.refreshToken;
    }
    public void setRefreshToken(String val) {
        this.refreshToken = val;
    }
    
    @Basic
    @Column(name="auth_token")
    public String getAuthToken() {
        return this.authToken;
    }
    public void setAuthToken(String val) {
        this.authToken = val;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="access_expires")
    public Calendar getAccessExpires() {
        return this.accessExpires;
    }
    public void setAccessExpires(Calendar val) {
        this.accessExpires = val;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="refresh_expires")
    public Calendar getRefreshExpires() {
        return this.refreshExpires;
    }
    public void setRefreshExpires(Calendar val) {
        this.refreshExpires = val;
    }
    
    @Basic
    @Column(name="auth_token_consumed")
    public boolean isAuthTokenConsumed() {
        return this.bAuthTokenConsumed;
    }
    public void setAuthTokenConsumed(boolean val) {
        this.bAuthTokenConsumed = val;
    }
}
