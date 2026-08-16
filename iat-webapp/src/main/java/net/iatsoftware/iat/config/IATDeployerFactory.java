package net.iatsoftware.iat.config;

import net.iatsoftware.iat.communication.ReplyChannel;
import net.iatsoftware.iat.deployment.IATDeployer;

import org.springframework.web.socket.WebSocketSession;

public interface IATDeployerFactory {
    IATDeployer createDeployer(Long clientId, Long deploymentId, Long testId, ReplyChannel replyChannel, WebSocketSession session);
}
