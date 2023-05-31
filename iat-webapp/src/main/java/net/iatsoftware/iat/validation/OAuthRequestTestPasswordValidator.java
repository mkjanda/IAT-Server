/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.validation;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.forms.RegisterTestForRestfulForm;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import javax.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OAuthRequestTestPasswordValidator implements ConstraintValidator<OAuthRequestTestPasswordValid, RegisterTestForRestfulForm> {
    
    @Inject IATRepositoryManager repositoryManager;
    
    @Override
    public void initialize(OAuthRequestTestPasswordValid annotation) {}
    
    @Override
    public boolean isValid(RegisterTestForRestfulForm form, ConstraintValidatorContext ctx) {
        Client c = repositoryManager.getClient(form.getProductKey());
        if (c == null)
            return false;
        PartiallyEncryptedRSAKey key = repositoryManager.getDataKey(c.getClientId(), form.getTestName());
        if (key == null)
            return false;
        if (!key.testPassword(form.getTestPassword()))
            return false;
        return true;
    }
}
