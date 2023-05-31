/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.services;

/**
 *
 * @author michael
 */


public interface MailService {
    void sendEmail(EmailParameters params) throws jakarta.mail.MessagingException;
    void reportError(String serverMessage, Exception ex) throws jakarta.mail.MessagingException;
}
