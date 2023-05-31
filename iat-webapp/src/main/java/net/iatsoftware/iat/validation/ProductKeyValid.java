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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ProductKeyValidator.class})
@NotNull
@Pattern(regexp="[A-Za-z0-9]{20}")
@ReportAsSingleViolation
public @interface ProductKeyValid {
    String message() default "Invalid Product Key";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
