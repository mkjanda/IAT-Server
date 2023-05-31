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

import net.iatsoftware.iat.generated.CodeType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Transient;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import java.util.Random;


@Entity
@Table(name="test_code", indexes={
    @Index(name="codes_index", columnList="TestSegmentID")
})
@JsonIgnoreProperties(allowSetters = false)
public class EncCodeLine implements java.io.Serializable {
    static private final long serialVersionUID = 1;
    static public final Random rand = new Random();
    private long id;
    private TestSegment testSegment;
    private String entityName;
    int andx;
    int bndx;
    int cl;
    private CodeType type; 
    private final String[] code;
    private int ordinal = 0;
    
    public EncCodeLine()
    {
        code = new String[4];
    }
    
    public EncCodeLine(TestSegment test, String[] encCode, String entityName, int codeLength, int keySetNum, CodeType type, int ordinal)
    {
        code = new String[4];
        code[0] = encCode[0];
        code[1] = encCode[1];
        code[2] = encCode[2];
        code[3] = encCode[3];
        cl = codeLength;
        this.testSegment = test;
        andx = rand.nextInt(65536);
        bndx = andx ^ keySetNum;
        this.type = type;
        this.entityName = entityName;
        this.ordinal = ordinal;
    }
    
    @Id
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="TestCodeID")
    public long getID()
    {
        return this.id;
    }
    public void setID(long val)
    {
        this.id = val;
    }
    
    @JsonIgnore
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="TestSegmentID", referencedColumnName="TestSegmentID")
    public TestSegment getTestSegment()
    {
        return this.testSegment;
    }
    public void setTestSegment(TestSegment val)
    {
        this.testSegment = val;
    }
    
    @Basic
    @Column(name="entity_name")
    public String getEntityName() {
        return this.entityName;
    }
    public void setEntityName(String val) {
        this.entityName = val;
    }
    
    @Basic
    @JsonProperty("ANDX")
    @Column(name="andx")
    public int getAndx()
    {
        return this.andx;
    }
    public void setAndx(int val)
    {
        this.andx = val;
    }
           
    @Basic
    @JsonProperty("BNDX")
    @Column(name="bndx")
    public int getBndx()
    {
        return this.bndx;
    }
    public void setBndx(int val)
    {
        this.bndx = val;
    }
    
    @Basic
    @JsonProperty("CL")
    @Column(name="cl")
    public int getCL()
    {
        return this.cl;
    }
    public void setCL(int val)
    {
        this.cl = val;
    }

    @JsonProperty("Type")
    @Enumerated(EnumType.STRING)
    @Basic
    @Column(name="code_type")
    public CodeType getType()
    {
        return this.type;
    }
    public void setType(CodeType val)
    {
        this.type = val;
    }
    
    @Lob
    @JsonIgnore
    @Column(name="code1")
    public String getCode1()
    {
        return this.code[0];
    }
    public void setCode1(String val)
    {
        this.code[0] = val;
    }

    @Lob
    @JsonIgnore
    @Column(name="code2")
    public String getCode2()
    {
        return this.code[1];
    }
    public void setCode2(String val)
    {
        this.code[1] = val;
    }

    @Lob
    @JsonIgnore
    @Column(name="code3")
    public String getCode3()
    {
        return this.code[2];
    }
    public void setCode3(String val)
    {
        this.code[2] = val;
    }

    @Lob
    @JsonIgnore
    @Column(name="code4")
    public String getCode4()
    {
        return this.code[3];
    }
    public void setCode4(String val)
    {
        this.code[3] = val;
    }
    
    @Basic
    @JsonIgnore
    @Column(name="ordinal")
    public int getOrdinal() {
        return this.ordinal;
    }
    public void setOrdinal(int val) {
        this.ordinal = val;
    }
    
    @Transient
    @JsonProperty("Lines")
    public String[] getCodeLines() {
        return this.code;
    }
}
