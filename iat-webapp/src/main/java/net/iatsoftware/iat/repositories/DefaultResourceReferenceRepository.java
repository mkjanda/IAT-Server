package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.ResourceReference;

import org.springframework.stereotype.Repository;

@Repository
public class DefaultResourceReferenceRepository extends GenericJpaRepository<Long, ResourceReference> 
    implements ResourceReferenceRepository {
    
}
