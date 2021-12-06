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

import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="RSAKeyPair")
@XmlAccessorType(XmlAccessType.NONE)
public class RSAKeyPair extends net.iatsoftware.iat.generated.RSAKeyPairPojo {
    public RSAKeyPair(){}
    
    public RSAKeyPair(PartiallyEncryptedRSAKey dataKey, PartiallyEncryptedRSAKey adminKey) {
        this.adminKey = adminKey;
        this.dataKey = dataKey;
    }
}
