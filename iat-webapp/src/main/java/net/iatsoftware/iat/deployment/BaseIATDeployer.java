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

import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.generated.TokenType;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;

import org.springframework.web.socket.WebSocketSession;


public interface BaseIATDeployer  {
    void requestUpload(WebSocketSession session);
    void storeRSAKeys(PartiallyEncryptedRSAKey adminKey, PartiallyEncryptedRSAKey dataKey);
    void storeTokenDefinition(TokenType type, String tokenName);
    void setFailed(WebSocketSession session, ServerExceptionMessage ex);
    void setSuccess(WebSocketSession session);
    void abort();
    Long getTestId();
    void setClientId(Long id);
    void setTestId(Long id);
    void setDeploymentId(Long id);
    void setSessionId(String sessId);
    void generateTest();
}
