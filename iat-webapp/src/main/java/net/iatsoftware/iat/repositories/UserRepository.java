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

import net.iatsoftware.iat.entities.User;

public interface UserRepository extends GenericRepository<Long, User> {
    User getFirstUserWithEmail(String email) throws javax.persistence.NonUniqueResultException;
    User getUserByClientAndActivationKey(Client c, String email) throws javax.persistence.NoResultException;
    User getUserByVerificationKey(String verificaitonKey);
    User getUserByClientAndEmail(Client c, String email);
}
