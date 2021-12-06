/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.events;

/**
 *
 * @author Michael Janda
 */

public class DeploymentCompleteEvent extends DeploymentTransactionEvent {
    public enum EResult { itemSlidesDeployed, testDeployed, failure, halt, deploymentDescriptorMismatch };
    private final EResult result;
    
    public DeploymentCompleteEvent(String sess, Long deploymentID, EResult result) {
        super(sess, deploymentID);
        this.result = result;
    }
    
    public EResult getResult() {
        return this.result;
    }
}
