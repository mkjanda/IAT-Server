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

import net.iatsoftware.iat.entities.IAT;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class OAuthException extends Exception implements ApplicationContextAware {
    private static final long serialVersionUID = 1;
    private  String message, title, caption, oClientId;
    private Long testId;
    private final OAuthExceptionType type;
    private transient ApplicationContext ctx;

    public OAuthException(OAuthException o) {
        this.message = o.message;
        this.title = o.title;
        this.caption = o.caption;
        this.oClientId = o.oClientId;
        this.type = o.type;
    }

    public OAuthException(OAuthExceptionType type, String oClientId) {
        constructException(type);
        this.oClientId = oClientId;
        this.type = type;
    }

    public OAuthException(OAuthExceptionType type, IAT test, String oClientId) {
        this.testId = test.getId();
        this.oClientId = oClientId;
        this.type = type;
        constructException(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    private void constructException(OAuthExceptionType type) {
        MessageSource source = (MessageSource) ctx.getBean("ExceptionMessageSource");
        this.title = source.getMessage("oauth.title", null, Locale.ROOT);
        switch (type) {
            case INVALID_CLIENT_ID:
                this.caption = source.getMessage("oauth.invalid.caption", null, Locale.ROOT);
                this.message = source.getMessage("oauth.invalid.message", null, Locale.ROOT);
                break;

            case EXPLICIT_REDIRECT_NOT_SUPPORTED:
                this.caption = source.getMessage("oauth.redirection.caption", null, Locale.ROOT);
                this.message = source.getMessage("oauth.redirection.message", null, Locale.ROOT);
                break;

            default:
                this.caption = "Unknown OAuth Exception Type";
                this.message = "Unknown OAuth Exception Type";
                break;
        }
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public String getCaption() {
        return this.caption;
    }

    public String getTestId() {
        return this.testId.toString();
    }

    public String getOClientId() {
        return this.oClientId;
    }

    public String getExceptionTypeString() {
        return this.type.toString();
    }
}
