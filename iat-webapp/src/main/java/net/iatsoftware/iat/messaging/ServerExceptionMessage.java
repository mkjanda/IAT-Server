/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

/**
 *
 * @author Michael Janda
 */

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ServerException")
@XmlAccessorType(XmlAccessType.NONE)
public class ServerExceptionMessage extends net.iatsoftware.iat.generated.ServerExceptionMessagePojo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    public ServerExceptionMessage() { }
    
    public ServerExceptionMessage(String msg, Exception ex) {
        this.serverMessage = msg;
        this.exceptionMessage = ex.toString();
        this.stackTraceElement = new ArrayList<>();
        for (StackTraceElement e : ex.getStackTrace())
            this.stackTraceElement.add(e.toString());
        this.innerException = new ArrayList<>();
        Throwable t = ex.getCause();
        while (t != null) {
            InnerException inner = new InnerException();
            inner.setExceptionMessage(t.getMessage());
            for (StackTraceElement e : t.getStackTrace())
                inner.getStackTraceElement().add(e.toString());
            this.innerException.add(inner);
            t = t.getCause();
        }
    }
}
