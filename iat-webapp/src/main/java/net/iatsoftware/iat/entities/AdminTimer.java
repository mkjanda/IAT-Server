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

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Calendar;

@Entity
@Table(name="admin_timers")
public class AdminTimer implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private IAT iat;
    private Calendar lastTick;
    private boolean complete;
    private UniqueResponse uniqueResponse = null;
    private byte[] token = null;
    private String sessId = null;
    
    public AdminTimer()
    {}
    
    public AdminTimer(IAT test) {
        this.iat = test;
        this.lastTick = Calendar.getInstance();
        this.complete = false;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="timer_id")
    public long getId() {
        return this.id;
    }
    public void setId(long val) {
        this.id = val;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_timer_refresh")
    public Calendar getLastTick() {
        return this.lastTick;
    }
    public void setLastTick(Calendar val) {
        this.lastTick = val;
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
    @Column(name="complete")
    public boolean getComplete() {
        return this.complete;
    }
    public void setComplete(Boolean val) {
        this.complete = val;
    }
 
    @OneToOne(optional=true, fetch=FetchType.EAGER, mappedBy="adminTimer")
    public UniqueResponse getUniqueResponse() {
        return this.uniqueResponse;
    }
    public void setUniqueResponse(UniqueResponse val) {
        this.uniqueResponse = val;
    }
    
    @Basic
    @Column(name="testee_token")
    public byte[] getTesteeToken() {
        return this.token;
    }
    public void setTesteeToken(byte[] val) {
        this.token = val;
    }

    @Basic
    @Column(name="iatsessionid")
    public String getIATSESSIONID() {
        return sessId;
    }
    public void setIATSESSIONID(String val) {
        sessId = val;
    }
}
