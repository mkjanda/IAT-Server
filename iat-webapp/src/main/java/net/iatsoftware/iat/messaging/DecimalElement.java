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

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DecimalElement")
@XmlAccessorType(XmlAccessType.NONE)
public class DecimalElement extends net.iatsoftware.iat.generated.DecimalElement {
    public DecimalElement() {}

    public DecimalElement(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }
}
