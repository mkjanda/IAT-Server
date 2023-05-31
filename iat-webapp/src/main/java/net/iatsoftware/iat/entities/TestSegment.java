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
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Lob;
import jakarta.persistence.Index;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "test_segments", indexes = {
    @Index(name = "tests_id", columnList = "TestId")
})
public class TestSegment implements java.io.Serializable {

    private long id;
    private IAT test;
    private String elementName;
    private String html, jskeys_xml;
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
        if (elementName.equals(test.getTestName())) {
            this.iat = true;
        }
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
    @Column(name = "jskeys_xml", nullable=true)
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
}
