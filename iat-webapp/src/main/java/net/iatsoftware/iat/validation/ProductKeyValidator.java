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

import net.iatsoftware.iat.repositories.IATRepositoryManager;

import javax.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductKeyValidator implements ConstraintValidator<ProductKeyValid, CharSequence> {
    
    @Inject IATRepositoryManager repositoryManager;
    
    @Override
    public void initialize(ProductKeyValid annotation) {}
    
    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext ctx) {
        String productKey;
        if (value instanceof String) {
            productKey = (String)value;
        } else {
            productKey = value.toString();
        }
        if (repositoryManager.getClient(productKey) == null)
            return false;
        return true;
    }
    
}
