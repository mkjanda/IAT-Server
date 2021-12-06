/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.services;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author Michael Janda
 */
public class MailAuthenticator extends Authenticator {
    @Override
    protected PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication("IATSoftware", "ytND42_)f");
    }
}
