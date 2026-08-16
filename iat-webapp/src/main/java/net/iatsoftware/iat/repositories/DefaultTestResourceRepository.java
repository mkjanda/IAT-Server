package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.generated.ResourceType;
import net.iatsoftware.iat.messaging.Manifest;

import org.springframework.stereotype.Repository;

import java.util.List;
import jakarta.inject.Inject;

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
	}

	public List<byte[]> getItemSlides(IAT test) {
		var cb = entityManager.getCriteriaBuilder();
		var query = cb.createQuery(TestResource.class);
		var root = query.from(TestResource.class);
		var pred = cb.and(cb.equal(root.get("test"), test), cb.equal(root.get("resourceType"), ResourceType.ITEM_SLIDE));
		query = query.select(root).where(pred).orderBy(cb.asc(root.get("id")));
		return this.entityManager.createQuery(query).getResultStream().filter(tr -> tr.getResourceType().equals(ResourceType.ITEM_SLIDE)).map(tr -> tr.getResourceBytes()).toList();
	}

	public Manifest getItemSlideManifest(IAT test) {
		var cb = entityManager.getCriteriaBuilder();
		var query = cb.createQuery(Long.class);
		var root = query.from(TestResource.class);
		var pred = cb.and(cb.equal(root.get("test"), test),
			cb.equal(root.get("resourceType"), ResourceType.ITEM_SLIDE));
		query = query.select(root.get("size")).where(pred);
		query = query.orderBy(cb.asc(root.get("id")));
		var sizes = this.entityManager.createQuery(query).getResultList();
		var manifest = new Manifest();
		for (var i = 0; i < sizes.size(); i++) {
			var f = new net.iatsoftware.iat.messaging.File();
			f.setName("ItemSlide" + (i + 1));
			f.setSize(sizes.get(i).intValue());
			f.setResourceType(ResourceType.ITEM_SLIDE);
			manifest.getFiles().add(f);
		}
		return manifest;
	}
}