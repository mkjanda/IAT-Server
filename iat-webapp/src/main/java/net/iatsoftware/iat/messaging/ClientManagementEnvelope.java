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

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ClientManagementEnvelope")
@XmlAccessorType(XmlAccessType.NONE)
public class ClientManagementEnvelope extends net.iatsoftware.iat.generated.ClientManagementEnvelopePojo {
    
    public ClientManagementEnvelope() {}
    
    public ClientManagementEnvelope(Message msg) {
        if (msg instanceof AcceptConfirmation)
            this.setAcceptConfirmation((AcceptConfirmation)msg);
        else if (msg instanceof ProductRequests)
            this.setProductRequests((ProductRequests)msg);
        else if (msg instanceof Transaction)
            this.setTransaction((Transaction)msg);
        else if (msg instanceof UsageReport)
            this.setUsageReport((UsageReport)msg);
    }
}
