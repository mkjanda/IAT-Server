/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.entities;

/**
 *
 * @author michael
 */


import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="cors_origins")
public class CorsOrigin implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private Client client;
    private String origin;
    private boolean http = false;
    public CorsOrigin(){}
    
    public CorsOrigin(Client client, String origin) {
        this.client = client;
        this.origin = origin;
    }
    
    @Id
    @Column(name="OriginID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public long getOriginId() {
        return this.id;
    }
    public void setOriginId(long val) {
        this.id = val;
    }
    
    @Basic
    @Column(name="origin")
    public String getOrigin() {
        return this.origin;
    }
    public void setOrigin(String val) {
        this.origin = val;
    }
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ClientID", referencedColumnName="ClientID")
    public Client getClient() {
        return this.client;
    }
    public void setClient(Client val) {
        this.client = val;
    }
    
    @Basic
    @Column(name="http")
    public boolean isHttp() {
        return this.http;
    }
    public void setHttp(boolean val) {
        this.http = val;
    }
}
