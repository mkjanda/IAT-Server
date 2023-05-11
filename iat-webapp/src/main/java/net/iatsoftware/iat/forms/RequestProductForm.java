/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.forms;

/**
 *
 * @author Michael Janda
 */

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class RequestProductForm {
    @NotBlank(message="{validate.productRequest.noFirstName}")
    private String firstName;

    @NotBlank(message="{validate.productRequest.noLastName}")
    @Length(min=2, message="{validate.productRequest.invalidLastName}")
    private String lastName;
    
    @NotBlank(message="{validate.productRequest.noEmail}")
    @Email(message="{validate.productRequest.invalidEmail}")
    @Length(max=255, message="{validate.productRequest.invalidEmail}")
    private String email;
    
    @NotBlank(message="{validate.productRequest.noProductUse}")
    @Length(min=50, message="{validate.productRequest.shortProductUse}")
    private String use;
    
    public RequestProductForm(){}
    
    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String val) {
        this.firstName = val;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String val) {
        this.lastName = val;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String val) {
        this.email = val;
    }

    public String getUse() {
        return this.use;
    }
    public void setUse(String val) {
        this.use = val;
    }
}