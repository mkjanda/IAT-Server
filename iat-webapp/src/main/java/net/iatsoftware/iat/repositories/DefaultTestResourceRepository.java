package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.TestResource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DefaultTestResourceRepository extends GenericJpaRepository<Long, TestResource> implements TestResourceRepository{
    private static final Logger critical = LogManager.getLogger("critical");

    public TestResource get(Long testId, String name) {
        var cb = entityManager.getCriteriaBuilder();
        var testQuery = cb.createQuery(IAT.class);
        var root = testQuery.from(IAT.class);
        var pred = cb.equal(root.get("TestID"), testId);
        var test = this.entityManager.createQuery(testQuery.select(root).where(pred)).getSingleResult();
        var resourceQuery = cb.createQuery(TestResource.class);
        var resourceRoot = resourceQuery.from(TestResource.class);
        pred = cb.and(cb.equal(root.get("test"), test), cb.equal(root.get("name"),name));
        try {
        return this.entityManager.createQuery(resourceQuery.select(resourceRoot).where(pred)).getSingleResult();
        }
        catch (javax.persistence.NoResultException ex) {
            critical.error("Test resource (TestID: " + Long.toString(testId) + ", ResourceName: " + name + ") not found", ex);
            return null;
        }
    }

}
