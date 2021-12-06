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
import net.iatsoftware.iat.entities.ResourcePrice;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class DefaultResourcePriceRepository extends GenericJpaRepository<Long, ResourcePrice> implements ResourcePriceRepository {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<ResourcePrice> getResourcePrices() {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<ResourcePrice> query = cb.createQuery(ResourcePrice.class);
            Root<ResourcePrice> root = query.from(ResourcePrice.class);
            return this.entityManager.createQuery(query.select(root)).getResultList();
        } catch (Exception ex) {
            logger.error("Error retrieving resource prices", ex);
            return null;
        }

    }
}
