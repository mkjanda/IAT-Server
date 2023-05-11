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
import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;

public interface PartiallyEncryptedRSAKeyRepository extends GenericRepository<Long, PartiallyEncryptedRSAKey> {
    PartiallyEncryptedRSAKey getDataKey(IAT test);
    PartiallyEncryptedRSAKey getAdminKey(IAT test);
    void copyRSAKeys(IAT newTest, IAT oldTest);
}
