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
import net.iatsoftware.iat.forms.RegisterTestForRestfulForm;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OAuthRequestTestNameValidator implements ConstraintValidator<OAuthRequestTestNameValid, RegisterTestForRestfulForm> {
    
    @Inject IATRepositoryManager repositoryManager;
    
    @Override
    public void initialize(OAuthRequestTestNameValid annotation) {}
    
    @Override
    public boolean isValid(RegisterTestForRestfulForm form, ConstraintValidatorContext ctx) {
        Client c = repositoryManager.getClient(form.getProductKey());
        if (c == null)
            return false;
        if (repositoryManager.getIATByNameAndClientID(form.getTestName(), c.getClientId()) == null)
            return false;
        return true;
    }
}
