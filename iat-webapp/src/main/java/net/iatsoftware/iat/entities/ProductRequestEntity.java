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

import java.util.Calendar;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
        
@Entity
@Table(name="requests")
public class ProductRequestEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1;
    private long id;
    private String fName, lName, organization, email, address1, address2, city, province, postalCode, country, productUse, phone;
    private Calendar registrationTimestamp;
    private boolean deleted;
    
    public ProductRequestEntity() {
        registrationTimestamp = Calendar.getInstance();
    }
    
    public ProductRequestEntity(String fName, String lName, String organization, String email, String phone, String address1, String address2, String city,
            String province, String country, String postalCode, String productUse) {
        this.fName = fName;
        this.lName = lName;
        this.organization = organization;
        this.email = email;
        this.phone = phone;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
        this.productUse = productUse;
        this.registrationTimestamp = Calendar.getInstance();
        this.deleted = false;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="request_id")
    public long getId() {
        return this.id;
    }
    public void setId(Long val) {
        this.id = val;
    }
    
    @Basic
    @Column(name="fname")
    public String getFName() {
        return this.fName;
    }
    public void setFName(String val) {
        this.fName = val;
    }
    
    @Basic
    @Column(name="lname")
    public String getLName() {
        return this.lName;
    }
    public void setLName(String val) {
        this.lName = val;
    }
    
    @Basic
    @Column(name="organization")
    public String getOrganization() {
        return this.organization;
    }
    public void setOrganization(String val) {
        this.organization = val;
    }
    
    @Basic
    @Column(name="email")
    public String getEMail() {
        return this.email;
    }
    public void setEMail(String val) {
        this.email = val;
    }
    
    @Basic
    @Column(name="phone")
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String val) {
        this.phone = val;
    }
    
    @Basic
    @Column(name="address1")
    public String getAddress1() {
        return this.address1;
    }
    public void setAddress1(String val) {
        this.address1 = val;
    }
    
    @Basic
    @Column(name="address2")
    public String getAddress2() {
        return this.address2;
    }
    public void setAddress2(String val) {
        this.address2 = val;
    }
    
    @Basic
    @Column(name="city")
    public String getCity() {
        return this.city;
    }
    public void setCity(String val) {
        this.city = val;
    }
    
    @Basic
    @Column(name="province")
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String val) {
        this.province = val;
    }
    
    @Basic
    @Column(name="postal_code")
    public String getPostalCode() {
        return this.postalCode;
    }
    public void setPostalCode(String val) {
        this.postalCode = val;
    }
    
    @Basic
    @Column(name="country")
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String val) {
        this.country = val;
    }
    
    @Lob
    @Column(name="product_use")
    public String getProductUse() {
        return this.productUse;
    }
    public void setProductUse(String val) {
        this.productUse = val;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="time_stamp")
    public Calendar getRegistrationTimestamp() {
        return this.registrationTimestamp;
    }
    public void setRegistrationTimestamp(Calendar val) {
        this.registrationTimestamp = val;
    }
    
    @Basic
    @Column(name="delete_flag")
    public boolean isDeleted() {
        return this.deleted;
    }
    public void setDeleted(boolean val) {
        this.deleted = val;
    }
}
