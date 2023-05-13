package net.iatsoftware.iat.events;

public class BeginDeploymentEvent extends DeploymentEvent {
    public BeginDeploymentEvent(Long dsId) {
        super(dsId);
    }
}
