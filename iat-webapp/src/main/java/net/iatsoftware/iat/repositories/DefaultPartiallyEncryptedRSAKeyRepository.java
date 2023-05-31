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
import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.generated.KeyType;

import org.springframework.stereotype.Repository;

import java.util.List;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;

@Repository
public class DefaultPartiallyEncryptedRSAKeyRepository extends GenericJpaRepository<Long, PartiallyEncryptedRSAKey>
        implements PartiallyEncryptedRSAKeyRepository {

    @Override
    public PartiallyEncryptedRSAKey getDataKey(IAT test) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<PartiallyEncryptedRSAKey> query = cb.createQuery(PartiallyEncryptedRSAKey.class);
            Root<PartiallyEncryptedRSAKey> root = query.from(PartiallyEncryptedRSAKey.class);
            Predicate pred1 = cb.equal(root.get("test"), test);
            Predicate pred2 = cb.equal(root.get("entityKeyType"), KeyType.DATA);
            return this.entityManager.createQuery(query.where(pred1, pred2)).getSingleResult();
        } catch (jakarta.persistence.NoResultException ex) {
            return null;
        }
    }

    @Override
    public PartiallyEncryptedRSAKey getAdminKey(IAT test) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<PartiallyEncryptedRSAKey> query = cb.createQuery(PartiallyEncryptedRSAKey.class);
            Root<PartiallyEncryptedRSAKey> root = query.from(PartiallyEncryptedRSAKey.class);
            Predicate pred1 = cb.equal(root.get("test"), test);
            Predicate pred2 = cb.equal(root.get("entityKeyType"), KeyType.ADMIN);
            return this.entityManager.createQuery(query.where(pred1, pred2)).getSingleResult();
        } catch (jakarta.persistence.NoResultException ex) {
            return null;
        }
    }

    @Override
    public void copyRSAKeys(IAT newTest, IAT oldTest) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<PartiallyEncryptedRSAKey> query = cb.createQuery(PartiallyEncryptedRSAKey.class);
        Root<PartiallyEncryptedRSAKey> root = query.from(PartiallyEncryptedRSAKey.class);
        Predicate pred = cb.equal(root.get("test"), oldTest);
        List<PartiallyEncryptedRSAKey> keys = this.entityManager.createQuery(query.where(pred)).getResultList();
        for (PartiallyEncryptedRSAKey key : keys) {
            PartiallyEncryptedRSAKey copy = new PartiallyEncryptedRSAKey();
            copy.setEncryptedKeyBytes(key.getEncryptedKeyBytes());
            copy.setModulusBytes(key.getModulusBytes());
            copy.setExponentBytes(key.getExponentBytes());
            copy.setTest(newTest);
            this.add(copy);
        };
    }
}
