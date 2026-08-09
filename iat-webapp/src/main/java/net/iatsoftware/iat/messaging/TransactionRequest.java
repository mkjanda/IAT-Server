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
import net.iatsoftware.iat.generated.TransactionType;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
       
@XmlRootElement(name="TransactionRequest")
@XmlAccessorType(XmlAccessType.NONE)
public class TransactionRequest extends net.iatsoftware.iat.generated.GTransactionRequest {
    public TransactionRequest() {
        type = TransactionType.UNSET;
        iatName = "";
        productKey = "";
        clientId= 0;
        isLastTransaction = false;
    }

    public TransactionRequest(TransactionType transType) {
        type = transType;
        clientId = 0;
        iatName = "";
        productKey = "";
        isLastTransaction = false;
    }
    
    public TransactionRequest(TransactionType transType, long clientID) {
        this.type = transType;
        this.clientId = clientID;
        iatName = "";
        productKey = "";
        isLastTransaction = false;
    }
}
