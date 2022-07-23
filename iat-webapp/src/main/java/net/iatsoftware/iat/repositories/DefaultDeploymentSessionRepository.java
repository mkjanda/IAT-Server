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

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.events.CommunicationEvent;
import net.iatsoftware.iat.services.DeploymentService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class DefaultDeploymentSessionRepository extends GenericJpaRepository<Long, DeploymentSession>
        implements DeploymentSessionRepository {
    private static final Logger critical = LogManager.getLogger("critical");

    @Inject
    DeploymentService deploymentService;
    @Inject
    IATRepositoryManager repositoryManager;

    @Scheduled(initialDelay = 5_000L, fixedRate = 5_000L)
    protected void cleanupAbandonedDeployments() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<DeploymentSession> query = cb.createQuery(DeploymentSession.class);
        Root<DeploymentSession> root = query.from(DeploymentSession.class);
        var timeout = Calendar.getInstance();
        timeout.setTimeInMillis(System.currentTimeMillis() - DeploymentSession.DEPLOYMENT_TIMEOUT);
        Predicate pred = cb.lessThan(root.get("deploymentStart"), timeout);
        var sessions = this.entityManager.createQuery(query.select(root).where(pred)).getResultList();
        for (var sess : sessions) {
            repositoryManager.deleteIAT(sess.getId());
        }
    }


/*
    @Override
    public boolean verifyUploadKey(long deploymentID, String key) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<DeploymentSession> root = query.from(DeploymentSession.class);
        Predicate pred = cb.and(cb.equal(root.get("id"), deploymentID),
                cb.or(cb.equal(root.get("deploymentUploadKey"), key), cb.equal(root.get("itemSlideUploadKey"), key)));
        long result = this.entityManager.createQuery(query.select(cb.count(root)).where(pred)).getSingleResult();
        return (result > 0);
    }
    */
/*
    @Override
    public void deleteDeploymentSession(DeploymentSession ds) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<DeploymentSession> deletion = cb.createCriteriaDelete(DeploymentSession.class);
        Root<DeploymentSession> root = deletion.from(DeploymentSession.class);
        Predicate pred = cb.equal(root.get("id"), ds.getId());
        this.entityManager.createQuery(deletion.where(pred)).executeUpdate();
    }

    public int deleteDeploymentSession(Long dsId) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<DeploymentSession> deletion = cb.createCriteriaDelete(DeploymentSession.class);
        Root<DeploymentSession> root = deletion.from(DeploymentSession.class);
        Predicate pred = cb.equal(root.get("id"), dsId);
        return this.entityManager.createQuery(deletion.where(pred)).executeUpdate();
    }
*/
    public DeploymentSession get(IAT test) {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(DeploymentSession.class);
        var root = query.from(DeploymentSession.class);
        try {
            return this.entityManager.createQuery(query.select(root).where(cb.equal(root.get("test"), test))).getSingleResult();
        }
        catch (javax.persistence.NonUniqueResultException | javax.persistence.NoResultException ex) {
            return null;
        }
    }

    public DeploymentSession get(CommunicationEvent ce) {
        var cb = this.entityManager.getCriteriaBuilder();
        var query = cb.createQuery(DeploymentSession.class);
        var root = query.from(DeploymentSession.class);
        var pred = cb.equal(root.get("webSocketId"), ce.getSessionId());
        try {
            return this.entityManager.createQuery(query.select(root).where(pred)).getSingleResult();
        }
        catch (javax.persistence.NoResultException | javax.persistence.NonUniqueResultException ex) {
            return null;
        }
    }

}
