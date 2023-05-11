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

import javax.validation.constraints.Size;

public class RequestDownloadForm {
    @Size(min=20, max=20, message="{validate.requestDownload.invalidPassword}")
    private String password;
    
    public RequestDownloadForm() {
        password = "";
    }
    
    public RequestDownloadForm(String pass) {
        this.password = pass;
    }
    
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String val) {
        this.password = val;
    }
}
