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


import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="ServerReport")
@XmlAccessorType(XmlAccessType.NONE)
public class ServerReport extends net.iatsoftware.iat.generated.GServerReport {
    
    public void load(long clientID, IATRepositoryManager iatRepositoryManager)
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
            IATReport report = new IATReport();
            var deploymentSession = iatRepositoryManager.getDeploymentSession(test);
            report.load(test, deploymentSession != null, iatRepositoryManager.getNumResultSets(test));
            this.getIATReport().add(report);
        }
    }
}
