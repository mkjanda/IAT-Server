package net.iatsoftware.iat.events;

import net.iatsoftware.iat.generated.ResourceType;

public class TestResourcesRecordedEvent extends DeploymentTransactionEvent {
	private final ResourceType type;
    public TestResourcesRecordedEvent(String sessionId, Long deploymentId, ResourceType type) {
        super(sessionId, deploymentId);
		this.type = type;
    }

	public ResourceType getType() {
		return type;
	}
}
