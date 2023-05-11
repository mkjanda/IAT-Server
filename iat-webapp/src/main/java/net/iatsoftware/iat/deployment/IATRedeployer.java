/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.deployment;

/**
 *
 * @author Michael Janda
 */
public interface IATRedeployer extends IATDeployer {
    void onDescriptorMismatch(String sessionId);
    void setOldTestId(Long val);
}
