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
import net.iatsoftware.iat.entities.User;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;


@XmlType(name="ClientUsage")
@XmlAccessorType(XmlAccessType.NONE)
@Component("ClientUsage")
@Scope("prototype")
public class ClientUsage extends net.iatsoftware.iat.generated.ClientUsagePojo {
    private static DecimalFormat decFormat = new DecimalFormat("####.##");
    public ClientUsage() {}
    
    public ClientUsage(Client c, List<User> users, List<IAT> tests) {
        DateFormat df = DateFormat.getDateTimeInstance();
        this.setActivationsConsumed(Integer.toString(c.getActivationsConsumed()));
        if (c.getActivationsRemaining() == null)
            this.setActivationsRemaining("unlimited");
        else
            this.setActivationsRemaining(Integer.toString(c.getActivationsRemaining()));
        this.setAdministrations(Integer.toString(c.getNumAdministrations()));
        if (c.getAdministrationsRemaining() == null)
            this.setAdministrationsRemaining("unlimited");
        else
            this.setAdministrationsRemaining(Integer.toString(c.getAdministrationsRemaining()));
        this.setClientID(c.getClientId());
        this.setProductKey(c.getProductKey());
        this.setContactFName(c.getContactFName());
        this.setContactLName(c.getContactLName());
        this.setDeleted(c.isDeleted());
        if (c.getDiskAlottmentMB() == null)
            this.setDiskAlottmentMB("unlimited");
        else
            this.setDiskAlottmentMB(Integer.toString(c.getDiskAlottmentMB()));
        this.setDownloadPassword(c.getDownloadPassword());
        this.setDownloadsConsumed(Integer.toString(c.getDownloadsConsumed()));
        if (c.getDownloadsRemaining() == null) 
            this.setDownloadsRemaining("unlimited");
        else
            this.setDownloadsRemaining(Integer.toString(c.getDownloadsRemaining()));
        this.setEMail(c.getEmail());
        this.setFrozen(c.isFrozen());
        this.setNumIATs(Integer.toString(tests.size()));
        if (c.getNumIATsAlotted() == null)
            this.setNumIATsAlotted("unlimited");
        else
            this.setNumIATsAlotted(Integer.toString(c.getNumIATsAlotted()));
        this.setProductUse(c.getProductUse());
        if (this.getProductUse() == null)
            this.setProductUse("N/A");
        this.setRegistrationDate(df.format(c.getRegistrationDate().getTime()));
        this.userData = new ArrayList<>();
        for (User u : users) {
            this.userData.add(new UserData(u));
        }
        this.testUsage = new ArrayList<>();
        int diskSpaceConsumedKB = 0;
        for (IAT t : tests) {
            this.testUsage.add(new TestUsage(t));
            diskSpaceConsumedKB += t.getTestSizeKB();
        }
        
        this.setTotalDiskUsageMB(decFormat.format((double)diskSpaceConsumedKB / 1024D));
    }
    
    @Override
    public boolean doBeforeMarshal(Marshaller m) {
        if (!this.productUse.matches("<!\\[CDATA\\[.*?\\]\\]>"))
            this.productUse = "<![CDATA[" + super.getProductUse() + "]]>";
        return true;
    }
}
