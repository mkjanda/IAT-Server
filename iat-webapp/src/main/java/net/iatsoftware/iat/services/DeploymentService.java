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

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.User;

import org.springframework.web.socket.WebSocketSession;


import java.util.Calendar;

public interface DeploymentService {
    Calendar beginNewDeployment(Client c, User u, String testName, WebSocketSession session) throws java.io.IOException, java.net.URISyntaxException, java.nio.file.NoSuchFileException;
    Calendar beginNewRedeployment(Client c, User u, String testName, IAT oldTest, WebSocketSession session)
            throws java.io.IOException, java.net.URISyntaxException, java.nio.file.NoSuchFileException;
    void completeDeployment(DeploymentSession ds) throws java.io.IOException, java.net.URISyntaxException;
}
