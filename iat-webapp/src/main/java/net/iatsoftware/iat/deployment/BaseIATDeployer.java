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
import net.iatsoftware.iat.entities.EncryptedRSAKey;
import net.iatsoftware.iat.generated.TokenType;



public interface BaseIATDeployer  {
    void storeRSAKeys(EncryptedRSAKey adminKey, EncryptedRSAKey dataKey);
    void storeTokenDefinition(TokenType type, String tokenName);
    void abort();
    Long getTestId();
    void setClientId(Long id);
    void setTestId(Long id);
    void setDeploymentId(Long id);
    void setSession(SessionState session);
    SessionState session();
    void generateTest();
    void setReplyChannel(ReplyChannel replyChannel);
    ReplyChannel replyChannel();
}
