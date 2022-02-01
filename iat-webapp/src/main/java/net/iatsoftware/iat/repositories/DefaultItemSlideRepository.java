/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.repositories;

/**
 *
 * @author Michael Janda
 */
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.ItemSlide;
import net.iatsoftware.iat.generated.DeploymentFileType;
import net.iatsoftware.iat.generated.ManifestFileType;
import net.iatsoftware.iat.messaging.Manifest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.Tuple;

@Repository
public class DefaultItemSlideRepository extends GenericJpaRepository<Long, ItemSlide> implements ItemSlideRepository {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<ItemSlide> getSlidesByTest(IAT test) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<ItemSlide> query = cb.createQuery(ItemSlide.class);
            Root<ItemSlide> root = query.from(ItemSlide.class);
            Predicate pred = cb.equal(root.get("test"), test);
            return this.entityManager.createQuery(query.where(pred).orderBy(cb.asc(root.get("id")))).getResultList();
        } catch (Exception ex) {
            logger.error("Error retrieving item slides", ex);
            return null;
        }
    }
    
    @Override
    public Manifest getItemSlideManifest(IAT test) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class);
            Root<ItemSlide> root = query.from(ItemSlide.class);
            Predicate pred = cb.and(cb.equal(root.get("test"), test), cb.equal(root.get("fileType", ManifestFileType)));
            List<Tuple> slideInfo = this.entityManager.createQuery(query.where(pred).orderBy(cb.asc(root.get("slideNum"))).multiselect(root.get("fileName"), root.get("slideSize"), root.get(""))).getResultList();
            Manifest m = new Manifest();
            m.setIATName(test.getTestName());
            for (Tuple t : slideInfo) {
                m.addFile(t.get(0, String.class), t.get(1, Long.class,  t.get(1, ManifestFileType.class)));
            }
            return m;
        }
        catch (Exception ex) {
            logger.error("Error retrieving list of slide filenames", ex);
            return null;
        }
    }
}
