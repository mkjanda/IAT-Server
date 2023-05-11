/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.entities;

/**
 *
 * @author Michael Janda
 */

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="purchases", indexes={@Index(name="purchases_client_ndx", columnList="ClientID")})
public class Purchase implements java.io.Serializable {
    private long id;
    private Date purchaseTime = new Date();
    private String cardholderFName, cardholderLName, creditCard, endingCardDigits;
    private int numAdministrations, administrationsPrice, numTests, testsPrice, diskSpace, diskSpacePrice, total, cardExpMonth, cardExpYear;
    private Client client;
    
    public Purchase(){}
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="PurchaseID")
    public long getId() {
        return this.id;
    }
    public void setId(long val) {
        this.id = val;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="purchase_time")
    public Date getPurchaseTime() {
        return this.purchaseTime;
    }
    public void setPurchaseTime(Date val) {
        this.purchaseTime = val;
    }
    
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="ClientID", referencedColumnName="ClientID")
    public Client getClient() {
        return this.client;
    }
    public void setClient(Client val) {
        this.client = val;
    }
    
    @Basic
    @Column(name="num_administrations")
    public int getNumAdministrations() {
        return this.numAdministrations;
    }
    public void setNumAdministrations(int val) {
        this.numAdministrations = val;
    }
    
    @Basic
    @Column(name="administrations_price")
    public int getAdministrationsPrice() {
        return this.administrationsPrice;
    }
    public void setAdministrationsPrice(int val) {
        this.administrationsPrice = val;
    }
    
    @Basic
    @Column(name="num_tests")
    public int getNumTests() {
        return this.numTests;
    }
    public void setNumTests(int val) {
        this.numTests = val;
    }
    
    @Basic
    @Column(name="tests_price")
    public int getTestsPrice() {
        return this.testsPrice;
    }
    public void setTestsPrice(int val) {
        this.testsPrice = val;
    }
    
    @Basic
    @Column(name="disk_space")
    public int getDiskSpace() {
        return this.diskSpace;
    }
    public void setDiskSpace(int val) {
        this.diskSpace = val;
    }
    
    @Basic
    @Column(name="disk_space_price")
    public int getDiskSpacePrice() {
        return this.diskSpacePrice;
    }
    public void setDiskSpacePrice(int val) {
        this.diskSpacePrice = val;
    }
    
    @Basic
    @Column(name="total")
    public int getTotal() {
        return this.total;
    }
    public void setTotal(int val) {
        this.total = val;
    }
    
    @Basic
    @Column(name="cardholder_fname")
    public String getCardholderFName() {
        return this.cardholderFName;
    }
    public void setCardholderFName(String val) {
        this.cardholderFName = val;
    }
    
    @Basic
    @Column(name="cardholder_lname")
    public String getCardholderLName() {
        return this.cardholderLName;
    }
    public void setCardholderLName(String val) {
        this.cardholderLName = val;
    }
    
    @Basic
    @Column(name="credit_card")
    public String getCreditCard() {
        return this.creditCard;
    }
    public void setCreditCard(String val) {
        this.creditCard = val;
    }
    
    @Basic
    @Column(name="ending_card_digits")
    public String getEndingCardDigits() {
        return this.endingCardDigits;
    }
    public void setEndingCardDigits(String val) {
        this.endingCardDigits = val;
    }
    
    @Basic
    @Column(name="card_exp_month")
    public int getCardExpMonth() {
        return this.cardExpMonth;
    }
    public void setCardExpMonth(int val) {
        this.cardExpMonth = val;
    }
    
    @Basic
    @Column(name="card_exp_year")
    public int getCardExpYear() {
        return this.cardExpYear;
    }
    public void setCardExpYear(int val) {
        this.cardExpYear = val;
    }
}