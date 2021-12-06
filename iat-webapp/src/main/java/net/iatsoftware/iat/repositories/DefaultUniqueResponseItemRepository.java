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

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.UniqueResponseItem;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

@Repository
public class DefaultUniqueResponseItemRepository extends GenericJpaRepository<Long, UniqueResponseItem> 
    implements UniqueResponseItemRepository
{
    public UniqueResponseItem getByTestAndSurveyName(IAT test, String surveyName) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<UniqueResponseItem> query = cb.createQuery(UniqueResponseItem.class);
        Root<UniqueResponseItem> root = query.from(UniqueResponseItem.class);
        Predicate pred1 = cb.equal(root.get("test"), test);
        Predicate pred2 = cb.equal(root.get("surveyName"), surveyName);
        query.where(pred1, pred2);
        return this.entityManager.createQuery(query).getSingleResult();
    }
}
