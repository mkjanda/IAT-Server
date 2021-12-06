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

public interface DeploymentSessionRepository extends GenericRepository<Long, DeploymentSession> {
    boolean verifyUploadKey(long deploymentID, String key);
    void deleteDeploymentSession(DeploymentSession ds);
    int deleteDeploymentSession(Long dsId);
}
