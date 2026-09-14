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

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class MessageBase {
    public boolean beforeMarshal(Marshaller m) {
        return doBeforeMarshal(m);
    }
    
    public void afterUnmarshal(Unmarshaller um, Object parent) {
        doAfterUnmarshal(um, parent);
    }
    
    protected boolean doBeforeMarshal(Marshaller m) { return true; };
    protected void doAfterUnmarshal(Unmarshaller um, Object parent){};
}
