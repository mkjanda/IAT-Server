package net.iatsoftware.iat.validation;

import net.iatsoftware.iat.entities.OAuthAccess;
import net.iatsoftware.iat.forms.RetrieveResultForm;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import javax.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccessTokenValidator implements ConstraintValidator<AccessTokenValid, RetrieveResultForm> {
    @Inject
    IATRepositoryManager repositoryManager;

    @Override
    public void initialize(AccessTokenValid annotation){}

    @Override
    public boolean isValid(RetrieveResultForm form, ConstraintValidatorContext ctx) {
        OAuthAccess oauth = repositoryManager.validateAccessToken(form.getAccessToken());
        if (oauth == null)
            return false;
        if (!oauth.getTest().getTestName().equals(form.getTestName()))
            return false;
        return true;
    }
}

