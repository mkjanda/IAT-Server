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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="IntElement")
@XmlAccessorType(XmlAccessType.NONE)
public class IntElement extends net.iatsoftware.iat.generated.IntElement {
    public IntElement(){}
    
    public IntElement(String name, int value) {
        this.value = value;
        this.name = name;
    }
}
