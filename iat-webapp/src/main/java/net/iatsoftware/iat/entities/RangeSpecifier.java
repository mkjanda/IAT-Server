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

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Basic;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@Entity
@DiscriminatorValue("Range")
@PrimaryKeyJoinColumn(name="SpecifierID")
@Table(name="range_specifiers")
@XmlType(name="RangeSpecifier")
@XmlAccessorType(XmlAccessType.NONE)
public class RangeSpecifier extends net.iatsoftware.iat.generated.RangeSpecifierPojo {
    
    public RangeSpecifier(){}

    @Basic
    @Column(name="cutoff")
    @Override
    public int getCutoff()
    {
        return super.getCutoff();
    }
    @Override
    public void setCutoff(int val)
    {
        cutoff = val;
    }
    
    @Basic
    @Column(name="reverse_scored")
    @Override
    public boolean isReverseScored()
    {
        return reverseScored;
    }
    @Override
    public void setReverseScored(boolean val)
    {
        reverseScored = val;
    }
    
    @Basic
    @Column(name="cutoff_excludes")
    @Override
    public boolean isCutoffExcludes()
    {
        return cutoffExcludes;
    }
    @Override
    public void setCutoffExcludes(boolean val)
    {
        super.setCutoffExcludes(val);
    }
    
    @Override
    public String testValue(String val)
    {
        int response = Integer.parseInt(val);
        if (cutoffExcludes && (response == cutoff))
            return "Exclude";
        if (reverseScored && (response > cutoff))
            return "True";
        else if (reverseScored)
            return "False";
        else if (response < cutoff)
            return "True";
        else
            return "False";
    }
}
