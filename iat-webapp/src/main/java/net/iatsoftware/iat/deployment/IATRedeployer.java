/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.deployment;
import org.springframework.web.socket.WebSocketSession;
/**
 *
 * @author Michael Janda
 */
public interface IATRedeployer extends IATDeployer {
    void onDescriptorMismatch(WebSocketSession session);
    void setOldTestId(Long val);
}
