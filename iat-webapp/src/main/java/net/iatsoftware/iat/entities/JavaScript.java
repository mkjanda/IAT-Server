package net.iatsoftware.iat.entities;

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.IAT;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import javax.persistence.Table;


@Entity
@Table(name="javascript")
public class JavaScript implements java.io.Serializable {
    final static long uSerialVersionID = 1;
    private String script;
    private long id;
    private IAT test;
    private int indexInTest;        

    public JavaScript(){}

    public JavaScript(IAT test, int index, String script) {
        this.test = test;
        this.indexInTest = index;
        this.script = script;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getJavaScriptId() {
        return id;
    }
    public void setJavaScriptId(long val) {
        id = val;
    }

    @ManyToOne(fetch=FetchType.EAGER, optional = false )
    @JoinColumn(name="test_id", referencedColumnName="TestID")
    public IAT getTest() {
        return test;
    }
    public void setClient(IAT val) {
        test = val;
    }

    @Basic
    @Column(name="IndexInTest", nullable = false)
    public int getIndexInTest() {
        return indexInTest;
    }
    public void setIndexInTest(int val) {
        indexInTest = val;
    }

    @Lob
    @Column(name="script")
    public String getScript() {
        return script;
    }
    public void setScript(String val) {
        script = val;
    }



}
