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

import net.iatsoftware.iat.generated.ResourceType;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;


@Entity
@Table(name="resource_prices")
@XmlType(name="ResourcePrice")
@XmlAccessorType(XmlAccessType.NONE)
public class ResourcePrice extends net.iatsoftware.iat.generated.GResourcePrice implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    
    public ResourcePrice() {}
    
    @Id
    @Column(name="ResourcePriceID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public long getId() {
        return this.id;
    }
    public void setId(long val) {
        this.id = val;
    }
            
    @Enumerated(EnumType.STRING)
    @Column(name="resource_type")
    @Override
    public ResourceType getResource() {
        return super.getResource();
    }
    @Override
    public void setResource(ResourceType val) {
        super.setResource(val);
    }
    
    @Basic
    @Column(name="quantity")
    @Override
    public int getQuantity() {
        return super.getQuantity();
    }
    @Override
    public void setQuantity(int val) {
        super.setQuantity(val);
    }
            
    @Basic
    @Column(name="price")
    @Override
    public int getPrice() {
        return super.getPrice();
    }
    @Override
    public void setPrice(int val) {
        super.setPrice(val);
    }
}
