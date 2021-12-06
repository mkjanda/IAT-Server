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
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Index;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name="unique_response_items", indexes = @Index(name="test_id_index", columnList="TestID, survey_name"))
public class UniqueResponseItem implements java.io.Serializable {
    private long id;
    private IAT test;
    private boolean additive;
    private String surveyName;
    private int itemNum;
    private Set<UniqueResponse> uniqueResponses;
    
    @Id
    @Column(name="UniqueResponseItemID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public long getId() {
        return this.id;
    }
    public void setId(long val) {
        this.id = val;
    }
    
    @OneToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="TestID")
    public IAT getTest() {
        return this.test;
    }
    public void setTest(IAT val) {
        this.test = val;
    }
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="item")
    public Set<UniqueResponse> getUniqueResponses() {
        return this.uniqueResponses;
    }
    public void setUniqueResponses(Set<UniqueResponse> val) {
        this.uniqueResponses = val;
    }
    
    @Basic
    @Column(name="additive")
    public boolean getAdditive()
    {
        return this.additive;
    }
    public void setAdditive(boolean val) {
        this.additive = val;
    }
    
    @Basic
    @Column(name="survey_name")
    public String getSurveyName() {
        return this.surveyName;
    }
    public void setSurveyName(String val) {
        this.surveyName = val;
    }
            
    @Basic
    @Column(name="item_num")
    public int getItemNum() {
        return this.itemNum;
    }
    public void setItemNum(int val) {
        this.itemNum = val;
    }
}
