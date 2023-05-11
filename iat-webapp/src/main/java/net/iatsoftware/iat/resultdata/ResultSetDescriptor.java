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
import net.iatsoftware.iat.generated.ResourceType;
import net.iatsoftware.iat.generated.TokenType;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.transform.stream.StreamSource;

@Component
@Scope(value = "prototype")
@XmlRootElement(name = "ResultSetDescriptor")
@XmlAccessorType(XmlAccessType.NONE)
public class ResultSetDescriptor extends net.iatsoftware.iat.generated.ResultSetDescriptorPojo {

    private static final Base64.Encoder encoder = Base64.getEncoder();
    @Inject
    IATRepositoryManager iatRepositoryManager;
    @Inject
    Marshaller marshaller;
    @Inject
    Unmarshaller unmarshaller;

    public ResultSetDescriptor() {
    }

    public void load(Long clientID, String testName) throws java.io.IOException, java.net.URISyntaxException {
        IAT test = iatRepositoryManager.getIATByNameAndClientID(testName, clientID);
        var cfData = iatRepositoryManager.getTestResource(test, 0L).getResourceBytes();
        var cfSource = new StreamSource(new StringReader(new String(cfData, StandardCharsets.UTF_16)));
      //  var cf = (ConfigFile)this.unmarshaller.unmarshal(cfSource);
        this.configFile = encoder.encodeToString(cfData);
        this.tokenType = test.getTokenType();
        if (this.tokenType != TokenType.NONE)
            this.tokenName = test.getTokenName();
        else
            this.tokenName = null;
        this.testAuthor = test.getUser().getFName() + " " + test.getUser().getLName();
        this.dataVersion = test.getResultFormat();
        this.tokenName = test.getTokenName();
        this.tokenType = test.getTokenType();
        this.rsaKey = iatRepositoryManager.getDataKey(clientID, testName);
        this.numResults = (int) iatRepositoryManager.getNumResults(clientID, testName);
        this.afterSurvey = new ArrayList<>();
        this.beforeSurvey = new ArrayList<>();
        /*
        for (var survey : cf.getSurvey()) {
            var surveyB64 = encoder.encodeToString(iatRepositoryManager.getTestResource(test, survey.getSurveyName(),
                ResourceType.DEPLOYMENT_FILE).getResourceBytes());
            if (survey.isIsBeforeSurvey())
                this.beforeSurvey.add(surveyB64);
            else
                this.afterSurvey.add(surveyB64);
        }
        */
    }
}
