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
public class TestAbortParams {
    private String iatName ="", clientId = "", serverPath = "", tokenName = null, tokenValue = null, httpReferer = "", sessionId = "", version = "";
    private String corruptCookieName = "corrupted";
    private boolean corruptAdministration = false, multipleAdministrations = false;
    private int adminPhase;
    
    public String getIatName() {
        return this.iatName;
    }
    public void setIatName(String val) {
        this.iatName = val;
    }
    
    public String getClientId() {
        return this.clientId;
    }
    public void setClientId(String val) {
        this.clientId = val;
    }
    
    public String getTokenName() {
        return this.tokenName;
    }
    public void setTokenName(String val) {
        this.tokenName = val;
    }
    
    public String getTokenValue() {
        return this.tokenValue;
    }
    public void setTokenValue(String val) {
        this.tokenValue = val;
    }

    public String getHttpReferer() {
        return this.httpReferer;
    }
    public void setHttpReferer(String val) {
        this.httpReferer = val;
    }
    
    public String getSessionId() {
        return this.sessionId;
    }
    public void setSessionId(String val) {
        this.sessionId = val;
    }
    
    public String getVersion() {
        return this.version;
    }
    public void setVersion(String val) {
        this.version = val;
    }
           
    public boolean isCorruptAdministration() {
        return this.corruptAdministration;
    }
    public void setCorruptAdministration(boolean val) {
        this.corruptAdministration = val;
    }
    
    public boolean isMultipleAdministrations() {
        return this.multipleAdministrations;
    }
    public void setMultipleAdministrations(boolean val) {
        this.multipleAdministrations = val;
    }
    
    public String getCorruptCookieName() {
        return this.corruptCookieName;
    }
    public void setCorruptCookieName(String val) {
        this.corruptCookieName = val;
    }
    
    public String getServerPath() {
        return this.serverPath;
    }
    public void setServerPath(String val) {
        this.serverPath = val;
    }
    
    public String getRestartLink() {
        if (tokenName == null)
            return serverPath + "?IATName=" + iatName + "&ClientID=" + clientId;
        else
            return serverPath + "?IATName=" + iatName + "&ClientID=" + clientId + "&" + tokenName + "=" + tokenValue;
    }
    
    public boolean hasReferer() {
        if (httpReferer == null)
            return false;
        return (!httpReferer.equals("") && !httpReferer.equals("-"));
    }
    
    public String getRelativeUrl(String path) {
        return serverPath + "/" + path;
    }
    
    public int getAdminPhase() {
        return this.adminPhase;
    }
    public void setAdminPhase(int val) {
        this.adminPhase = val;
    }
}
