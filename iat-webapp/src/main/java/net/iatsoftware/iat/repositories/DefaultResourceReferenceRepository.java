package net.iatsoftware.iat.repositories;


import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.ResourceReference;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class DefaultResourceReferenceRepository extends GenericJpaRepository<Long, ResourceReference> 
    implements ResourceReferenceRepository {
    
        public List<ResourceReference> getResourceReferences(IAT test) {
            var cb = entityManager.getCriteriaBuilder();
            var query = cb.createQuery(ResourceReference.class);
            var root = query.from(ResourceReference.class);
            return entityManager.createQuery(query.where(cb.equal(root.get("test"), test))).getResultList();
        }
}
