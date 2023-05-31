package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.generated.ResourceType;

import java.util.List;

public interface TestResourceRepository extends GenericRepository<Long, TestResource> {
    List<TestResource> getFromTest(IAT test, ResourceType type);
    TestResource get(IAT test, Long resourceId);
    List<TestResource> getDeploymentResources(IAT test);
    TestResource getTestImage(IAT test, int index);
}
