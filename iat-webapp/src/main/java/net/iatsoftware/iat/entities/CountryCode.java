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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="country_codes")
public class CountryCode implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String code, country;
    
    public CountryCode(){}
    
    public CountryCode(String code, String country) {
        this.code = code;
        this.country = country;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="CodeID")
    public long getId() {
        return this.id;
    }
    public void setId(long val) {
        this.id = val;
    }
    
    @Basic
    @Column(name="code")
    public String getCode() {
        return this.code;
    }
    public void setCode(String val) {
        this.code = val;
    }
    
    @Basic
    @Column(name="country")
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String val) {
        this.country = val;
    }
}
