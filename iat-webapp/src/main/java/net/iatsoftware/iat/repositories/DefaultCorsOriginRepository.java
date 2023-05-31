/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.repositories;

/**
 *
 * @author michael
 */

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.CorsOrigin;

import org.springframework.stereotype.Repository;

import java.util.List;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaQuery;

@Repository
public class DefaultCorsOriginRepository extends GenericJpaRepository<Long, CorsOrigin> implements CorsOriginRepository {
    
    @Override
    public List<Client> getClientsWithCors() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = cb.createQuery(Client.class);
        Root<CorsOrigin> root = query.from(CorsOrigin.class);
        return this.entityManager.createQuery(query.distinct(true).select(root.get("client"))).getResultList();
    }
    
    @Override
    public List<CorsOrigin> getCorsOriginsForClient(Client c) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<CorsOrigin> query = cb.createQuery(CorsOrigin.class);
        Root<CorsOrigin> root = query.from(CorsOrigin.class);
        Predicate pred = cb.equal(root.get("client"), c);
        return this.entityManager.createQuery(query.where(pred)).getResultList();
    }
    
    @Override
    public long getNumClientOrigins(Client c) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<CorsOrigin> root = query.from(CorsOrigin.class);
        Predicate pred = cb.equal(root.get("client"), c);
        return this.entityManager.createQuery(query.select(cb.count(pred))).getSingleResult();
    }
}
