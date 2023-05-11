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

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;


public class Authorization {
    private String scope, accessToken, tokenType, appId, expiresIn;
    
    @JsonGetter("scope")
    public String getScope() {
        return this.scope;
    }
    @JsonSetter("scope")
    public void setScope(String val) {
        this.scope = val;
    }
    
    @JsonGetter("access_token")
    public String getAccessToken() {
        return this.accessToken;
    }
    @JsonSetter("access_token")
    public void setAccessToken(String val) {
        this.accessToken = val;
    }
    
    @JsonGetter("token_type")
    public String getTokenType() {
        return this.tokenType;
    }
    @JsonSetter("token_type")
    public void setTokenType(String val) {
        this.tokenType = val;
    }
    
    @JsonGetter("app_id")
    public String getAppId() {
        return this.appId;
    }
    @JsonSetter("app_id")
    public void setAppId(String val) {
        this.appId = val;
    }
    
    @JsonGetter("expires_in")
    public String getExpiresIn() {
        return this.expiresIn;
    }
    @JsonSetter("expires_in")
    public void setExpiresIn(String val) {
        this.expiresIn = val;
    }
}
