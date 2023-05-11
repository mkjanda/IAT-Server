/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.forms;

/**
 *
 * @author Michael Janda
 */

public class PaymentForm {
    private String fName ="", lName ="", address1 ="", address2 ="", city = "", state = "", countryCode = "", zip = "", cvv2 = "", card = "", cardNumber = ""; 
    private int cardExpMonth = 0, cardExpYear = 0;
    
    public String getFirstName() {
        return this.fName;
    }
    public void setFirstName(String val) {
        this.fName = val;
    }
    
    public String getLastName() {
        return this.lName;
    }
    public void setLastName(String val) {
        this.lName = val;
    }
    
    public String getAddress1() {
        return this.address1;
    }
    public void setAddress1(String val) {
        this.address1 = val;
    }
    
    public String getAddress2() {
        return this.address2;
    }
    public void setAddress2(String val) {
        this.address2 = val;
    }
    
    public String getCity() {
        return this.city;
    }
    public void setCity(String val) {
        this.city = val;
    }
    
    public String getState() {
        return this.state;
    }
    public void setState(String val) {
        this.state = val;
    }
    
    public String getCountryCode() {
        return this.countryCode;
    }
    public void setCountryCode(String val) {
        this.countryCode = val;
    }
    
    public String getZip() {
        return this.zip;
    }
    public void setZip(String val) {
        this.zip = val;
    }
    
    public String getCvv2() {
        return this.cvv2;
    }
    public void setCvv2(String val) {
        this.cvv2 = val;
    }
    
    public String getCard() {
        return this.card;
    }
    public void setCard(String val) {
        this.card = val;
    }
    
    public String getCardNumber() {
        return this.cardNumber;
    }
    public void setCardNumber(String val) {
        this.cardNumber = val;
    }
    
    public int getCardExpMonth() {
        return this.cardExpMonth;
    }
    public void setCardExpMonth(int val) {
        this.cardExpMonth = val;
    }
    
    public int getCardExpYear() {
        return this.cardExpYear;
    }
    public void setCardExpYear(int val) {
        this.cardExpYear = val;
    }

}
