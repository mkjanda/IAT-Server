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

import org.springframework.stereotype.Repository;

import java.util.List;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class DefaultClientRepository extends GenericJpaRepository<Long, Client>
        implements ClientRepository {


    @Override
    public boolean productKeyExists(String productKey) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Client> root = query.from(Client.class);
        Predicate pred = cb.equal(root.get("productKey"), productKey);
        return this.entityManager.createQuery(query.select(cb.count(pred))).getSingleResult() >= 1L;
    }

    @Override
    public boolean downloadPasswordExists(String pass) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Client> root = query.from(Client.class);
        Predicate pred = cb.equal(root.get("downloadPassword"), pass);
        return this.entityManager.createQuery(query.select(cb.count(pred))).getSingleResult() >= 1L;
    }

    @Override
    public void freezeByProductKey(String key) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<Client> update = cb.createCriteriaUpdate(Client.class);
        Root<Client> root = update.from(Client.class);
        Predicate pred = cb.equal(root.get("clientId"), key);
        this.entityManager.createQuery(update.set(root.get("frozen"), true).where(pred)).executeUpdate();
    }

    @Override
    public void deleteByProductKey(String key) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<Client> update = cb.createCriteriaUpdate(Client.class);
        Root<Client> root = update.from(Client.class);
        Predicate pred = cb.equal(root.get("clientId"), key);
        this.entityManager.createQuery(update.set(root.get("deleted"), true).where(pred)).executeUpdate();
    }

    @Override
    public Iterable<Client> getClientsByIds(List<Long> ids) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = cb.createQuery(Client.class);
        Root<Client> root = query.from(Client.class);
        Predicate pred = cb.in(root.get("clientId")).in(ids);
        return this.entityManager.createQuery(query.select(root).where(pred)).getResultList();
    }

    @Override
    public List<Client> getClientsByName(String fname, String lname) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = cb.createQuery(Client.class);
        Root<Client> root = query.from(Client.class);
        Predicate pred = cb.and(cb.equal(root.get("contactFName"), fname), cb.equal(root.get("contactLName"), lname));
        return this.entityManager.createQuery(query.select(root).where(pred)).getResultList();
    }

    @Override
    public List<Client> getClientsByFName(String fname) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = cb.createQuery(Client.class);
        Root<Client> root = query.from(Client.class);
        Predicate pred = cb.equal(root.get("contactFName"), fname);
        return this.entityManager.createQuery(query.select(root).where(pred)).getResultList();
    }

    @Override
    public List<Client> getClientsByLName(String lname) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = cb.createQuery(Client.class);
        Root<Client> root = query.from(Client.class);
        Predicate pred = cb.equal(root.get("contactLName"), lname);
        return this.entityManager.createQuery(query.select(root).where(pred)).getResultList();
    }

    @Override
    public List<Client> getClientsByOrganization(String organization) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = cb.createQuery(Client.class);
        Root<Client> root = query.from(Client.class);
        Predicate pred = cb.equal(root.get("organization"), organization);
        return this.entityManager.createQuery(query.select(root).where(pred)).getResultList();
    }

    @Override
    public Client getByProductKey(String productKey) {
        try {
            CriteriaBuilder cBuilder = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Client> query = cBuilder.createQuery(Client.class);
            Root<Client> root = query.from(Client.class);
            return this.entityManager.createQuery(query.where(cBuilder.equal(root.get("productKey"), productKey))).getSingleResult();
        } catch (jakarta.persistence.NoResultException ex) {
            return null;
        }

    }

    @Override
    public void debitAdministration(Client c) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<Client> update = cb.createCriteriaUpdate(Client.class);
        Root<Client> root = update.from(Client.class);
        update.set("administrationsRemaining", cb.diff(root.get("administrationsRemaining"), 1));
        Predicate pred = cb.and(cb.equal(root.get("clientId"), c.getClientId()), cb.notEqual(root.get("administrationsRemaining"), cb.nullLiteral(Integer.class)));
        this.entityManager.createQuery(update.where(pred)).executeUpdate();
        update = cb.createCriteriaUpdate(Client.class);
        update.set("administrations", cb.sum(root.get("administrations"), 1));
        pred = cb.equal(root.get("clientId"), c.getClientId());
        this.entityManager.createQuery(update.where(pred)).executeUpdate();
        c.setNumAdministrations(c.getNumAdministrations() + 1);
        if (c.getAdministrationsRemaining() != -1) {
            c.setAdministrationsRemaining(c.getAdministrationsRemaining() - 1);
        }
        update(c);
    }

    @Override
    public void creditAdministration(Client c) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<Client> update = cb.createCriteriaUpdate(Client.class);
        Root<Client> client = update.from(Client.class);
        update.set("administrationsRemaining", cb.sum(client.get("administrationsRemaining"), 1));
        update.set("administrations", cb.diff(client.get("administrations"), 1));
        Predicate pred = cb.and(cb.equal(client.get("clientId"), c.getClientId()), cb.notEqual(client.get("administrationsRemaining"), cb.nullLiteral(Integer.class)));
        this.entityManager.createQuery(update.where(pred)).executeUpdate();
    }

    @Override
    public ProductKey getNewProductKey() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Client> root = query.from(Client.class);
        ProductKey key = new ProductKey();
        boolean bExists = true;
        while (bExists) {
            key.generateProductKey();
            Predicate pred = cb.equal(root.get("productKey"), key.getProductKey());
            if (this.entityManager.createQuery(query.select(cb.count(root)).where(pred)).getSingleResult() > 0) {
                bExists = true;
            } else {
                bExists = false;
            }
        }
        return key;
    }

    @Override
    public DownloadPassword getNewDownloadPassword() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Client> root = query.from(Client.class);
        DownloadPassword pass = new DownloadPassword();
        boolean bExists = true;
        while (bExists) {
            pass.generateDownloadPassword();
            Predicate pred = cb.equal(root.get("downloadPassword"), pass.getDownloadPassword());
            if (this.entityManager.createQuery(query.select(cb.count(root)).where(pred)).getSingleResult() > 0) {
                bExists = true;
            } else {
                bExists = false;
            }
        }
        return pass;
    }

    @Override
    public Client getClientByDownloadPassword(String pass) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = cb.createQuery(Client.class);
        Root<Client> root = query.from(Client.class);
        Predicate pred = cb.equal(root.get("downloadPassword"), pass);
        try {
            return this.entityManager.createQuery(query.where(pred)).getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void updateDownloadsRemaining(int nDownloads) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<Client> update = cb.createCriteriaUpdate(Client.class);
        Root<Client> root = update.from(Client.class);
        Predicate pred = cb.notEqual(root.get("downloadsRemaining"), cb.nullLiteral(Integer.class));
        this.entityManager.createQuery(update.set(root.get("downloadsRemaining"), nDownloads).where(pred)).executeUpdate();
    }

    @Override
    public Client getClientByEmail(String email) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<Client> root = query.from(Client.class);
            Predicate pred = cb.equal(root.get("email"), email.toLowerCase());
            Long id = this.entityManager.createQuery(query.select(cb.min(root.get("clientId"))).where(pred)).getSingleResult();
            CriteriaQuery<Client> clientQuery = cb.createQuery(Client.class);
            root = clientQuery.from(Client.class);
            pred = cb.equal(root.get("clientId"), id);
            return this.entityManager.createQuery(clientQuery.select(root).where(pred)).getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }
    
    @Override
    public boolean clientWithEmailExists(String email) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Client> root = query.from(Client.class);
        Predicate pred = cb.equal(root.get("email"), email.toLowerCase());
        return (this.entityManager.createQuery(query.select(cb.count(root)).where(pred)).getSingleResult() > 0L);
    }
}
