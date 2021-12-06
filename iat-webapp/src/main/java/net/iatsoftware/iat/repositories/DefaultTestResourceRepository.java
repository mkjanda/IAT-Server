package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.TestResource;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DefaultTestResourceRepository extends GenericJpaRepository<Long, TestResource> implements TestResourceRepository{
    public TestResource get(Long testId, String resourceName) {
        var cb = entityManager.getCriteriaBuilder();
        var testQuery = cb.createQuery(IAT.class);
        var root = testQuery.from(IAT.class);
        var pred = cb.equal(root.get("TestID"), testId);
        var test = this.entityManager.createQuery(testQuery.select(root).where(pred)).getSingleResult();
        var resourceQuery = cb.createQuery(TestResource.class);
        var resourceRoot = resourceQuery.from(TestResource.class);
        pred = cb.and(cb.equal(root.get("test"), test), cb.equal(root.get("resourceName"), resourceName));
        return this.entityManager.createQuery(resourceQuery.select(resourceRoot).where(pred)).getSingleResult();
    }

    public List<TestResource> getLike(IAT test, String patt) {
        var cb = entityManager.getCriteriaBuilder();
        var testQuery = cb.createQuery(TestResource.class);
        var root = testQuery.from(TestResource.class);
        var pred = cb.and(cb.equal(root.get("test"), test), cb.like(root.get("resourceName"), patt));
        return this.entityManager.createQuery(testQuery.select(root).where(pred)).getResultList();
    }
}
