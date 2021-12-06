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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="specifier_values", indexes = {
    @Index(name="specifier_values_admin_ndx", columnList="AdminID")
})
public class SpecifierValue implements java.io.Serializable {
    private long id;
    private AdminTimer admin;
    private int testSpecifierID;
    private String specifierValue;
    

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="SpecifierValueID")
    public long getId() {
        return this.id;
    }
    public void setId(long val) {
        this.id = val;
    }
    
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="AdminID", referencedColumnName="timer_id")
    public AdminTimer getAdmin() {
        return this.admin;
    }
    public void setAdmin(AdminTimer val) {
        this.admin = val;
    }
    
    @Basic
    @Column(name="specifier_value")
    public String getSpecifierValue() {
        return this.specifierValue;
    }
    public void setSpecifierValue(String val) {
        this.specifierValue = val;
    }
    
    @Basic
    @Column(name="test_specifier_id")
    public int getTestSpecifierId() {
        return this.testSpecifierID;
    }
    public void setTestSpecifierId(int val) {
        this.testSpecifierID = val;
    }
}
