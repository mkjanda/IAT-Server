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

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.generated.ActivationResult;

import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="ActivationResponse")
@XmlAccessorType(XmlAccessType.NONE)
public class ActivationResponse extends net.iatsoftware.iat.generated.ActivationResponsePojo {
    public ActivationResponse()
    {
        verificationCode = productKey = clientName = clientEMail = phone = address1 = address2 = province = city = postalCode = country = "";
    }
    
    public ActivationResponse(String name, String email, Client c, ActivationResult result) {
        this.clientName = name;
        this.clientEMail = email;
        this.activationResult = result;
        this.phone = c.getPhone();
        if (this.phone == null)
            this.phone = "NONE";
        this.address1 = c.getAddress1();
        if (this.address1 == null)
            this.address1 = "NONE";
        this.address2 = c.getAddress2();
        if (address2 == null)
            address2 = "NONE";
        this.province = c.getProvince();
        if (this.province == null)
            this.province = "NONE";
        this.city = c.getCity();
        if (this.city == null)
            this.city = "NONE";
        this.productKey = c.getProductKey();
        if (this.productKey == null)
            this.productKey = "NONE";
        this.postalCode = c.getPostalCode();
        if (this.postalCode == null)
            this.postalCode = "NONE";
        this.country = c.getCountry();
        if (this.country == null)
            this.country = "NONE";
    }
    

    public static ActivationResponse getFailedActivationConfirmation(ActivationResult result) {
        ActivationResponse resp = new ActivationResponse();
        resp.activationResult = result;
        return resp;
    }

    @Override
    public boolean doBeforeMarshal(Marshaller m)
    {
        if (activationResult != ActivationResult.SUCCESS) {
            this.verificationCode = "FAILED";
        }
        return true;
    }
}
