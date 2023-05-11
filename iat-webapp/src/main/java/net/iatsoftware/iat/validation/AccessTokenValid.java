package net.iatsoftware.iat.validation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AccessTokenValidator.class})
@NotNull
@Pattern(regexp="[A-Za-z0-9]{20}")
@ReportAsSingleViolation
public @interface AccessTokenValid {
    String message() default "Invalid accesss token";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}

