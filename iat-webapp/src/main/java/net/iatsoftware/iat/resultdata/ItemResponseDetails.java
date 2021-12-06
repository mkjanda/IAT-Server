/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultdata;

/**
 *
 * @author michael
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class ItemResponseDetails {
    
    private final long latency;
    private final int block, item;
    public final boolean error;
    
    public ItemResponseDetails(long latency, int block, int item, boolean error)
    {
        this.latency = latency;
        this.block = block;
        this.item = item;
        this.error = error;
    }
    
    @XmlElement(name="latency")
    public long getLatency() {
        return this.latency;
    }
    
    @XmlElement(name="block") 
    public int getBlock() {
        return this.block;
    }
    
    @XmlElement(name="item")
    public int getItem() {
        return this.item;
    }
    
    @XmlElement(name="error")
    public boolean hasError() {
        return this.error;
    }
}
