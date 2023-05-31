/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.entities;

/**
 *
 * @author Michael Janda
 */

import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@XmlType(name="TrueFalseSpecifier")
@XmlAccessorType(XmlAccessType.NONE)
@DiscriminatorValue("TrueFalse")
@Table(name="true_false_specifiers")
public class TrueFalseSpecifier extends net.iatsoftware.iat.generated.TrueFalseSpecifierPojo {
    
    public TrueFalseSpecifier(){}
    
    @Override
    public String testValue(String val)
    {
        if (val.equals("1"))
            return "True";
        return "False";
    }
}
