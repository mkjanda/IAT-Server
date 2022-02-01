package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.messaging.Manifest;

public interface TestResourceRepository extends GenericRepository<Long, TestResource> {
    TestResource get(Long testId, String resourceName);
    Manifest  getDeploymentManifest(IAT test);
}
