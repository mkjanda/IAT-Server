/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

/**
 *
 * @author michael
 */

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name="LongElement")
@XmlAccessorType(XmlAccessType.NONE)
public class LongElement extends net.iatsoftware.iat.generated.LongElement {
    public LongElement(){}
    
    public LongElement(String name, long value) {
        this.name = name;
        this.value = value;
    }
}
