package net.iatsoftware.iat.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {OAuthIdMatchesSecretValidator.class})
@ReportAsSingleViolation
public @interface OAuthIdMatchesSecret {
    String message() default "Client Id does not match Client Secret";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
