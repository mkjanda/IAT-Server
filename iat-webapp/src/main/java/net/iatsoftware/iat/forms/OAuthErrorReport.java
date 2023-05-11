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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="OAuthErrorReport")
public class OAuthErrorReport {
    private String []stackTrace = null;
    private String clientId, exceptionCaption, exceptionMessage, contactEmail, contactName, testName;
    private boolean reported;
    
    public OAuthErrorReport(){}
    
    @XmlElement(name="ContactName")
    public String getContactName() {
        return this.contactName;
    }
    public void setContactName(String val) {
        this.contactName = val;
    }        
    
    @XmlElement(name="ContactEmail")
    public String getContactEmail() {
        return this.contactEmail;
    }
    public void setContactEmail(String val) {
        this.contactEmail = val;
    }
    
    @XmlElement(name="TestName") 
    public String getTestName() {
        return this.testName;
    }
    public void setTestName(String val) {
        this.testName = val;
    }
    
    @XmlElement(name="Reported") 
    public boolean isReported() {
        return this.reported;
    }
    public void setReported(boolean val) {
        this.reported = val;
    }
    
    @XmlElement(name="ClientId")
    public String getClientId() {
        return this.clientId;
    }
    public void setClientId(String val) {
        this.clientId = val;
    }
    
    @XmlElement(name="ExceptionCaption")
    public String getExceptionCaption() {
        return this.exceptionCaption;
    }
    public void setExceptionCaption(String val) {
        this.exceptionCaption = val;
    }
    
    @XmlElement(name="ExceptionMessage")
    public String getExceptionMessage() {
        return this.exceptionMessage;
    }
    public void setExceptionMessage(String val) {
        this.exceptionMessage = val;
    }
    
    @XmlElement(name="StackTrace")
    public String[] getStackTrace() {
        return this.stackTrace;
    }
    public void setStackTrace(String[] val) {
        this.stackTrace = val;
    }
}
