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
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "AcceptConfirmation")
public class AcceptConfirmation extends net.iatsoftware.iat.generated.AcceptConfirmationPojo {

    public AcceptConfirmation() {
        this.downloadPassword = "FAILED";
        this.productKey = "FAILED";
    }

    public AcceptConfirmation(String productKey, String downloadPassword) {
        this.productKey = productKey;
        this.downloadPassword = downloadPassword;
    }
}
