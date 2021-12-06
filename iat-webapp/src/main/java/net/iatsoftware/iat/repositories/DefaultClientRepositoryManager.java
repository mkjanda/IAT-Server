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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

@Service("ClientRepositoryManager")
public class DefaultClientRepositoryManager implements ClientRepositoryManager {
    
    @Inject
    ProductRequestRepository productRequestRepository;
    @Inject
    ClientRepository clientRepository;
    @Inject
    IATRepository iatRepository;
    @Inject
    ResourcePriceRepository resourcePriceRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    ResultSetRepository resultSetRepository;
    @Inject
    PurchaseRepository purchaseRepository;
    @Inject
    CountryCodeRepository countryCodeRepository;
    
    @Override
    @Transactional
    public void recordProductRequest(ProductRequestEntity pr) {
        productRequestRepository.add(pr);
    }
    
    @Override
    @Transactional
    public boolean productKeyExists(String productKey) {
        return clientRepository.productKeyExists(productKey);
    }
    
    @Override
    @Transactional
    public boolean downloadPasswordExists(String pass) {
        return clientRepository.downloadPasswordExists(pass);
    }
    
    @Override
    @Transactional
    public ProductRequestEntity getProductRequest(Long id) {
        return productRequestRepository.get(id);
    }
    
    @Override
    @Transactional
    public void addClient(Client c) {
        clientRepository.add(c);
    }
   
    @Override
    @Transactional
    public void deleteProductRequest(ProductRequestEntity pRequest) {
        productRequestRepository.delete(pRequest);
    }
    
    @Override
    @Transactional
    public void freezeClient(String productKey) {
        clientRepository.freezeByProductKey(productKey);
    }
    
    @Override
    @Transactional
    public void deleteClient(String productKey) {
        clientRepository.deleteByProductKey(productKey);
    }
    
    @Override
    @Transactional
    public Client getClient(String productKey) {
        return clientRepository.getByProductKey(productKey);
    }
    
    @Override
    @Transactional
    public Iterable<Client> getAllClients() {
        return clientRepository.getAll();
    }
    
    @Override
    @Transactional
    public Iterable<Client> getClientsByIds(List<Long> ids) {
        return clientRepository.getClientsByIds(ids);
    }
    
    @Override
    @Transactional
    public Iterable<ProductRequestEntity> getAllRequests() {
        return productRequestRepository.getAll();
    }
    
    @Override
    @Transactional
    public Iterable<ProductRequestEntity> getRequestsById(List<Long> ids) {
        return productRequestRepository.getRequestsById(ids);
    }
    
    @Override
    @Transactional
    public Client getClientById(Long id) {
        return clientRepository.get(id);
    }
    
    @Override
    @Transactional
    public List<Client> getClientsByName(String fname, String lname) {
        return clientRepository.getClientsByName(fname, lname);
    }
    
    @Override
    @Transactional
    public List<Client> getClientsByFName(String fName) {
        return clientRepository.getClientsByFName(fName);
    }
    
    @Override
    @Transactional
    public List<Client> getClientsByLName(String lName) {
        return clientRepository.getClientsByLName(lName);
    }
    
    @Override
    @Transactional
    public List<Client> getClientsByOrganization(String organization) {
        return clientRepository.getClientsByOrganization(organization);
    }
    
    @Override
    @Transactional
    public Client getClientByProductKey(String productKey) {
        return clientRepository.getByProductKey(productKey);
    }
    
    @Override
    @Transactional
    public List<IAT> getTestsByClient(Client c) {
        return iatRepository.getIATsByClient(c);
    }
    
    @Override
    @Transactional
    public Iterable<ProductRequestEntity> getAllProductRequests() {
        return productRequestRepository.getAll();
    }
    
    @Override
    @Transactional
    public List<ResourcePrice> getResourcePrices() {
        return resourcePriceRepository.getResourcePrices();
    }
    
    @Override
    @Transactional
    public ProductKey getNewProductKey() {
        return clientRepository.getNewProductKey();
    }
    
    @Override
    @Transactional
    public DownloadPassword getNewDownloadPassword() {
        return clientRepository.getNewDownloadPassword();
    }
    
    @Override
    @Transactional
    public Long getTotalTestSizeKB(Client c) {
        return iatRepository.getConsumedDiskSpaceKB(c);
    }
    
    @Override
    @Transactional
    public Long getNumIATs(Client c) {
        return iatRepository.getNumIATs(c);
    }
    
    @Override
    @Transactional
    public Calendar getLastIATUploadDate(Client c) {
        return iatRepository.getLastIATUploadDate(c);
    }
    
    @Override
    @Transactional
    public Calendar getLastIATAdministrationDate(Client c) {
        List<IAT> iats = iatRepository.getIATsByClient(c);
        return resultSetRepository.getLastIATAdminDate(iats);
    }
    
    @Override
    @Transactional
    public Calendar getLastDataRetrievalDate(Client c) {
        return iatRepository.getLastDataRetrievalDate(c);
    }
    
    @Override
    @Transactional
    public Client getClientByDownloadPassword(String pass) {
        return clientRepository.getClientByDownloadPassword(pass);
    }
    
    @Override
    @Transactional
    public Client updateClient(Client c) {
        return clientRepository.update(c);
    }
    
    @Override
    @Transactional
    public void setDownloadsRemaining(int nDownloads) {
        clientRepository.updateDownloadsRemaining(nDownloads);
    }
    
    @Override
    @Transactional
    public List<Purchase> getPurchasesByProductKey(String productKey) {
        Client c = clientRepository.getByProductKey(productKey);
        return purchaseRepository.getPurchasesByClient(c);
    }
    
    @Override
    @Transactional
    public List<CountryCode> getCountryCodes() {
        return countryCodeRepository.getCountryCodes();
    }
    
    @Override
    @Transactional
    public void registerPurchase(Purchase p, Client c) {
        p.setClient(c);
        purchaseRepository.add(p);
        c.setAdministrationsRemaining(c.getAdministrationsRemaining() + p.getNumAdministrations());
        c.setNumIATsAlotted(c.getNumIATsAlotted() + p.getNumTests());
        c.setDiskAlottmentMB(c.getDiskAlottmentMB() + 10 * (p.getNumTests()) + p.getDiskSpace());
        clientRepository.update(c);
    }
    
    @Override
    @Transactional
    public Client getClientByEmail(String email) {
        return clientRepository.getClientByEmail(email);
    }
    
    @Override
    @Transactional
    public boolean containsClientWithEmail(String email) {
        return clientRepository.clientWithEmailExists(email);
    }
    
    @Override
    @Transactional
    public User getFirstUserWithEmail(String email) throws ConflictingUsersException {
        return userRepository.getFirstUserWithEmail(email);
    }
}