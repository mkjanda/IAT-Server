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

public class OAuthAccessForm {
    private String clientId, clientSecret, redirectUrl;
    private boolean subpathRedirectAllowed;
    
    public OAuthAccessForm(){}
    
    public OAuthAccessForm(String clientId, String clientSecret, String redirectUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
    }
    
    public String getClientId() {
        return this.clientId;
    }
    public void setClientId(String val) {
        this.clientId = val;
    }
    
    public String getClientSecret() {
        return this.clientSecret;
    }
    public void setClientSecret(String val) {
        this.clientSecret = val;
    }
    
    public String getRedirectUrl() {
        return this.redirectUrl;
    }
    public void setRedirectUrl(String val) {
        this.redirectUrl = val;
    }
    
    public boolean isSubpathRedirectAllowed() {
        return this.subpathRedirectAllowed;
    }
    public void setSubpathRedirectAllowed(boolean val) {
        this.subpathRedirectAllowed = val;
    }
            
}
