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

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaDelete;

import net.iatsoftware.iat.entities.ResultSet;
import net.iatsoftware.iat.entities.IAT;

import java.util.Calendar;
import java.util.List;

@Repository
public class DefaultResultSetRepository extends GenericJpaRepository<Long, ResultSet>
    implements ResultSetRepository
{
    @Override
    public long getNumResults(IAT test) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<ResultSet> root = query.from(ResultSet.class);
        Predicate pred = cb.equal(root.get("test"), test);
        return this.entityManager.createQuery(query.select(cb.count(root)).where(pred)).getSingleResult();
    }
    
    @Override
    public List<ResultSet> getResults(IAT test) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<ResultSet> query = cb.createQuery(ResultSet.class);
        Root<ResultSet> root = query.from(ResultSet.class);
        Predicate pred = cb.equal(root.get("test"), test);
        return this.entityManager.createQuery(query.where(pred)).getResultList();
    }
    
    @Override
    public void deleteResults(IAT test) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<ResultSet> deleteStatement = cb.createCriteriaDelete(ResultSet.class);
        Root<ResultSet> root = deleteStatement.from(ResultSet.class);
        Predicate pred = cb.equal(root.get("test"), test);
        this.entityManager.createQuery(deleteStatement.where(pred)).executeUpdate();
    }
    
    @Override
    public void reassociateResults(IAT newTest, IAT oldTest) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<ResultSet> update = cb.createCriteriaUpdate(ResultSet.class);
        Root<ResultSet> root = update.from(ResultSet.class);
        Predicate pred = cb.equal(root.get("test"), oldTest);
        this.entityManager.createQuery(update.where(pred).set(root.get("test"), newTest)).executeUpdate();
    }
    

    @Override
    public Calendar getLastIATAdminDate(List<IAT> tests) {
        try {
            if (tests.isEmpty())
                return null;
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Calendar> query = cb.createQuery(Calendar.class);
            Root<ResultSet> root = query.from(ResultSet.class);
            Predicate pred = root.get("test").in(tests);
            return this.entityManager.createQuery(query.select(cb.greatest(root.get("adminTime").as(Calendar.class))).where(pred)).getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }
    
    @Override
    public ResultSet getResultSetWithToken(IAT test, byte[] token) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<ResultSet> query = cb.createQuery(ResultSet.class);
        Root<ResultSet> root = query.from(ResultSet.class);
        Predicate pred = cb.and(cb.equal(root.get("test"), test), cb.equal(root.get("testeeToken"), token));
        return this.entityManager.createQuery(query.select(root).where(pred)).getSingleResult();
    }
    
}
