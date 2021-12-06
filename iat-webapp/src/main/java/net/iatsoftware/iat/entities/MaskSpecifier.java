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

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlType;

@Entity
@PrimaryKeyJoinColumn(name="SpecifierID")
@DiscriminatorValue("Mask")
@Table(name="mask_specifiers")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="MaskSpecifier")
public class MaskSpecifier extends net.iatsoftware.iat.generated.MaskSpecifierPojo {
    private static final long serialVersionUID = 1;    
    public MaskSpecifier(){}

    @Basic
    @Column(name="mask")
    public String getMaskString()
    {
        return this.mask;
    }
    public void setMaskString(String val)
    {
        this.mask = val;
    }
    
    @Override
    public String testValue(String val)
    {
        int resp = Integer.parseInt(val, 2);
        int m = Integer.parseInt(mask, 2);
        if ((resp & m) != 0)
            return "True";
        return "False";
    }
}
