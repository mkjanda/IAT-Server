package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.TestResource;

import java.util.List;

public interface TestResourceRepository extends GenericRepository<Long, TestResource> {
    TestResource get(Long testId, String resourceName);
    List<TestResource> getLike(IAT test, String patt);
}
