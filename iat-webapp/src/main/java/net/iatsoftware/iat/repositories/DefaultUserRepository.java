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
import net.iatsoftware.iat.entities.User;
import net.iatsoftware.iat.entities.Client;

import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

import java.util.List;

@Repository
public class DefaultUserRepository extends GenericJpaRepository<Long, User>
        implements UserRepository {

    @Override
    public User getFirstUserWithEmail(String email) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate pred = cb.equal(root.get("EMail"), email.toLowerCase());
        List<User> results = this.entityManager.createQuery(query.where(pred).orderBy(cb.asc(root.get("activationDate")))).getResultList();
        if (results.isEmpty()) {
            return null;
        }
        if (results.stream().map(User::getClient).map(Client::getClientId).distinct().count() > 1) {
            throw new javax.persistence.NonUniqueResultException();
        }
        return results.get(0);
    }
   
    @Override
    public User getUserByClientAndActivationKey(Client c, String key) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate pred = cb.and(cb.equal(root.get("client"), c), cb.equal(root.get("activationKey"), key));
        return this.entityManager.createQuery(query.where(pred)).getSingleResult();
    }
    
    @Override
    public User getUserByVerificationKey(String key) throws javax.persistence.NoResultException {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate pred = cb.equal(root.get("verificationKey"), key);
        return this.entityManager.createQuery(query.where(pred)).getSingleResult();
    }

    @Override
    public User getUserByClientAndEmail(Client c, String email) throws javax.persistence.NoResultException {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate pred = cb.and(cb.equal(root.get("client"), c), cb.equal(root.get("EMail"), email));
        return this.entityManager.createQuery(query.where(pred)).getSingleResult();
    }
}
