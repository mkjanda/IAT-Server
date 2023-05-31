package net.iatsoftware.iat.validation;

import net.iatsoftware.iat.forms.OAuthRequestForm;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import javax.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OAuthIdMatchesSecretValidator implements ConstraintValidator<OAuthIdMatchesSecret, OAuthRequestForm> {
    
    @Inject IATRepositoryManager repositoryManager;

    @Override
    public void initialize(OAuthIdMatchesSecret annotation){}

    @Override
    public boolean isValid(OAuthRequestForm form, ConstraintValidatorContext ctx) {
        return repositoryManager.clientIdMatchesClientSecret(form.getClientId(), form.getClientSecret());
    }
}