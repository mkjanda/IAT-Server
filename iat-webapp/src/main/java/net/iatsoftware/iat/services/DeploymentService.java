/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.services;



/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.communication.SessionState;
import net.iatsoftware.iat.communication.ReplyChannel;
import net.iatsoftware.iat.deployment.IATDeployer;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.User;

import org.springframework.web.socket.WebSocketSession;

public interface DeploymentService {
    long beginNewDeployment(Client c, User u, String testName, SessionState session, ReplyChannel replyChannel) throws java.io.IOException, java.net.URISyntaxException;
    void completeDeployment(Long deploymentId) throws java.io.IOException, java.net.URISyntaxException;
    IATDeployer getDeployer(long deploymentId);   
    void setWebSocketSessionState(long deploymentId, SessionState state);
}
