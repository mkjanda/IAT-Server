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
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {OAuthRequestTestPasswordValidator.class})
@ReportAsSingleViolation
public @interface OAuthRequestTestPasswordValid {
    String message() default "Invalid Test Name";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}

