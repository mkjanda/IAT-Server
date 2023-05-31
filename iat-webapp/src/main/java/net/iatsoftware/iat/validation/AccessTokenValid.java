package net.iatsoftware.iat.validation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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

