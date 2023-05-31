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

import net.iatsoftware.iat.entities.RSAKeyData;

import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Random;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class DefaultRSADataRepository extends GenericJpaRepository<Long, RSAKeyData> implements RSADataRepository {
    private static final int NUM_KEYS_STORED = 64;
    private static final Random random = new Random();

    @Override
    public RSAKeyData getRandomRSA() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<RSAKeyData> root = query.from(RSAKeyData.class);
        long numKeys = this.entityManager.createQuery(query.select(cb.count(root))).getSingleResult();
        if (numKeys == 0) {
            RSAKeyData key = RSAKeyData.generateKey();
            add(key);
            return key;
        } else {
            int nDataSet = random.nextInt((int) numKeys);
            CriteriaQuery<RSAKeyData> rsaQuery = cb.createQuery(RSAKeyData.class);
            root = rsaQuery.from(RSAKeyData.class);
            var rsas = this.entityManager
                    .createQuery(rsaQuery.select(root).orderBy(cb.asc(root.get("id")))).getResultList();
            return rsas.get(nDataSet);
        }
    }

    @Override
    public void clean() {
        Calendar expiration = Calendar.getInstance();
        expiration.setTimeInMillis(expiration.getTimeInMillis() - 2_419_200_000L);
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<RSAKeyData> del = cb.createCriteriaDelete(RSAKeyData.class);
        Root<RSAKeyData> delRoot = del.from(RSAKeyData.class);
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<RSAKeyData> countRoot = countQuery.from(RSAKeyData.class);
        this.entityManager.createQuery(del.where(cb.lessThan(delRoot.get("generationTimestamp"), expiration)))
                .executeUpdate();
        long numKeys = this.entityManager.createQuery(countQuery.select(cb.count(countRoot))).getSingleResult();
        for (int ctr = 0; ctr < NUM_KEYS_STORED - numKeys; ctr++)
            add(RSAKeyData.generateKey());
    }
}
