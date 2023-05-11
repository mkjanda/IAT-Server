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


import net.iatsoftware.iat.entities.AdminTimer;
import net.iatsoftware.iat.entities.SpecifierValue;

import java.util.List;

public interface SpecifierValueRepository extends GenericRepository<Long, SpecifierValue> {
    List<SpecifierValue> getByAdminID(AdminTimer admin);
}
