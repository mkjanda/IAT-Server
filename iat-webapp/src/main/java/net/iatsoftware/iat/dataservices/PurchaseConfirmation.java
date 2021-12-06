/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.dataservices;

/**
 *
 * @author Michael Janda too
 */

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.Purchase;
import net.iatsoftware.iat.generated.ResourceType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PurchaseConfirmation")
@XmlAccessorType(XmlAccessType.NONE)
public class PurchaseConfirmation extends net.iatsoftware.iat.generated.PurchaseConfirmationPojo {
    public PurchaseConfirmation(){}
    
    public PurchaseConfirmation(Purchase p, Client c) {
        this.card = new Card();
        this.card.setCardExpMonth(p.getCardExpMonth());
        this.card.setCardExpYear(p.getCardExpYear());
        this.card.setCreditCard(p.getCreditCard());
        this.card.setEndingCardDigits(p.getEndingCardDigits());
        if (p.getDiskSpace() > 0) {
            Resource r = new Resource();
            r.setQuantity(p.getDiskSpace());
            r.setPrice(Integer.toString(p.getDiskSpacePrice()));
            r.setType(ResourceType.DISK_SPACE);
            this.getResource().add(r);
        }
        if (p.getNumTests() > 0) {
            Resource r = new Resource();
            r.setType(ResourceType.IAT_WITH_10_MB);
            r.setPrice(Integer.toString(p.getTestsPrice()));
            r.setQuantity(p.getNumTests());
            this.getResource().add(r);
        }
        if (p.getNumAdministrations() > 0) {
            Resource r = new Resource();
            r.setType(ResourceType.ADMINISTRATION);
            r.setQuantity(p.getNumAdministrations());
            r.setPrice(Integer.toString(p.getAdministrationsPrice()));
            this.getResource().add(r);
        }
        this.setFirstName(c.getContactFName());
        this.setLastName(c.getContactLName());
        this.setCardholderFirstName(p.getCardholderFName());
        this.setCardholderLastName(p.getCardholderLName());
        this.setDownloadPassword(c.getDownloadPassword());
        this.setProductKey(c.getProductKey());
        this.setTotal(Integer.toString(p.getTotal()));
    }
}
