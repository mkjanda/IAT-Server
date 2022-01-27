package net.iatsoftware.iat.events;

import net.iatsoftware.iat.messaging.ItemSlideManifest;

public class ItemSlideManifestReceivedEvent extends DeploymentTransactionEvent {
    private final ItemSlideManifest manifest;
    
    public ItemSlideManifestReceivedEvent(String sessionId, Long deploymentID, ItemSlideManifest manifest)
    {
        super(sessionId, deploymentID);
        this.manifest = manifest;
    }
    
    public ItemSlideManifest getManifest() {
        return this.manifest;
    }
}
