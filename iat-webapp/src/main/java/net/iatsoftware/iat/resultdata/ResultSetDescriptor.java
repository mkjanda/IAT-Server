/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultdata;

/**
 *
 * @author Michael Janda
 */
import net.iatsoftware.iat.configfile.ConfigFile;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.EncryptedRSAKey;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "ResultSetDescriptor")
@XmlAccessorType(XmlAccessType.NONE)
public class ResultSetDescriptor extends net.iatsoftware.iat.generated.GResultSetDescriptor {

    public ResultSetDescriptor() {
    }

    public void load(IAT test, ConfigFile configFile, EncryptedRSAKey dataKey, int numResults) {
        this.testAuthor = test.getClient().getFirstName() + " " + test.getClient().getLastName();
        this.dataVersion = test.getResultFormat();
        this.rsaKey = dataKey;
        this.numResults = numResults;
    }

}
