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
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="dynamic_specifiers", indexes={
    @Index(name="tests_id", columnList="TestID")
})
@DiscriminatorColumn(name="SpecifierType", discriminatorType=DiscriminatorType.STRING)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="DynamicSpecifier")
public abstract class DynamicSpecifier extends net.iatsoftware.iat.generated.DynamicSpecifierPojo implements java.io.Serializable {
    private long id;
    private IAT test;
    private TestSegment testSegment;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="SpecifierID")
    public long getId()
    {
        return this.id;
    }
    public void setId(long val)
    {
        this.id = val;
    }

    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="TestID")
    public IAT getTest()
    {
        return this.test;
    }
    public void setTest(IAT val)
    {
        this.test = val;
    }

    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="TestSegmentID")
    public TestSegment getTestSegment() {
        return this.testSegment;
    }
    public void setTestSegment(TestSegment val) {
        this.testSegment = val;
    }
            
    @Basic
    @Column(name="item_num")
    @Override
    public int getItemNum()
    {
        return this.itemNum;
    }
    @Override
    public void setItemNum(int val)
    {
        this.itemNum = val;
    }
    
    @Basic
    @Column(name="test_specifier_id")
    @Override
    public int getTestSpecifierID() {
        return this.testSpecifierID;
    }
    @Override
    public void setTestSpecifierID(int val) {
        this.testSpecifierID = val;
    }
    
    public abstract String testValue(String val);
}
