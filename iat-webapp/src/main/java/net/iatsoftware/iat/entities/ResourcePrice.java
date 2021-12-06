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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;


@Entity
@Table(name="resource_prices")
@XmlType(name="ResourcePrice")
@XmlAccessorType(XmlAccessType.NONE)
public class ResourcePrice extends net.iatsoftware.iat.generated.ResourcePricePojo implements java.io.Serializable {
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
