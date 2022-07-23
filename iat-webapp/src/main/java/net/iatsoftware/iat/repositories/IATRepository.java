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

import java.util.Calendar;
import java.util.List;

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.IAT;

public interface IATRepository extends GenericRepository<Long, IAT> {
    boolean containsByProductKey(String productKey, String testName);
    void setDescriptor(Long testID, byte[] descrioptor);
    boolean iatExists(Client c, String iatName);
    long getNumIATs(Client client);
    Long getConsumedDiskSpaceKB(Client client);
    int getResultDataFormat(IAT test);
    void creditAdministration(IAT test);
    void debitAdministration(IAT test);
    List<IAT> getIATsByClient(Client c);
    Long getDiskUsageByClient(Client c);
    Calendar getLastIATUploadDate(Client c);
    Calendar getLastDataRetrievalDate(Client c);
    List<IAT> getExpiredTestResults(long timeout);
    boolean clientIdMatchesClientSecret(String id, String secret);
    IAT get(String testName, Long clientId);
}
