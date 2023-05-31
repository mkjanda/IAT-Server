/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.validation;

/**
 *
 * @author michael
 */



import java.util.Properties;
import java.util.regex.Pattern;

import javax.inject.Named;
import javax.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class URLValidator implements ConstraintValidator<URL, CharSequence> {
    @Named("ServerConfiguration")
    @Inject Properties serverConfiguration;
    
    @Override
    public void initialize(URL annotation) {}
    
    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext ctx) {
        if (Boolean.parseBoolean(serverConfiguration.getProperty("oauth-https-enabled"))) {
            Pattern p = Pattern.compile("^https://.+");
            if (!p.matcher(value).matches())
                return false;
            return true;
        } else {
            Pattern p = Pattern.compile("^http://.+");
            if (!p.matcher(value).matches())
                return false;
            return true;
        }
    }
}
