package net.iatsoftware.iat.events;

import net.iatsoftware.iat.generated.ResourceType;

import org.springframework.web.socket.WebSocketSession;

public class TestResourcesRecordedEvent extends DeploymentTransactionEvent {
	private final ResourceType type;
    public TestResourcesRecordedEvent(WebSocketSession session, Long deploymentId, ResourceType type) {
        super(session, deploymentId);
		this.type = type;
    }

	public ResourceType getType() {
		return type;
	}
}
