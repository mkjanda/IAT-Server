/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.admin;

/**
 *
 * @author michael
 */
public class InvalidTokenParams {
    private String tokenName = "", tokenValue = "", tokenTypeStr = "NONE", suppliedTokenName = "", testName = "", errorMessage = "", httpReferer = "";
    private boolean invalidTokenValue = false, invalidTokenName = false, missingToken = false;
    
    public String getTokenName() {
        return tokenName;
    }
    public void setTokenName(String val) {
        tokenName = val;
    }
    
    public String getTokenValue() {
        return tokenValue;
    }
    public void setTokenValue(String val) {
        tokenValue = val;
    }
    
    public String getTokenTypeStr() {
        return tokenTypeStr;
    }
    public void setTokenTypeStr(String val) {
        tokenTypeStr = val;
    }
    
    public boolean isInvalidTokenValue() {
        return invalidTokenValue;
    }
    public void setInvalidTokenValue(boolean val) {
        invalidTokenValue = val;
    }
    
    public boolean isInvalidTokenName() {
        return invalidTokenName;
    }
    public void setInvalidTokenName(boolean val) {
        invalidTokenName = val;
    }
    
    public boolean isMissingToken() {
        return missingToken;
    }
    public void setMissingToken(boolean val) {
        missingToken = val;
    }
    
    public String getSuppliedTokenName() {
        return suppliedTokenName;
    }
    public void setSuppliedTokenName(String val) {
        suppliedTokenName = val;
    }

    public String getTestName() {
        return testName;
    }
    public void setTestName(String val) {
        testName  = val;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String val) {
        errorMessage = val;
    }
    
    public String getHttpReferer() {
        return httpReferer;
    }
    public void setHttpReferer(String val) {
        httpReferer = val;
    }
}
