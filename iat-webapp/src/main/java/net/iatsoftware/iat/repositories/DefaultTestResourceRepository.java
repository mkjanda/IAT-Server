package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.generated.ResourceType;

import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;
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
			throws jakarta.persistence.NoResultException, jakarta.persistence.NonUniqueResultException {
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
		return this.entityManager.createQuery(query.where(pred).orderBy(cb.asc(root.get("resourceId"))))
				.getResultList();
	}

	public void add(TestResource res) {
		if (res.getResourceId() != null) {
			if (res.getResourceId() != 0) 
				throw new jakarta.persistence.PersistenceException("Non-null test resource added");
			else {
				super.add(res);
				return;
			}
		}
		var cb = this.entityManager.getCriteriaBuilder();
		var query = cb.createQuery(Integer.class);
		var root = query.from(TestResource.class);

		synchronized (this) {
			var resourceIds = this.entityManager.createQuery(query.select(root.get("resourceId"))
					.where(cb.equal(root.get("test"), res.getTest())).orderBy(cb.asc(root.get("resourceId"))))
					.getResultList();

			res.setResourceId(resourceIds.stream().reduce(0, (a, b) -> (a < b) ? a : a + 1));
			super.add(res);
		}
	}

	public TestResource getTestImage(IAT test, int index) {
		var cb = entityManager.getCriteriaBuilder();
		var query = cb.createQuery(TestResource.class);
		var root = query.from(TestResource.class);
		var pred = cb.and(cb.equal(root.get("test"), test), cb.equal(root.get("resourceId"), index));
		return this.entityManager.createQuery(query.select(root).where(pred).orderBy(cb.asc(root.get("resourceId"))))
				.getSingleResult();
//		return resourceList.stream().filter(f -> (f.getResourceType() == ResourceType.IMAGE) || (f.getResourceType() == ResourceType.ERROR_MARK)).collect(Collectors.toList()).get(index - 1);
	}
}