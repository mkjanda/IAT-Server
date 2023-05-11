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

@XmlType(name="StringElement")
@XmlAccessorType(XmlAccessType.NONE)
public class StringElement extends net.iatsoftware.iat.generated.StringElement {
    public StringElement(){}
    
    public StringElement(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
