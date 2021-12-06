/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.controllers;

/**
 *
 * @author michael
 */
public class OAuthWebException extends OAuthException {
    private static final long serialVersionUID = 1;
    public OAuthWebException(OAuthException ex) {
        super(ex);
    }
    
    public OAuthWebException(OAuthExceptionType type, String clientId) {
        super(type, clientId);
    }
}
