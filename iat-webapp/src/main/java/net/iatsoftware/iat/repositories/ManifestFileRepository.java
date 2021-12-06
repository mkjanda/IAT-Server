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

import java.util.List;

public interface ManifestFileRepository extends GenericRepository<Long, ManifestFile> {
    List<ManifestFile> getDeploymentManifest(DeploymentSession ds, FileManifestType fileType);
    void deleteManifestFiles(DeploymentSession ds, FileManifestType fileType);
}
