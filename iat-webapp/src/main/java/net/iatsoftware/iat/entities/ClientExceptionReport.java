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


import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="client_exception")
public class ClientExceptionReport implements java.io.Serializable {
    private static final long serialVersionUID = 1;
    private long id;
    private Client client = null;
    private User user = null;
    private String exceptionXml = "";
    private Calendar timestamp = Calendar.getInstance();
    
    public ClientExceptionReport() {}
    
    public ClientExceptionReport(Client c, User u, String exceptionXml) {
        this.client = c;
        this.user = u;
        this.exceptionXml = exceptionXml;
    }
    
    @Id
    @Column(name="ExceptionID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public long getId() {
        return this.id;
    }
    public void setId(Long val) {
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
    @JoinColumn(name="UserID", referencedColumnName="UserID")
    public User getUser() {
        return this.user;
    }
    public void setUser(User val) {
        this.user = val;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="exception_timestamp")
    public Calendar getExceptionTimestamp() {
        return this.timestamp;
    }
    public void setExceptionTimestamp(Calendar val) {
        this.timestamp = val;
    }
    
    @Lob
    @Column(name="exception_xml")
    public String getExceptionXml() {
        return this.exceptionXml;
    }
    public void setExceptionXml(String val) {
        this.exceptionXml = val;
    }
}

