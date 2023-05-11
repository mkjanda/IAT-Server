package net.iatsoftware.iat.config;

import net.iatsoftware.iat.deployment.IATDeployer;
import net.iatsoftware.iat.deployment.IATRedeployer;

public interface IATDeployerFactory {
    IATDeployer createDeployer(Long clientId, Long deploymentId, Long testId, String session);
    IATRedeployer createRedeployer(Long clientId, Long deploymentId, Long replacementTestId, Long testId, String sessId);
}
