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


import org.springframework.stereotype.Repository;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.TypedQuery;

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.IAT;

@Repository
public class DefaultIATRepository extends GenericJpaRepository<Long, IAT>
        implements IATRepository {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean containsByProductKey(String productKey, String iatName) {
        TypedQuery<Boolean> existsQuery = this.entityManager
                .createQuery("select count(t) from test t where t.product_key = :prodKey and t.test_name = :testName",
                        Boolean.class)
                .setParameter("prodKey", productKey).setParameter("testName", iatName);
        return existsQuery.getSingleResult();
    }

    @Override
    public void setDescriptor(Long testID, byte[] descriptor) {
        IAT test = this.get(testID);
        test.setDeploymentDescriptor(descriptor);
        this.update(test);
    }

    public IAT get(String testName, Long clientId) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            var clientQuery = cb.createQuery(Client.class);
            var clientRoot = clientQuery.from(Client.class);
            var client = this.entityManager
                    .createQuery(clientQuery.where(cb.equal(clientRoot.get("clientId"), clientId))).getSingleResult();
            var iatQuery = cb.createQuery(IAT.class);
            var iatRoot = iatQuery.from(IAT.class);
            Predicate pred1 = cb.equal(iatRoot.get("client"), client);
            Predicate pred2 = cb.equal(iatRoot.get("testName"), testName);
            return this.entityManager.createQuery(iatQuery.where(pred1, pred2)).getSingleResult();
        } catch (jakarta.persistence.NonUniqueResultException | jakarta.persistence.NoResultException ex) {
            logger.error("No such IAT", ex);
            return null;
        }
    }

    @Override
    public boolean iatExists(Client client, String testName) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<IAT> root = query.from(IAT.class);
        Predicate pred1 = cb.equal(root.get("client"), client);
        Predicate pred2 = cb.equal(root.get("testName"), testName);
        return (this.entityManager.createQuery(query.select(cb.count(root.get("id"))).where(pred1, pred2))
                .getSingleResult() > 0L);
    }

    @Override
    public long getNumIATs(Client client) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<IAT> root = query.from(IAT.class);
        query.select(cb.count(root));
        Predicate pred = cb.equal(root.get("client"), client);
        query.where(pred);
        return this.entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Long getConsumedDiskSpaceKB(Client client) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<IAT> root = query.from(IAT.class);
        query.select(cb.sumAsLong(root.get("testSizeKB").as(Integer.class)));
        Predicate pred = cb.equal(root.get("client"), client);
        Long result = this.entityManager.createQuery(query.where(pred)).getSingleResult();
        if (result == null)
            return 0L;
        return result;
    }

    @Override
    public int getResultDataFormat(IAT test) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
        Root<IAT> root = query.from(IAT.class);
        Predicate pred = cb.equal(root.get("id"), test.getId());
        return this.entityManager.createQuery(query.select(root.get("resultFormat")).where(pred)).getSingleResult();
    }

    @Override
    public void creditAdministration(IAT test) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<IAT> update = cb.createCriteriaUpdate(IAT.class);
        Root<IAT> iat = update.from(IAT.class);
        update.set("numAdministrations", cb.diff(iat.get("numAdministrations"), 1));
        Predicate pred = cb.equal(iat.get("id"), test.getId());
        this.entityManager.createQuery(update.where(pred)).executeUpdate();
    }

    @Override
    public void debitAdministration(IAT test) {
        test.setNumAdministrations(test.getNumAdministrations() + 1);
        update(test);
    }

    @Override
    public List<IAT> getIATsByClient(Client c) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<IAT> query = cb.createQuery(IAT.class);
        Root<IAT> root = query.from(IAT.class);
        Predicate pred = cb.equal(root.get("client"), c);
        return this.entityManager.createQuery(query.where(pred)).getResultList();
    }

    @Override
    public Long getDiskUsageByClient(Client c) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<IAT> root = query.from(IAT.class);
        Predicate pred = cb.equal(root.get("client"), c);
        query.where(pred);
        query.select(cb.sumAsLong(root.get("testSizeKB")));
        return this.entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Calendar getLastIATUploadDate(Client c) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Calendar> query = cb.createQuery(Calendar.class);
            Root<IAT> root = query.from(IAT.class);
            Predicate pred = cb.equal(root.get("client"), c);
            return this.entityManager
                    .createQuery(query.select(cb.greatest(root.get("uploadTimestamp").as(Calendar.class))).where(pred))
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Calendar getLastDataRetrievalDate(Client c) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Calendar> query = cb.createQuery(Calendar.class);
            Root<IAT> root = query.from(IAT.class);
            Predicate pred = cb.equal(root.get("client"), c);
            return this.entityManager
                    .createQuery(
                            query.select(cb.greatest(root.get("lastDataRetrieval").as(Calendar.class))).where(pred))
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<IAT> getExpiredTestResults(long timeout) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<IAT> query = cb.createQuery(IAT.class);
        Root<IAT> root = query.from(IAT.class);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime() - timeout);
        Predicate pred = cb.and(cb.notEqual(root.get("resultRetrievalTokenAge"),
                cb.nullLiteral(Calendar.class)), cb.lessThan(root.get("resultRetrievalTokenAge"), cal));
        return this.entityManager.createQuery(query.select(root).where(pred)).getResultList();
    }

    @Override
    public boolean clientIdMatchesClientSecret(String id, String secret) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<IAT> root = query.from(IAT.class);
        Predicate pred = cb.and(cb.equal(root.get("oauthClientId"), id),
                cb.equal(root.get("oauthClientSecret"), secret));
        return this.entityManager.createQuery(query.select(cb.count(root)).where(pred)).getSingleResult() > 0;
    }
}
