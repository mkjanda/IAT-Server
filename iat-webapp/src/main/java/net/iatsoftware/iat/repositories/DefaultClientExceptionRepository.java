/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.repositories;

/**
 *
 * @author michael
 */

import net.iatsoftware.iat.entities.ClientExceptionReport;

import org.springframework.stereotype.Repository;

@Repository
public class DefaultClientExceptionRepository extends GenericJpaRepository<Long, ClientExceptionReport> implements ClientExceptionRepository {
    
}
