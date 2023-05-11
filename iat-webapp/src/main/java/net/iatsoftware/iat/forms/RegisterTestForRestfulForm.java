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

import net.iatsoftware.iat.validation.ProductKeyValid;
import net.iatsoftware.iat.validation.OAuthRequestTestNameValid;
import net.iatsoftware.iat.validation.OAuthRequestTestPasswordValid;
import net.iatsoftware.iat.validation.URL;

import javax.validation.constraints.NotBlank;

@OAuthRequestTestNameValid(message="{validate.requestOAuthRegistration.invalidTestName}")
@OAuthRequestTestPasswordValid(message="{validate.requestOAuthRegistration.invalidTestPassword}")
public class RegisterTestForRestfulForm {
    private String productKey = null, testName = null, testPassword = null, redirectUrl = null;
    private boolean subpathRedirectsAllowed = false;
    
    public RegisterTestForRestfulForm() {}
    
    @NotBlank(message="{validate.requestOAuthRegistration.noProductKey}")
    @ProductKeyValid(message="{validate.requestOAuthRegistration.invalidProductKey}")
    public String getProductKey() {
        return this.productKey;
    }
    public void setProductKey(String val) {
        this.productKey = val;
    }
    
    @NotBlank(message="{validate.requestOAuthRegistration.noTestName}")
    public String getTestName() {
        return this.testName;
    }
    public void setTestName(String val) {
        this.testName = val;
    }
    
    @NotBlank(message="{validate.requestOAuthRegistration.noTestPassword}")
    public String getTestPassword() {
        return this.testPassword;
    }
    public void setTestPassword(String val) {
        this.testPassword = val;
    }
    
    @NotBlank(message="{validate.requestOAuthRegistration.noRedirectURL}")
    @URL(message="{validate.requestOAuthRegistration.invalidRedirectURL}")
    public String getRedirectUrl() {
        return this.redirectUrl;
    }
    public void setRedirectUrl(String val) {
        this.redirectUrl = val;
    }
    
    public boolean isSubpathRedirectsAllowed() {
        return this.subpathRedirectsAllowed;
    }
    public void setSubpathRedirectsAllowed(boolean val) {
        this.subpathRedirectsAllowed = val;
    }
}
