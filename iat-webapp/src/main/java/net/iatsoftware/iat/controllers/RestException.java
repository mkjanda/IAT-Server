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

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class RestException extends RuntimeException {
    private static final long serialVersionUID = 1;
    
    public RestException(String msg) {
        super(msg);
    }
    
    
    public RestException(String msg, Exception innerEx) {
        super(msg, innerEx);
    }
    
    @XmlElement(name="error")
    public String getErrorMessage() {
        return super.getMessage();
    }
    
    @XmlElement(name="stack_trace")
    public List<String> getTextStackTrace() {
        if (super.getCause() == null)
            return new ArrayList<>();
        List<String> returnValue = new ArrayList<>();
        Throwable cause = super.getCause();
        while (cause != null) {
            returnValue.add("Caused by:");
            StackTraceElement[] stackTrace = cause.getStackTrace();
            returnValue.addAll(Arrays.asList(stackTrace).stream().map((ste) -> "     " + ste.toString()).collect(Collectors.toList()));
            cause = cause.getCause();
        }
        return returnValue;
    }
}
