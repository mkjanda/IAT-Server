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

import java.io.Serializable;


public class SpecifierId implements Serializable {
    private long baseSpecifierID, derivedSpecifierID;
    
    public long getBaseSpecifierID() {
        return this.baseSpecifierID;
    }
    public void setBaseSpecifierID(long val) {
        this.baseSpecifierID = val;
    }
    
    public long getDerivedSpecifierID() {
        return this.derivedSpecifierID;
    }
    public void setDerivedSpecifierID(long val) {
        this.derivedSpecifierID = val;
    }
    
    @Override
    public boolean equals(Object o) {
        SpecifierId spec = (SpecifierId)o;
        return ((this.baseSpecifierID != spec.baseSpecifierID) || (this.derivedSpecifierID != spec.derivedSpecifierID)) ? false : true;
    }
    
    @Override
    public int hashCode() {
        long lHash = this.baseSpecifierID ^ this.derivedSpecifierID;
        return (int)lHash;
    }
}
