/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.forms;

/**
 *
 * @author Michael Janda
 */
public class PurchaseForm {
    private int numAdministrations, numIATs, diskSpace;
    private String productKey;
    
    public int getNumAdministrations() {
        return numAdministrations;
    }
    public void setNumAdministrations(int val) {
        this.numAdministrations = val;
    }
    
    public int getNumIATs() {
        return numIATs;
    }
    public void setNumIATs(int val) {
        this.numIATs = val;
    }
    
    public int getDiskSpace() {
        return this.diskSpace;
    }
    public void setDiskSpace(int val) {
        this.diskSpace = val;
    }
    
    public String getProductKey() {
        return this.productKey;
    }
    public void setProductKey(String val) {
        this.productKey  = val;
    }
}
