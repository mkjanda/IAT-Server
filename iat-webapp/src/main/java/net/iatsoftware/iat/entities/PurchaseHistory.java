/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.entities;

/**
 *
 * @author Michael Janda too
 */

import java.text.DateFormat;
import java.util.Calendar;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="purchase_history")
public class PurchaseHistory extends net.iatsoftware.iat.generated.PurchaseHistoryEntityPojo implements java.io.Serializable {
    private long id;
    private Client client;
    private Calendar purchaseTimestamp = Calendar.getInstance();
    private final static DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="PurchaseID")
    public Long getId() {
        return this.id;
    }
    public void setId(Long val) {
        this.id = val;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="purchase_time")
    public Calendar getPurchaseTimestamp() {
        return this.purchaseTimestamp;
    }
    public void setPurchaseTimestamp(Calendar val) {
        this.purchaseTimestamp = val;
    }
    
    
    @Override
    public String getPurchaseTime() {
        return df.format(getPurchaseTimestamp());
    }
    
    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="ClientID", referencedColumnName="ClientID")
    public Client getClient() {
        return this.client;
    }
    public void setClient(Client val) {
        this.client = val;
    }
    
    @Basic
    @Column(name="num_administrations")
    @Override
    public int getNumAdministrations() {
        return super.getNumAdministrations();
    }
    @Override
    public void setNumAdministrations(int val) {
        super.setNumAdministrations(val);
    }
    
    @Basic
    @Column(name="administrations_total")
    @Override
    public int getAdministrationsTotal() {
        return super.getAdministrationsTotal();
    }
    @Override
    public void setAdministrationsTotal(int val) {
        super.setAdministrationsTotal(val);
    }
    
    @Basic
    @Column(name="num_tests")
    @Override
    public int getNumTests() {
        return super.getNumTests();
    }
    @Override
    public void setNumTests(int val) {
        super.setNumTests(val);
    }
    
    @Basic
    @Column(name="test_total")
    @Override
    public int getTestsTotal() {
        return super.getTestsTotal();
    }
    @Override
    public void setTestsTotal(int val) {
        super.setTestsTotal(val);
    }
    
    @Basic
    @Column(name="disk_space")
    @Override
    public int getDiskSpace() {
        return super.getDiskSpace();
    }
    @Override
    public void setDiskSpace(int val) {
        super.setDiskSpace(val);
    }
    
    @Basic
    @Column(name="disk_space_total")
    @Override
    public int getDiskSpaceTotal() {
        return super.getDiskSpaceTotal();
    }
    @Override
    public void setDiskSpaceTotal(int val) {
        super.setDiskSpaceTotal(val);
    }
    
    @Basic
    @Column(name="total")
    @Override
    public int getTotal() {
        return super.getTotal();
    }
    @Override
    public void setTotal(int val) {
        super.setTotal(val);
    }
}
