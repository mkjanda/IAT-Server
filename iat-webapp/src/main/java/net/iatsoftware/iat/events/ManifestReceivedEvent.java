/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.events;

import net.iatsoftware.iat.messaging.Manifest;


public class ManifestReceivedEvent extends DeploymentTransactionEvent {
	private final Manifest manifest;

    public ManifestReceivedEvent(String sessionId, Long deploymentID, Manifest manifest)
    {
        super(sessionId, deploymentID);
		this.manifest = manifest;

    }
    
    public Manifest getManifest() {
        return this.manifest;
    }
}
