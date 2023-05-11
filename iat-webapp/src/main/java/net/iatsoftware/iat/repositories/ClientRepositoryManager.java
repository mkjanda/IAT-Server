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
import net.iatsoftware.iat.entities.CountryCode;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.ProductRequestEntity;
import net.iatsoftware.iat.entities.Purchase;
import net.iatsoftware.iat.entities.ResourcePrice;
import net.iatsoftware.iat.entities.User;

import java.util.Calendar;
import java.util.List;

public interface ClientRepositoryManager {
    void recordProductRequest(ProductRequestEntity pr);
    boolean productKeyExists(String productKey);
    boolean downloadPasswordExists(String pass);
    ProductRequestEntity getProductRequest(Long id);
    void addClient(Client c);
    void deleteProductRequest(ProductRequestEntity pRequest);
    void freezeClient(String productKey);
    void deleteClient(String productKey);
    Client getClient(String productKey);
    Iterable<Client> getAllClients();
    Iterable<Client> getClientsByIds(List<Long> ids);
    Iterable<ProductRequestEntity> getAllRequests();
    Iterable<ProductRequestEntity> getRequestsById(List<Long> ids);
    Client getClientById(Long id);
    List<Client> getClientsByName(String fname, String lname);
    List<Client> getClientsByFName(String fname);
    List<Client> getClientsByLName(String lname);
    List<Client> getClientsByOrganization(String organization);
    Client getClientByProductKey(String productKey);
    List<IAT> getTestsByClient(Client c);
    Iterable<ProductRequestEntity> getAllProductRequests();
    List<ResourcePrice> getResourcePrices();
    ProductKey getNewProductKey();
    DownloadPassword getNewDownloadPassword();
    Long getTotalTestSizeKB(Client c);
    Long getNumIATs(Client c);
    Calendar getLastIATUploadDate(Client c);
    Calendar getLastIATAdministrationDate(Client c);
    Calendar getLastDataRetrievalDate(Client c);
    Client getClientByDownloadPassword(String pass);
    Client updateClient(Client c);
    void setDownloadsRemaining(int nDownloads);
    List<Purchase> getPurchasesByProductKey(String productKey);
    List<CountryCode> getCountryCodes();
    void registerPurchase(Purchase p, Client c);
    Client getClientByEmail(String email);
    boolean containsClientWithEmail(String email);
    User getFirstUserWithEmail(String email);
}
