/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.deployment;


/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.communication.ReplyChannel;
import net.iatsoftware.iat.communication.SessionState;
import net.iatsoftware.iat.entities.Crypt;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

public interface IATDeployer  {
    void storeRSAKeys(Crypt adminKey, Crypt dataKey);
    void abort();
    Long getTestId();
    void setClientId(Long id);
    void setTestId(Long id);
    void setDeploymentId(Long id);
    Long getDeploymentId();
    void setSession(SessionState session);
    void setReplyChannel(ReplyChannel replyChannel);
    void setRepositoryManager(IATRepositoryManager repositoryManager);
    SessionState session();
    void generateTest();
    ReplyChannel replyChannel();
}
