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

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PurchaseOrder")
@XmlAccessorType(XmlAccessType.NONE)
public class PurchaseOrder extends net.iatsoftware.iat.generated.PurchaseOrderPojo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    public PurchaseOrder(){}
}
