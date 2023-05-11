/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.events;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.messaging.RSAKeyPair;

public class RSAKeyReceivedEvent extends DeploymentTransactionEvent {
    private PartiallyEncryptedRSAKey adminKey, dataKey;
    
    public RSAKeyReceivedEvent(String sessId, Long deploymentID, RSAKeyPair keyPair) {
        super(sessId, deploymentID);
        this.adminKey = keyPair.getAdminKey();
        this.dataKey = keyPair.getDataKey();
    }

    public PartiallyEncryptedRSAKey getAdminKey() {
        return this.adminKey;
    }
    
    public PartiallyEncryptedRSAKey getDataKey() {
        return this.dataKey;
    }
}
