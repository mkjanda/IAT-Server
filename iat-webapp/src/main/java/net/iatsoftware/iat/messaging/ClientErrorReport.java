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

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.User;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ClientErrorReport")
@XmlAccessorType(XmlAccessType.NONE)
public class ClientErrorReport extends net.iatsoftware.iat.generated.ClientErrorReport {
    
    private static final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.LONG);
    
    public ClientErrorReport() {}
    
    public ClientErrorReport(Client c, User u, String version, ClientException ex, Calendar timestamp) {
        this.clientMessage = ex.getClientMessage();
        this.exception = ex.getException();
        this.productCode = ex.getProductCode();
        this.version = version;
        this.clientID = c.getClientId();
        this.timestamp = dateFormat.format(new Date(timestamp.getTimeInMillis()));
        this.userEmail = u.getEMail();
        this.userName = u.getFullName();
        this.historyEntry = ex.getHistoryEntry();
        this.version = ex.getVersion();
        this.saveFileVersion = ex.getSaveFileVersion();
        this.timeOpened = ex.getTimeOpened();
        this.eventLog = ex.getEventLog();
        this.errorCount = ex.getErrorCount();
        this.errorsReported = ex.getErrorsReported();
    }
    
}
