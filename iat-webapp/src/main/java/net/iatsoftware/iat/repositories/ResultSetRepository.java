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

import net.iatsoftware.iat.entities.ResultSet;
import net.iatsoftware.iat.entities.IAT;

import java.util.Calendar;
import java.util.List;

public interface ResultSetRepository extends GenericRepository<Long, ResultSet> {
    long getNumResults(IAT test);
    List<ResultSet> getResults(IAT test);
    void deleteResults(IAT test);
    void reassociateResults(IAT newTest, IAT oldTest);
    Calendar getLastIATAdminDate(List<IAT> iats);
    ResultSet getResultSetWithToken(IAT test, byte[] token);
}
