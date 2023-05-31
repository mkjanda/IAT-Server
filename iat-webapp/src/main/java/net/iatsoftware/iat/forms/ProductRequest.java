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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ProductRequest {
    @NotNull(message="{validate.productRequest.noFirstName}")
    @Pattern(regexp="[A-Z][A-Za-z']*\\s*", message="{validate.productRequest.invalidFirstName}")
    private String firstName;

    @NotNull(message="{validate.productRequest.noLastName}")
    @Pattern(regexp="[A-Z][A-Za-z']*\\s*", message="{validate.productRequest.invalidLastName}")
    private String lastName;

    @NotNull(message="{validate.productRequest.noEmail}")
    @Pattern(regexp=".+@.+\\..", message="{validate.productRequest.invalidateEmail}")
    private String email;

    @NotNull(message="{validate.productRequest.noProductUse}")
    @Pattern(regexp=".{50,}", message="{validate.productRequest.invalidProductUse}")
    private String use;
    
    public void setFirstName(String val) {
        this.firstName = val;
    }
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setLastName(String val) {
        this.lastName = val;
    }
    public String getLastName() {
        return this.lastName;
    }
    
    public void setEmail(String val) {
        this.email = val;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setProductUse(String val) {
        this.use = val;
    }
    public String getProductUse() {
        return this.use;
    }
}
