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
import net.iatsoftware.iat.generated.TokenType;
import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "ResultSetDescriptor")
@XmlAccessorType(XmlAccessType.NONE)
public class ResultSetDescriptor extends net.iatsoftware.iat.generated.GResultSetDescriptor {

    public ResultSetDescriptor() {
    }

    public void load(IAT test, ConfigFile configFile, PartiallyEncryptedRSAKey dataKey, int numResults) throws java.io.IOException, java.net.URISyntaxException {
        this.tokenType = test.getTokenType();
        if (this.tokenType != TokenType.NONE)
            this.tokenName = test.getTokenName();
        else
            this.tokenName = null;
        this.testAuthor = test.getUser().getFName() + " " + test.getUser().getLName();
        this.dataVersion = test.getResultFormat();
        this.tokenName = test.getTokenName();
        this.tokenType = test.getTokenType();
        this.rsaKey = dataKey;
        this.numResults = numResults;
    }

    public void setTest(IAT test) {
        this.testAuthor = test.getUser().getFName() + " " + test.getUser().getLName();
        this.dataVersion = test.getResultFormat();
        this.tokenName = test.getTokenName();
        this.tokenType = test.getTokenType();
    }    

    @Override
    protected boolean doBeforeMarshal(jakarta.xml.bind.Marshaller m) {
        this.setConfigFile("<![CDATA[" + this.configFile + "]]>");
        return true;
    }
}
