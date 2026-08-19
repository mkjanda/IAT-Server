/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.entities.EncryptedRSAKey;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="RSAKeyPair")
@XmlAccessorType(XmlAccessType.NONE)
public class RSAKeyPair extends net.iatsoftware.iat.generated.GRSAKeyPair {
    public RSAKeyPair(){}
    
    public RSAKeyPair(EncryptedRSAKey dataKey, EncryptedRSAKey adminKey) {
        this.adminKey = adminKey;
        this.dataKey = dataKey;
    }
}
