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
public class PaymentConfirmationForm {
    private boolean otherEMail;
    private String email = "", updateEMail = "no";
    
    public PaymentConfirmationForm() {
    }
    
    public String getUpdateEMail() {
        return this.updateEMail;
    }
    public void setUpdateEMail(String val) {
        this.updateEMail = val;
    }

    public boolean isOtherEMail() {
        return this.otherEMail;
    }
    public void setOtherEMail(boolean val) {
        this.otherEMail = val;
    }
    
    
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String val) {
        this.email = val;
    }
    
}
