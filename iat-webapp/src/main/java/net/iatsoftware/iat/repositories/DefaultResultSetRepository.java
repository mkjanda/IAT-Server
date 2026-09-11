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

import net.iatsoftware.iat.resultdata.ResultSet;

import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaDelete;

import net.iatsoftware.iat.entities.EncryptedResultSet;
import net.iatsoftware.iat.entities.IAT;

import java.util.Calendar;
import java.util.List;
import jakarta.inject.Inject;

import org.springframework.oxm.Marshaller;

@Repository
public class DefaultResultSetRepository extends GenericJpaRepository<Long, EncryptedResultSet>
    implements ResultSetRepository
{
    @Inject Marshaller marshaller;

    @Override
    public long getNumResults(IAT test) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<EncryptedResultSet> root = query.from(EncryptedResultSet.class);
        Predicate pred = cb.equal(root.get("test"), test);
        return this.entityManager.createQuery(query.select(cb.count(root)).where(pred)).getSingleResult();
    }
    
    @Override
    public List<EncryptedResultSet> getResults(IAT test) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<EncryptedResultSet> query = cb.createQuery(EncryptedResultSet.class);
        Root<EncryptedResultSet> root = query.from(EncryptedResultSet.class);
        Predicate pred = cb.equal(root.get("test"), test);
        return this.entityManager.createQuery(query.where(pred)).getResultList();
    }
    
    @Override
    public void deleteResults(IAT test) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<EncryptedResultSet> deleteStatement = cb.createCriteriaDelete(EncryptedResultSet.class);
        Root<EncryptedResultSet> root = deleteStatement.from(EncryptedResultSet.class);
        Predicate pred = cb.equal(root.get("test"), test);
        this.entityManager.createQuery(deleteStatement.where(pred)).executeUpdate();
    }
    
    @Override
    public void saveResultSet(IAT test, ResultSet results) {
        var resultSet = new EncryptedResultSet(marshaller, test, results);
        resultSet.encryptResults();
        add(resultSet);
    }
    

    @Override
    public Calendar getLastIATAdminDate(List<IAT> tests) {
        try {
            if (tests.isEmpty())
                return null;
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Calendar> query = cb.createQuery(Calendar.class);
            Root<EncryptedResultSet> root = query.from(EncryptedResultSet.class);
            Predicate pred = root.get("test").in(tests);
            return this.entityManager.createQuery(query.select(cb.greatest(root.get("adminTime").as(Calendar.class))).where(pred)).getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }
}
