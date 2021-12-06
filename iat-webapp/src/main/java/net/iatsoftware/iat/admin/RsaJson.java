/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.admin;

/**
 *
 * @author michael
 */

public class RsaJson {
    private long publicKey, modulus;
    
    public RsaJson(long pubKey, long mod) {
        publicKey = pubKey;
        modulus = mod;
    }
    
    public long getPubicKey() {
        return this.publicKey;
    }
    public void setPublicKey(long val) {
        this.publicKey = val;
    }
    public long getModulus() {
        return this.modulus;
    }
    public void setModulus(long val) {
        this.modulus = val;
    }
    
}
