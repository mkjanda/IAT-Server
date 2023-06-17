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

import net.iatsoftware.iat.config.MyBeanFactory;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import javax.inject.Inject;

@XmlRootElement(name="ServerReport")
@XmlAccessorType(XmlAccessType.NONE)
@Component
@Scope(value="prototype")
public class ServerReport extends net.iatsoftware.iat.generated.GServerReport {
    @Inject IATRepositoryManager iatRepositoryManager;
    @Inject MyBeanFactory beanFactory;
    
    public ServerReport() 
    {
    }
    
    public void load(long clientID)
    {
        Client client = iatRepositoryManager.getClient(clientID);
        contactFName = client.getContactFName();
        contactLName = client.getContactLName();
        organization = client.getOrganization();
        if (client.getNumIATsAlotted() == null)
            numIATsAlotted = -1;
        else
            numIATsAlotted = client.getNumIATsAlotted();
        numAdministrations = client.getNumAdministrations();
        if (client.getAdministrationsRemaining() == null)
            numAdministrationsRemaining = -1;
        else
            numAdministrationsRemaining = client.getAdministrationsRemaining();
        if (client.getDiskAlottmentMB() == null)
            diskAlottmentMB = -1;
        else
            diskAlottmentMB = client.getDiskAlottmentMB();
        long totalDiskUsageKB = iatRepositoryManager.getClientDiskUsageKB(client);
        if (diskAlottmentMB == -1)
            diskAlottmentRemainingKB = -1;
        else
            diskAlottmentRemainingKB = (diskAlottmentMB << 10) - totalDiskUsageKB;
        List<IAT> tests = iatRepositoryManager.getIATs(client);
        for (IAT test : tests) {
            IATReport report = beanFactory.IATReport();
            report.load(test);
            this.getIATReport().add(report);
        }
    }
}
