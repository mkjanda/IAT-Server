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

import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlType(name="Transaction")
@XmlAccessorType(XmlAccessType.NONE)
public class Transaction extends net.iatsoftware.iat.generated.GTransaction {

    public Transaction()
    {
        this.setTransaction(TransactionType.UNSET);
    }
    
    public Transaction(TransactionType type) {
        this.setTransaction(type);
    }
}
