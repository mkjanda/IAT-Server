package net.iatsoftware.iat.repositories;

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.JavaScript;

public class DefaultJavaScriptRepository extends GenericJpaRepository<Long, JavaScript>
        implements JavaScriptRepository {

    public String getScript(IAT test, int index) {
        try {
        var cb = this.entityManager.getCriteriaBuilder();
        var query = cb.createQuery(String.class);
        var root = query.from(JavaScript.class);
        var pred = cb.and(cb.equal(root.get("test"), test), cb.equal(root.get("indexInTest"), index));
        return this.entityManager.createQuery(query.select(root.get("script")).where(pred)).getSingleResult();
        }
        catch (javax.persistence.NoResultException | javax.persistence.NonUniqueResultException ex) {
            return "";
        }
    }
}
