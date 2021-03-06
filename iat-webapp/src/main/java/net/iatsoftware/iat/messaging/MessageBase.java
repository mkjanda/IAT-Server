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

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class MessageBase {
    public boolean beforeMarshal(Marshaller m) {
        return doBeforeMarshal(m);
    }
    
    public void afterUnmarshal(Unmarshaller um, Object parent) {
        doAfterUnmarshal(um, parent);
    }
    
    public abstract boolean doBeforeMarshal(Marshaller m);
    public abstract void doAfterUnmarshal(Unmarshaller um, Object parent);
}
