package net.iatsoftware.iat.events;



import net.iatsoftware.iat.messaging.Manifest;

import org.springframework.web.socket.WebSocketSession;

public class ItemSlideManifestReceivedEvent extends DeploymentTransactionEvent {
    private final Manifest manifest;
    
    public ItemSlideManifestReceivedEvent(WebSocketSession session, Long deploymentID, Manifest manifest)
    {
        super(session, deploymentID);
        this.manifest = manifest;
    }
    
    public Manifest getManifest() {
        return this.manifest;
    }
}
