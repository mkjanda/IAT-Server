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

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Basic;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="dynamic_specifiers", indexes={
    @Index(name="tests_id", columnList="TestID")
})
@DiscriminatorColumn(name="SpecifierType", discriminatorType=DiscriminatorType.STRING)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="DynamicSpecifier")
public abstract class DynamicSpecifier extends net.iatsoftware.iat.generated.GDynamicSpecifier implements java.io.Serializable {
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
