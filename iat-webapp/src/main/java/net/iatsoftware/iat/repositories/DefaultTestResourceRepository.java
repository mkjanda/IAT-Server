package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.generated.ResourceType;

import org.springframework.stereotype.Repository;

import java.util.List;
import javax.inject.Inject;

@Repository
public class DefaultTestResourceRepository extends GenericJpaRepository<Long, TestResource>
		implements TestResourceRepository {

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

	public void add(TestResource res) {
		if (res.getResourceId() != -1) {
			super.add(res);
			return;
		}
		var cb = this.entityManager.getCriteriaBuilder();
		var query = cb.createQuery(Integer.class);
		var root = query.from(TestResource.class);
		var resourceIds = this.entityManager.createQuery(query.select(root.get("resourceId"))
			.where(cb.equal(root.get("test"), res.getTest())).orderBy(cb.asc(root.get("resourceId"))))
			.getResultList();
		if (!resourceIds.contains(resourceIds.size() - 1)) {
			res.setResourceId(resourceIds.size() - 1);
		} else {
			res.setResourceId(resourceIds.stream().reduce(-1, (a, b) -> (a + 1 == b) ? b : a));
		}
		super.add(res);
	}
}
