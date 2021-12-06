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
public class UploadRequestEvent extends DeploymentTransactionEvent {
    public UploadRequestEvent(String sessId, Long deploymentSessionID) {
        super(sessId, deploymentSessionID);
    }
}
