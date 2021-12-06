/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.forms;

/**
 *
 * @author michael
 */

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateOAuthRegistration {
    private String redirectUrl = null;
    private boolean allowExplicitRedirects = false;
    
    public UpdateOAuthRegistration() {}
    
    @JsonProperty("redirectUrl")
    public String getRedirectUrl() {
        return this.redirectUrl;
    }
    public void setRedirectUrl(String val) {
        this.redirectUrl = val;
    }
    
    @JsonProperty("allowExplicitRedirects")
    public boolean isAllowExplicitRedirects() {
        return this.allowExplicitRedirects;
    }
    public void setAllowExplicitRedirects(boolean val) {
        this.allowExplicitRedirects = val;
    }
}
