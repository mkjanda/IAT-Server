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

import org.springframework.stereotype.Repository;

import net.iatsoftware.iat.entities.SelectionSpecifier;

@Repository
public class DefaultSelectionSpecifierRepository extends GenericJpaRepository<Long, SelectionSpecifier> 
    implements SelectionSpecifierRepository
{
    
}
