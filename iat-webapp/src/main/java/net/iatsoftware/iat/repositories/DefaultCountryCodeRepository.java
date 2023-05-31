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

import net.iatsoftware.iat.entities.CountryCode;

import org.springframework.stereotype.Repository;

import java.util.List;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class DefaultCountryCodeRepository extends GenericJpaRepository<Long, CountryCode> implements CountryCodeRepository {
    
    @Override
    public List<CountryCode> getCountryCodes() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<CountryCode> query = cb.createQuery(CountryCode.class);
        Root<CountryCode> root = query.from(CountryCode.class);
        return this.entityManager.createQuery(query.select(root).orderBy(cb.asc(root.get("country")))).getResultList();
    }
    
}
