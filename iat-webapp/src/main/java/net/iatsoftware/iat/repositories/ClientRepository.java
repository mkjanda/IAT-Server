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

import net.iatsoftware.iat.dataservices.DownloadPassword;
import net.iatsoftware.iat.dataservices.ProductKey;
import net.iatsoftware.iat.entities.Client;

import java.util.List;

public interface ClientRepository extends GenericRepository<Long, Client> {
    boolean productKeyExists(String productKey);
    boolean downloadPasswordExists(String pass);
    void freezeByProductKey(String key);
    void deleteByProductKey(String key);
    Iterable<Client> getClientsByIds(List<Long> ids);
    List<Client> getClientsByName(String fname, String lname);
    List<Client> getClientsByFName(String fname);
    List<Client> getClientsByLName(String lname);
    List<Client> getClientsByOrganization(String organization);
    Client getByProductKey(String productKey);
    void creditAdministration(Client c);
    void debitAdministration(Client c);
    ProductKey getNewProductKey();
    DownloadPassword getNewDownloadPassword();
    Client getClientByDownloadPassword(String pass);
    void updateDownloadsRemaining(int nDownloads);
    Client getClientByEmail(String email);
    boolean clientWithEmailExists(String email);
}
