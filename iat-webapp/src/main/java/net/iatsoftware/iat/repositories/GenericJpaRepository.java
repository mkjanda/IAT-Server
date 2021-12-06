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

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaDelete;
import java.io.Serializable;

public abstract class GenericJpaRepository<I extends Serializable, E extends Serializable> extends GenericBaseRepository<I, E> {
    @PersistenceContext EntityManager entityManager;
    
    @Override
    @Transactional
    public Iterable<E> getAll()
    {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<E> q = cb.createQuery(this.entityClass);
        return entityManager.createQuery(q.select(q.from(this.entityClass))).getResultList();
    }
    
    @Override
    @Transactional
    (
        propagation = Propagation.REQUIRED, 
        readOnly = false,
        rollbackFor = Throwable.class
    )
    public E get(I id)
    {
        return this.entityManager.find(this.entityClass, id);
    }
    
    @Override
    @Transactional
    (
        propagation = Propagation.REQUIRED, 
        readOnly = false,
        rollbackFor = Throwable.class
    )
    public void add(E e)
    {
        this.entityManager.persist(e);
    }
    
    @Override
    @Transactional
    public E update(E entity)
    {
        return this.entityManager.merge(entity);
    }
    
    @Override
    @Transactional
    public void delete(E entity)
    {
        this.entityManager.remove(entity);
    }
    
    @Override
    public void deleteById(I id)
    {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<E> query = cb.createCriteriaDelete(this.entityClass);
        this.entityManager.createQuery(query.where(cb.equal(query.from(this.entityClass).get("id"), id))).executeUpdate();
    }
}
