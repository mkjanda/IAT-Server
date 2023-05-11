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


import java.util.Set;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Lob;
import javax.persistence.Index;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
@Table(name = "test_segments", indexes = {
    @Index(name = "tests_id", columnList = "TestId")
})
public class TestSegment implements java.io.Serializable {

    private long id;
    private IAT test;
    private String elementName;
    private String html;
    private String jskeys_xml;
    private boolean iat = false;
    private int alternationPriority, initialPos, numAlternations;
    private Set<DynamicSpecifier> dynamicSpecifiers;
    public TestSegment() {
    }

    public TestSegment(IAT test, String elementName, String html, int alternationPriority, int initialPos) {
        this.test = test;
        this.elementName = elementName;
        this.html = html;
        this.alternationPriority = alternationPriority;
        this.numAlternations = 0;
        this.initialPos = initialPos;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TestSegmentID")
    public long getId() {
        return this.id;
    }

    public void setId(long val) {
        this.id = val;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "TestID")
    public IAT getTest() {
        return this.test;
    }

    public void setTest(IAT val) {
        this.test = val;
    }

    @Basic
    @Column(name = "elem_name")
    public String getElemName() {
        return this.elementName;
    }

    public void setElemName(String val) {
        this.elementName = val;
    }

    @Lob
    @Column(name = "jskeys_xml, nullable=true")
    public String getJsKeyXml() {
        if (this.jskeys_xml == null)
            return null;
        return this.jskeys_xml.intern();
    }
    public void setJsKeyXml(String val) {
        this.jskeys_xml = val;
    }

    @Lob
    @Column(name = "html")
    public String getHtml() {
        return this.html;
    }
    public void setHtml(String val) {
        this.html = val;
    }

    @Basic
    @Column(name = "alternation_priority")
    public int getAlternationPriority() {
        return this.alternationPriority;
    }

    public void setAlternationPriority(int val) {
        this.alternationPriority = val;
    }

    @Basic
    @Column(name = "initial_pos")
    public int getInitialPos() {
        return this.initialPos;
    }

    public void setInitialPos(int val) {
        this.initialPos = val;
    }

    @Basic
    @Column(name = "num_alternations")
    public int getNumAlternations() {
        return this.numAlternations;
    }

    public void setNumAlternations(int val) {
        this.numAlternations = val;
    }
    
    @Basic
    @Column(name="iat")
    public boolean isIat() {
        return this.iat;
    }
    public void setIat(boolean val) {
        this.iat = val;
    }
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=net.iatsoftware.iat.entities.DynamicSpecifier.class, mappedBy="testSegment")
    public Set<DynamicSpecifier> getDynamicSpecifiers() {
        return this.dynamicSpecifiers;
    }
    public void setDynamicSpecifiers(Set<DynamicSpecifier> val) {
        this.dynamicSpecifiers = val;
    }
}
