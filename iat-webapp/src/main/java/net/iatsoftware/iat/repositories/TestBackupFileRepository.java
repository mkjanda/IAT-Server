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
import net.iatsoftware.iat.entities.TestBackupFile;

public interface TestBackupFileRepository extends GenericRepository<Long, TestBackupFile> {
    void deleteTestBackup(IAT test);
    void restoreTestBackup(IAT test) throws java.net.URISyntaxException, java.io.IOException;
}
