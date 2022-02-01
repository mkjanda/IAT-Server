package net.iatsoftware.iat.events;

import net.iatsoftware.iat.messaging.Manifest;

public class ItemSlideManifestReceivedEvent extends DeploymentTransactionEvent {
    private final Manifest manifest;
    
    public ItemSlideManifestReceivedEvent(String sessionId, Long deploymentID, Manifest manifest)
    {
        super(sessionId, deploymentID);
        this.manifest = manifest;
    }
    
    public Manifest getManifest() {
        return this.manifest;
    }
}
