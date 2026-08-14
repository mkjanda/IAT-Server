/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.events;

import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author michael
 */
public class DeploymentSuccessEvent extends DeploymentTransactionEvent {
    private final Long testId;
    public DeploymentSuccessEvent(WebSocketSession session, long deploymentId, Long testId) {
        super(session, deploymentId);
        this.testId = testId;
    }

    public Long getTestId() {
        return testId;
    }
}
