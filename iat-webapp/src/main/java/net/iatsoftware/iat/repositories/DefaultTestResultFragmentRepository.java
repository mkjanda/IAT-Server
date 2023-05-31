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

import net.iatsoftware.iat.entities.AdminTimer;
import net.iatsoftware.iat.entities.TestResultFragment;
import net.iatsoftware.iat.entities.ResultSet;

import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;

import java.util.List;

@Repository
public class DefaultTestResultFragmentRepository extends GenericJpaRepository<Long, TestResultFragment>
        implements TestResultFragmentRepository
{
    @Override
    public List<TestResultFragment> getResultFragments(ResultSet rs) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<TestResultFragment> query = cb.createQuery(TestResultFragment.class);
        Root<TestResultFragment> root = query.from(TestResultFragment.class);
        Predicate pred = cb.equal(root.get("resultSet"), rs);
        query.where(pred);
        query.orderBy(cb.asc(root.get("ordinal")));
        return this.entityManager.createQuery(query).getResultList();
    }
    
    @Override
    public void deleteResultFragments(ResultSet rs) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<TestResultFragment> deletion = cb.createCriteriaDelete(TestResultFragment.class);
        Root<TestResultFragment> root = deletion.from(TestResultFragment.class);
        Predicate pred = cb.equal(root.get("resultSet"), rs);
        deletion.where(pred);
        this.entityManager.createQuery(deletion).executeUpdate();
    }
    
    @Override
    public List<TestResultFragment> get(AdminTimer timer) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<TestResultFragment> query = cb.createQuery(TestResultFragment.class);
        Root<TestResultFragment> root = query.from(TestResultFragment.class);
        Predicate pred = cb.equal(root.get("adminTimer"), timer);
        return this.entityManager.createQuery(query.where(pred).orderBy(cb.asc(root.get("ordinal").as(Integer.class)))).getResultList();
    }
    
    @Override
    public void setComplete(AdminTimer timer) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<TestResultFragment> update = cb.createCriteriaUpdate(TestResultFragment.class);
        Root<TestResultFragment> root = update.from(TestResultFragment.class);
        Predicate pred = cb.equal(root.get("adminTimer"), timer);
        this.entityManager.createQuery(update.set(root.get("complete"), true).where(pred)).executeUpdate();
    }
}
