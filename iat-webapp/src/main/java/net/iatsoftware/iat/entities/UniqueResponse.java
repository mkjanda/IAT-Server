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

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Basic;
import jakarta.persistence.Id;

@Entity
@Table(name="unique_responses", indexes={@Index(name="items_id_index", columnList="ItemID")})
public class UniqueResponse implements java.io.Serializable {
    private long id;
    private UniqueResponseItem item;
    private boolean taken, consumed;
    private String value;
    private AdminTimer timer;
    
    public UniqueResponse() {}
    
    public UniqueResponse(UniqueResponseItem uri, String value) {
        this.item = uri;
        this.value = value;
        this.taken = false;
        this.consumed = false;
        this.timer = null;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="UniqueResponseID")
    public long getId() {
        return this.id;
    }
    public void setId(long val) {
        this.id = val;
    }
    
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="ItemID", referencedColumnName="UniqueResponseItemID")
    public UniqueResponseItem getItem() {
        return this.item;
    }
    public void setItem(UniqueResponseItem val) {
        this.item = val;
    }
    
    @Basic
    @Column(name="val")
    public String getValue() {
        return this.value;
    }
    public void setValue(String val) {
        this.value = val;
    }
    
    @Basic
    @Column(name="taken")
    public boolean getTaken() {
        return this.taken;
    }
    public void setTaken(boolean val) {
        this.taken = val;
    }
    
    @Basic
    @Column(name="consumed")
    public boolean getConsumed() {
        return this.consumed;
    }
    public void setConsumed(boolean val) {
        this.consumed = val;
    }
    
    @OneToOne(optional=true, fetch=FetchType.EAGER)
    @JoinColumn(name="AdminID", referencedColumnName="timer_id")
    public AdminTimer getAdminTimer() {
        return this.timer;
    }
    public void setAdminTimer(AdminTimer val) {
        this.timer = val;
    }
    
    public void free() {
        this.consumed = false;
        this.taken = false;
        this.timer = null;
    }
}
