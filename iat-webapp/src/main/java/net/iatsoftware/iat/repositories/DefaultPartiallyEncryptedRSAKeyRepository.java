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
import net.iatsoftware.iat.entities.EncryptedRSAKey;
import net.iatsoftware.iat.entities.IAT;

import org.springframework.stereotype.Repository;

import java.util.List;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;

@Repository
public class DefaultPartiallyEncryptedRSAKeyRepository extends GenericJpaRepository<Long, EncryptedRSAKey>
        implements PartiallyEncryptedRSAKeyRepository {

    @Override
    public EncryptedRSAKey getDataKey(IAT test) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<EncryptedRSAKey> query = cb.createQuery(EncryptedRSAKey.class);
            Root<EncryptedRSAKey> root = query.from(EncryptedRSAKey.class);
            Predicate pred = cb.equal(root.get("test"), test);
            return this.entityManager.createQuery(query.where(pred)).getSingleResult();
        } catch (jakarta.persistence.NoResultException ex) {
            return null;
        }
    }


    @Override
    public void copyRSAKeys(IAT newTest, IAT oldTest) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<EncryptedRSAKey> query = cb.createQuery(EncryptedRSAKey.class);
        Root<EncryptedRSAKey> root = query.from(EncryptedRSAKey.class);
        Predicate pred = cb.equal(root.get("test"), oldTest);
        List<EncryptedRSAKey> keys = this.entityManager.createQuery(query.where(pred)).getResultList();
        for (EncryptedRSAKey key : keys) {
            EncryptedRSAKey copy = new EncryptedRSAKey();
            copy.setEncryptedKeyBytes(key.getEncryptedKeyBytes());
            copy.setModulusBytes(key.getModulusBytes());
            copy.setExponentBytes(key.getExponentBytes());
            copy.setTest(newTest);
            this.add(copy);
        };
    }
}
