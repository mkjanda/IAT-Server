/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.repositories;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.events.CommunicationEvent;

public interface DeploymentSessionRepository extends GenericRepository<Long, DeploymentSession> {
    void cleanupAbandonedDeployments();
    DeploymentSession get(IAT test);    
    DeploymentSession get(CommunicationEvent ce);
}
