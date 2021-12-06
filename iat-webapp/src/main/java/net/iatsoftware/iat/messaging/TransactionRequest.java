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

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
       
@XmlRootElement(name="TransactionRequest")
@XmlAccessorType(XmlAccessType.NONE)
public class TransactionRequest extends net.iatsoftware.iat.generated.TransactionRequestPojo {
    public TransactionRequest() {
        transaction = TransactionType.UNSET;
        iatName = "";
        productKey = "";
        clientID = 0;
        lastTransaction = false;
    }

    public TransactionRequest(TransactionType transType) {
        transaction = transType;
        clientID = 0;
        iatName = "";
        productKey = "";
        lastTransaction = false;
    }
    
    public TransactionRequest(TransactionType transType, long clientID) {
        this.transaction = transType;
        this.clientID = clientID;
        iatName = "";
        productKey = "";
        lastTransaction = false;
    }
    
    public void addIntValue(String name, int val) {
        if (this.intValue == null)
            this.intValue = new ArrayList<>();
        if (this.intValue.stream().anyMatch(elem -> elem.getName().equals(name)))
            throw new IllegalArgumentException("Name exists in collection");
        this.intValue.add(new IntElement(name, val));
    }
    
    public int getIntValue(String name) throws java.util.NoSuchElementException {
        if (this.intValue == null)
            throw new java.util.NoSuchElementException();
        return this.intValue.stream().filter(elem -> elem.getName().equals(name)).findFirst().get().getValue();
    }
    
    public void addLongValue(String name, long val) {
        if (this.longValue == null)
            this.longValue = new ArrayList<>();
        if (this.longValue.stream().anyMatch(elem -> elem.getName().equals(name)))
            throw new IllegalArgumentException("Name exists in collection");
        this.longValue.add(new LongElement(name, val));
    }

    public long getLongValue(String name) throws java.util.NoSuchElementException {
        if (this.longValue == null)
            throw new java.util.NoSuchElementException();
        return this.longValue.stream().filter(elem -> elem.getName().equals(name)).findFirst().get().getValue();
    }
    
    public void addStringValue(String name, String val) {
        if (this.stringValue == null)
            this.stringValue = new ArrayList<>();
        if (this.stringValue.stream().anyMatch(elem -> elem.getName().equals(name)))
            throw new IllegalArgumentException("Name exists in collection");
        this.stringValue.add(new StringElement(name, val));
    }

    public String getStringValue(String name) throws java.util.NoSuchElementException {
        if (this.stringValue == null)
            throw new java.util.NoSuchElementException();
        return this.stringValue.stream().filter(elem -> elem.getName().equals(name)).findFirst().get().getValue();
    }
}
