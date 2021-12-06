/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.events;

/**
 *
 * @author michael
 */
public class TestDeploymentCompleteEvent extends DeploymentCompleteEvent {
    public TestDeploymentCompleteEvent(String sessId, Long deploymentSessionId) {
        super(sessId, deploymentSessionId, EResult.testDeployed);
    }
}
