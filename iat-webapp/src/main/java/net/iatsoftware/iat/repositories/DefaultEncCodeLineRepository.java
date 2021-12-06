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


import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.iatsoftware.iat.entities.EncCodeLine;
import net.iatsoftware.iat.entities.TestSegment;
import net.iatsoftware.iat.generated.CodeType;

@Repository
public class DefaultEncCodeLineRepository extends GenericJpaRepository<Long, EncCodeLine>
    implements EncCodeLineRepository
{
    @Override
    public List<EncCodeLine> getEncryptedLines(TestSegment testSegment, CodeType codeType) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<EncCodeLine> query = cb.createQuery(EncCodeLine.class);
        Root<EncCodeLine> root = query.from(EncCodeLine.class);
        Predicate pred = cb.and(cb.equal(root.get("testSegment"), testSegment), cb.equal(root.get("type"), codeType));
        return this.entityManager.createQuery(query.where(pred).orderBy(cb.asc(root.get("ordinal")))).getResultList();
    }
    
    @Override
    public List<EncCodeLine> getOrderedCodeLines(TestSegment testSegment) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<EncCodeLine> query = cb.createQuery(EncCodeLine.class);
        Root<EncCodeLine> root = query.from(EncCodeLine.class);
        Predicate pred = cb.equal(root.get("testSegment"), testSegment);
        return this.entityManager.createQuery(query.where(pred).orderBy(cb.asc(root.get("ordinal")))).getResultList();
    }
}
