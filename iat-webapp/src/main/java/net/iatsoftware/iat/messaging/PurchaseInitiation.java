/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

/**
 *
 * @author Michael Janda too
 */

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PurchaseInitiation")
@XmlAccessorType(XmlAccessType.NONE)
public class PurchaseInitiation extends net.iatsoftware.iat.generated.PurchaseInitiationPojo {
    public PurchaseInitiation(){}
}
