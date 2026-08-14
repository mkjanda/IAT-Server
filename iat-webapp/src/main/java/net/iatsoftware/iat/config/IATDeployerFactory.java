package net.iatsoftware.iat.config;

import net.iatsoftware.iat.deployment.IATDeployer;
import net.iatsoftware.iat.deployment.IATRedeployer;

import org.springframework.web.socket.WebSocketSession;

public interface IATDeployerFactory {
    IATDeployer createDeployer(Long clientId, Long deploymentId, Long testId, WebSocketSession session);
    IATRedeployer createRedeployer(Long clientId, Long deploymentId, Long replacementTestId, Long testId, WebSocketSession session);
}
