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

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.Purchase;

import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class DefaultPurchaseRepository extends GenericJpaRepository<Long, Purchase> implements PurchaseRepository {
    
    @Override
    public List<Purchase> getPurchasesByClient(Client c) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> query = cb.createQuery(Purchase.class);
        Root<Purchase> root = query.from(Purchase.class);
        Predicate pred = cb.equal(root.get("client"), c);
        return this.entityManager.createQuery(query.select(root).where(pred).orderBy(cb.asc(root.get("purchaseTime")))).getResultList();
    }
}
