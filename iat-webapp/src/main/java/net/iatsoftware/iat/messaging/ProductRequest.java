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

import net.iatsoftware.iat.entities.ProductRequestEntity;

import java.text.DateFormat;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name="ProductRequest")
@XmlAccessorType(XmlAccessType.NONE)
public class ProductRequest extends net.iatsoftware.iat.generated.GProductRequest {
    private static DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
    
    public ProductRequest(ProductRequestEntity pr) {
        this.requestID = pr.getId();
        this.address1 = pr.getAddress1();
        this.address2 = pr.getAddress2();
        this.city = pr.getCity();
        this.country = pr.getCountry();
        this.eMail = pr.getEMail();
        this.fName = pr.getFName();
        this.lName = pr.getLName();
        this.organization = pr.getOrganization();
        this.phone = pr.getPhone();
        this.postalCode = pr.getPostalCode();
        this.province = pr.getProvince();
        this.registrationTimestamp = dateFormat.format(pr.getRegistrationTimestamp().getTime());
    }
}
