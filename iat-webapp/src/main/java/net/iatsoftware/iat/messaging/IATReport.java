/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.iatsoftware.iat.messaging;

import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import javax.inject.Inject;

/**
 *
 * @author Michael Janda
 */

@XmlRootElement(name = "IATReport")
@XmlAccessorType(XmlAccessType.NONE)
@Component
@Scope(value = "prototype")
public class IATReport extends net.iatsoftware.iat.generated.IATReportPojo {
    @Inject
    IATRepositoryManager iatRepositoryManager;

    public IATReport() {
    }

    public void load(IAT test) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
        iatName = test.getTestName();
        this.url = test.getURL();
        numAdministrations = test.getNumAdministrations();
        testSizeKB = test.getTestSizeKB();
        if (test.getLastDataRetrieval() == null)
            lastDataRetrieval = "never";
        else
            lastDataRetrieval = df.format(test.getLastDataRetrieval().getTime());
        if (test.getUser().getTitle() != null)
            authorTitle = test.getUser().getTitle();
        else
            authorTitle = "";
        authorFName = test.getUser().getFName();
        authorLName = test.getUser().getLName();
        authorEMail = test.getUser().getEMail();
        numResultSets = iatRepositoryManager.getNumResultSets(test); 
        DeploymentSession ds = null;
        try {
            ds = iatRepositoryManager.getDeploymentSession(test);
        } catch (jakarta.persistence.NoResultException ex) {
            ds = null;
        }
        deploying = ds != null;
    }
}
