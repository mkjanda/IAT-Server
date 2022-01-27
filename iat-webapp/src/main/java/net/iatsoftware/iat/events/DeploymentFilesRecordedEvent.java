package net.iatsoftware.iat.events;

public class DeploymentFilesRecordedEvent extends DeploymentTransactionEvent {
    public DeploymentFilesRecordedEvent(String sessionId, Long deploymentId) {
        super(sessionId, deploymentId);
    }
}
