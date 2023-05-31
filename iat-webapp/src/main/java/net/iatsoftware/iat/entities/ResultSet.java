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
import java.util.Calendar;

@Entity
@Table(name="results", indexes={
    @Index(name="test_id", columnList="TestID")
})
public class ResultSet implements java.io.Serializable {
    private long id;
    private IAT test = null;
    private byte[] results, testeeToken;
    private String toc;
    private Calendar adminTime;
    
    public ResultSet() { }
    
    public ResultSet(IAT test, String toc, byte[] results) {
        this.adminTime = Calendar.getInstance();
        this.toc = toc;
        this.results = results;
        this.test = test;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ResultID")
    public long getId()
    {
        return this.id;
    }
    public void setId(long val)
    {
        this.id = val;
    }
    
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="TestID")
    public IAT getTest()
    {
        return this.test;
    }
    public void setTest(IAT val)
    {
        this.test = val;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="admin_time")
    public Calendar getAdminTime()
    {
        return this.adminTime;
    }
    public void setAdminTime(Calendar val)
    {
        this.adminTime = val;
    }
    
    @Lob
    @Column(name="results")
    public byte[] getResults()
    {
        return this.results;
    }
    public void setResults(byte[] val)
    {
        this.results = val;
    }
    
    @Lob
    @Column(name="toc")
    public String getToc()
    {
        return this.toc;
    }
    public void setToc(String val) {
        this.toc = val;
    }
    
    @Basic
    @Column(name="testee_token")
    public byte[] getTesteeToken() {
        return this.testeeToken;
    }
    public void setTesteeToken(byte[] val) {
        this.testeeToken = val;
    }
}
