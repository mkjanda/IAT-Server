/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

/**
 *
 * @author michael
 */
public class ResultDownloadRequest {
    private long clientId;
    private String authToken, testName;
    
    public ResultDownloadRequest(){}
    
    public long getClientId() {
        return clientId;
    }
    public void setClientId(long val) {
        clientId = val;
    }
    
    public String getAuthToken() {
        return this.authToken;
    }
    public void setAuthToken(String val) {
        this.authToken = val;
    }
    
    public String getTestName() {
        return testName;
    }
    public void setTestName(String val) {
        testName = val;
    }
}
