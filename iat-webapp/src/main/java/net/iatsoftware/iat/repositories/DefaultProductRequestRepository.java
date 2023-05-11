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

import net.iatsoftware.iat.entities.ProductRequestEntity;

import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class DefaultProductRequestRepository extends GenericJpaRepository<Long, ProductRequestEntity> implements ProductRequestRepository {
    
    @Override
    public Iterable<ProductRequestEntity> getRequestsById(List<Long> ids) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductRequestEntity> query = cb.createQuery(ProductRequestEntity.class);
        Root<ProductRequestEntity> root = query.from(ProductRequestEntity.class);
        Predicate pred = cb.in(root.get("id")).in(ids);
        return this.entityManager.createQuery(query.select(root).where(pred)).getResultList();
    }
    
}
