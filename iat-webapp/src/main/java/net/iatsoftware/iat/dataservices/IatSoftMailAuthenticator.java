/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.dataservices;

/**
 *
 * @author Michael Janda
 */

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

public class IatSoftMailAuthenticator extends Authenticator {
    
    @Override
    protected PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication("iatsoftware", "ytND42_)f");
    }

}
