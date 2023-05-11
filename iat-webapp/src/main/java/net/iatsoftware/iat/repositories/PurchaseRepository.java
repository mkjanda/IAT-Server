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


import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.Purchase;

import java.util.List;

public interface PurchaseRepository extends GenericRepository<Long, Purchase> {
    List<Purchase> getPurchasesByClient(Client c);
}
