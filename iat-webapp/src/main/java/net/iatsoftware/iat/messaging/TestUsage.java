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

import net.iatsoftware.iat.entities.IAT;

import java.text.DateFormat;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name="TestUsage")
@XmlAccessorType(XmlAccessType.NONE)
public class TestUsage extends net.iatsoftware.iat.generated.GTestUsage {
    
    public TestUsage() {}
    
    public TestUsage(IAT test) {
        DateFormat df = DateFormat.getDateTimeInstance();
        this.setAuthorFName(test.getUser().getFName());
        this.setAuthorLName(test.getUser().getLName());
        this.setAuthorTitle(test.getUser().getTitle());
        if (test.getLastDataRetrieval() == null)
            this.setLastDataRetrieval("Never");
        else
            this.setLastDataRetrieval(df.format(test.getLastDataRetrieval().getTime()));
        this.setNumAdministrations(test.getNumAdministrations());
        this.setTestName(test.getTestName());
        this.setTestSizeKB(test.getTestSizeKB());
        this.setUploadTimestamp(df.format(test.getUploadTimestamp().getTime()));
    }
    
}
