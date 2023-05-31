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

import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name="Message")
public class Message extends net.iatsoftware.iat.generated.MessagePojo {
    public Message() {
        super();
    }
    
    @Override
    public boolean doBeforeMarshal(Marshaller m) { return true; }
    @Override
    public void doAfterUnmarshal(Unmarshaller um, Object parent) {}
}
