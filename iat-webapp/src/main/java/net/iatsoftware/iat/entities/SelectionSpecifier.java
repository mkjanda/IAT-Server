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

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Basic;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@Entity
@PrimaryKeyJoinColumn(name="SpecifierID")
@DiscriminatorValue("Selection")
@Table(name="selection_specifiers")
@XmlType(name="SelectionSpecifier")
@XmlAccessorType(XmlAccessType.NONE)
public class SelectionSpecifier extends net.iatsoftware.iat.generated.GSelectionSpecifier {
    private static final long serialVersionUID = 1L;
    
    @Basic
    @Column(name="key_specifiers")
    public String getKeySpecifierString()
    {
            String result = "";
            if (this.keySpecifiers == null)
                return result;
            for (String str : getKeySpecifiers().getKeySpecifier())
                result += str + "|";
            return result;
    }
    public void setKeySpecifierString(String val)
    {
        if (this.keySpecifiers == null)
            this.keySpecifiers = new KeySpecifiers();
        Pattern p = Pattern.compile("[^|]+");
        Matcher m = p.matcher(val);
        while (m.find()) {
            this.keySpecifiers.getKeySpecifier().add(m.group(0));
        }
    }
    
    @Override
    public String testValue(String val)
    {
        for (int ctr = 0; ctr < getKeySpecifiers().getKeySpecifier().size(); ctr++)
            if (getKeySpecifiers().getKeySpecifier().get(ctr).equals(val))
                return Integer.toString(ctr + 1);
        return "";
    }
}
