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

import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.ManifestFile;
import net.iatsoftware.iat.generated.FileManifestType;

import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class DefaultManifestFileRepository extends GenericJpaRepository<Long, ManifestFile> implements ManifestFileRepository {
    public List<ManifestFile> getDeploymentManifest(DeploymentSession ds, FileManifestType fileType) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<ManifestFile> query = cb.createQuery(ManifestFile.class);
        Root<ManifestFile> root = query.from(ManifestFile.class);
        Predicate pred = cb.and(cb.equal(root.get("deploymentSession"), ds), cb.equal(root.get("fileType"), fileType));
        return this.entityManager.createQuery(query.where(pred).orderBy(cb.asc(root.get("transmissionOrder")))).getResultList();
    }

    public void deleteManifestFiles(DeploymentSession ds, FileManifestType fileType) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<ManifestFile> ce = cb.createCriteriaDelete(ManifestFile.class);
        var root = ce.from(ManifestFile.class);
        var pred = cb.and(cb.equal(root.get("deploymentSession"), ds), cb.equal(root.get("fileType"), fileType));
        this.entityManager.createQuery(ce.where(pred)).executeUpdate();
    }
}
