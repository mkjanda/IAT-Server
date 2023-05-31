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

import net.iatsoftware.iat.entities.UniqueResponse;
import net.iatsoftware.iat.entities.UniqueResponseItem;

import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository     
public class DefaultUniqueResponseRepository extends GenericJpaRepository<Long, UniqueResponse> implements UniqueResponseRepository {
    
    @Override
    public UniqueResponse getUniqueResponse(UniqueResponseItem uri, String val) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<UniqueResponse> query = cb.createQuery(UniqueResponse.class);
        Root<UniqueResponse> root = query.from(UniqueResponse.class);
        Predicate pred = cb.and(cb.equal(root.get("item"), uri), cb.equal(root.get("value"), val));
        try {
            return this.entityManager.createQuery(query.select(root).where(pred)).getSingleResult();
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    @Override
    public void free(UniqueResponse ur) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<UniqueResponse> update = cb.createCriteriaUpdate(UniqueResponse.class);
        Root<UniqueResponse> root = update.from(UniqueResponse.class);
        update.set("adminTimer", null);
        update.set("taken", false);
        Predicate pred = cb.equal(root, ur);
        this.entityManager.createQuery(update.where(pred)).executeUpdate();
    }
}
