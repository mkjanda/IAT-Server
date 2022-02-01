package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.ResourceReference;

import java.util.List;

interface ResourceReferenceRepository extends GenericRepository<Long, ResourceReference> {
    List<ResourceReference>  getResourceReferences(IAT test);
}
