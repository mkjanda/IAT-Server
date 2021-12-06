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

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

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
