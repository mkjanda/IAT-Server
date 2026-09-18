/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.iatsoftware.iat.messaging;

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.IAT;

import java.text.DateFormat;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

/**
 *
 * @author Michael Janda
 */

@XmlRootElement(name = "IATReport")
@XmlAccessorType(XmlAccessType.NONE)
public class IATReport extends net.iatsoftware.iat.generated.GIATReport {

    public IATReport() {
    }

    public void load(IAT test, Client client, int numResultSets) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
        this.setProductKey(client.getProductKey());
        this.testName = test.getTestName();
        this.url = test.getURL();
        this.numAdministrations = test.getNumAdministrations();
        this.testSizeKB = test.getTestSizeKB();
        this.uploadTimestamp = df.format(test.getUploadTimestamp().getTime());
        if (test.getLastDataRetrieval() == null)
            lastDataRetrieval = "never";
        else
            lastDataRetrieval = df.format(test.getLastDataRetrieval().getTime());
        this.authorName = test.getClient().getName();
        this.setNumResultSets(numResultSets);   
        this.testVersion = test.getVersion();
    }
}
