package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.generated.ResourceType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.inject.Inject;

@Repository
public class DefaultTestResourceRepository extends GenericJpaRepository<Long, TestResource>
		implements TestResourceRepository {
	private static final Logger critical = LogManager.getLogger("critical");

	@Inject
	ResourceReferenceRepository resourceReferenceRepository;

	public List<TestResource> getFromTest(IAT test, ResourceType type) {
		var cb = this.entityManager.getCriteriaBuilder();
		var query = cb.createQuery(TestResource.class);
		var root = query.from(TestResource.class);
		var pred = cb.and(cb.equal(root.get("test"), test), cb.equal(root.get("resourceType"), type));
		return this.entityManager.createQuery(query.select(root).where(pred)).getResultList();
	}

	public TestResource get(IAT test, Long resourceId)
			throws javax.persistence.NoResultException, javax.persistence.NonUniqueResultException {
		var cb = this.entityManager.getCriteriaBuilder();
		var query = cb.createQuery(TestResource.class);
		var root = query.from(TestResource.class);
		var pred = cb.and(cb.equal(root.get("test"), test), cb.equal(root.get("resourceId"), resourceId),
			cb.notEqual(root.get("resourceType"), ResourceType.ITEM_SLIDE));
		return this.entityManager.createQuery(query.select(root).where(pred)).getSingleResult();
	}

	public List<TestResource> getDeploymentResources(IAT test) {
		var cb = this.entityManager.getCriteriaBuilder();
		var query = cb.createQuery(TestResource.class);
		var root = query.from(TestResource.class);
		var pred = cb.equal(root.get("test"), test);
		return this.entityManager.createQuery(query.where(pred).orderBy(cb.asc(root.get("resourceId")))).getResultList();

	}
}
