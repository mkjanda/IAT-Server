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

import net.iatsoftware.iat.entities.EncryptedResultSet;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.resultdata.ResultSet;

import java.util.Calendar;
import java.util.List;

public interface ResultSetRepository extends GenericRepository<Long, EncryptedResultSet> {
    long getNumResults(IAT test);
    List<EncryptedResultSet> getResults(IAT test);
    void deleteResults(IAT test);
    Calendar getLastIATAdminDate(List<IAT> iats);
    void saveResultSet(IAT test, ResultSet results);
}
