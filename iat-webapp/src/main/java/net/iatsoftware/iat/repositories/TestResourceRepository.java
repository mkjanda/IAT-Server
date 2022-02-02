package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.generated.ResourceType;
import net.iatsoftware.iat.messaging.Manifest;

import java.util.List;

public interface TestResourceRepository extends GenericRepository<Long, TestResource> {
    TestResource get(Long testId, String resourceName);
    List<TestResource> getFromTest(IAT test, ResourceType type);
    TestResource get(IAT test, Long resourceId);
    Manifest  getTestManifest(IAT test);
}
