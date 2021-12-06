/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.events;

/**
 *
 * @author michael
 */

import net.iatsoftware.iat.messaging.TokenDefinition;
import net.iatsoftware.iat.generated.TokenType;


public class TokenDefinitionReceivedEvent extends DeploymentTransactionEvent {
    
    private TokenType tokenType;
    private String tokenName;
    
    public TokenDefinitionReceivedEvent(String sessId, Long deploymentId, TokenDefinition tokenDefinition) {
        super(sessId, deploymentId);
        tokenName = tokenDefinition.getTokenName();
        tokenType = tokenDefinition.getTokenType();
    }
    
    public TokenType getTokenType() {
        return this.tokenType;
    }
    
    public String getTokenName() {
        return this.tokenName;
    }
}
