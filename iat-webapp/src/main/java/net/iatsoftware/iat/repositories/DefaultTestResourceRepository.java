package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.generated.ManifestEntityType;
import net.iatsoftware.iat.generated.ResourceType;
import net.iatsoftware.iat.messaging.File;
import net.iatsoftware.iat.messaging.Manifest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

@Repository
public class DefaultTestResourceRepository extends GenericJpaRepository<Long, TestResource>
		implements TestResourceRepository {
	private static final Logger critical = LogManager.getLogger("critical");

	@Inject
	ResourceReferenceRepository resourceReferenceRepository;

	public TestResource get(Long testId, String name) {
		var cb = entityManager.getCriteriaBuilder();
		var testQuery = cb.createQuery(IAT.class);
		var root = testQuery.from(IAT.class);
		var pred = cb.equal(root.get("TestID"), testId);
		var test = this.entityManager.createQuery(testQuery.select(root).where(pred)).getSingleResult();
		var resourceQuery = cb.createQuery(TestResource.class);
		var resourceRoot = resourceQuery.from(TestResource.class);
		pred = cb.and(cb.equal(root.get("test"), test), cb.equal(root.get("name"), name));
		try {
			return this.entityManager.createQuery(resourceQuery.select(resourceRoot).where(pred)).getSingleResult();
		} catch (javax.persistence.NoResultException ex) {
			critical.error(
					"Test resource (TestID: " + Long.toString(testId) + ", ResourceName: " + name + ") not found", ex);
			return null;
		}
	}

	public Manifest getTestManifest(IAT test) {
		var manifest = new Manifest();
		var cb = this.entityManager.getCriteriaBuilder();
		var query = cb.createQuery(TestResource.class);
		var root = query.from(TestResource.class);
		var resources = this.entityManager.createQuery(query.select(root).where(cb.equal(root.get("test"), test)))
				.getResultList();
		for (var res : resources) {
			var file = new File();
			file.setName(res.getName());
			file.setPath(res.getPath());
			file.setMimeType(res.getMimeType());
			file.setSize(res.getSize());
			file.setEntityType(ManifestEntityType.FILE);
			file.setResourceId(res.getId());
			file.getReferenceId().addAll(res.getReferences().stream().map(ref -> ref.getId()).collect(Collectors.toList()));
		}
		return manifest;
	}

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
		var pred = cb.and(cb.equal(root.get("test"), test), cb.equal(root.get("resourceId"), resourceId));
		return this.entityManager.createQuery(query.select(root).where(pred)).getSingleResult();
	}
}
