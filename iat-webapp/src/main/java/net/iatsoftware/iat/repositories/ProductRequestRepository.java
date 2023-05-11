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

import net.iatsoftware.iat.entities.ProductRequestEntity;

import java.util.List;

public interface ProductRequestRepository extends GenericRepository<Long, ProductRequestEntity> {
    Iterable<ProductRequestEntity> getRequestsById(List<Long> ids);
}
