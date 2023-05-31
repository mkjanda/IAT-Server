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

import java.util.List;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlType(name="UsageReport")
@XmlAccessorType(XmlAccessType.NONE)
public class UsageReport extends net.iatsoftware.iat.generated.UsageReportPojo {
    public UsageReport(){}
    
    public UsageReport(ClientUsage cu) {
        super();
        getClientUsage().add(cu);
    }
    
    public UsageReport(List<ClientUsage> cuList) {
        super();
        getClientUsage().addAll(cuList);
    }
}
