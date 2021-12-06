/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.controllers;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(annotations = {ClientControllerAnnotation.class})
public class ClientControllerExceptionAdvisor {
    private static final Logger logger = LogManager.getLogger("critical");
    
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(javax.mail.MessagingException.class)
    @ResponseBody String handleMessagingException(javax.mail.MessagingException ex) {
        logger.error(ex);
        return "fail";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalStateException.class)
    void handelIllegalStateException(IllegalStateException ex) {
        logger.error(ex);
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    void handleGenericException(Exception ex) {
        logger.error(ex);
    }
   
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(java.net.URISyntaxException.class)
    void handleUriSyntaxException(java.net.URISyntaxException ex) {
        logger.error("Invalid URI", ex);
    }
}
