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
import net.iatsoftware.iat.entities.SpecifierValue;

import org.springframework.stereotype.Repository;

import java.util.List;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class DefaultSpecifierValueRepository extends GenericJpaRepository<Long, SpecifierValue> implements SpecifierValueRepository {
    
    @Override
    public List<SpecifierValue> getByAdminID(AdminTimer admin) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<SpecifierValue> query = cb.createQuery(SpecifierValue.class);
        Root<SpecifierValue> root = query.from(SpecifierValue.class);
        Predicate pred = cb.equal(root.get("admin"), admin);
        return this.entityManager.createQuery(query.select(root).where(pred)).getResultList();
    }
}
