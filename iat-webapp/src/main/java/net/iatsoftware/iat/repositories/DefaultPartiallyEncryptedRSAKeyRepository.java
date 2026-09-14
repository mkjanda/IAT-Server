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
import net.iatsoftware.iat.entities.Crypt;
import net.iatsoftware.iat.entities.IAT;

import org.springframework.stereotype.Repository;

import java.util.List;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;

@Repository
public class DefaultPartiallyEncryptedRSAKeyRepository extends GenericJpaRepository<Long, Crypt>
        implements PartiallyEncryptedRSAKeyRepository {

    @Override
    public Crypt getDataKey(IAT test) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Crypt> query = cb.createQuery(Crypt.class);
            Root<Crypt> root = query.from(Crypt.class);
            Predicate pred = cb.equal(root.get("test"), test);
            return this.entityManager.createQuery(query.where(pred)).getSingleResult();
        } catch (jakarta.persistence.NoResultException ex) {
            return null;
        }
    }


    @Override
    public void copyRSAKeys(IAT newTest, IAT oldTest) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Crypt> query = cb.createQuery(Crypt.class);
        Root<Crypt> root = query.from(Crypt.class);
        Predicate pred = cb.equal(root.get("test"), oldTest);
        List<Crypt> keys = this.entityManager.createQuery(query.where(pred)).getResultList();
        for (Crypt key : keys) {
            Crypt copy = new Crypt();
            copy.setRSAParamsBytes(key.getRSAParamsBytes());
            copy.setModulusBytes(key.getModulusBytes());
            copy.setExponentBytes(key.getExponentBytes());
            copy.setTest(newTest);
            this.add(copy);
        };
    }
}
