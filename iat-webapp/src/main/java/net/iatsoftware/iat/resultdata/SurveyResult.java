/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultdata;

/**
 *
 * @author Michael Janda
 */

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;      

@XmlAccessorType(XmlAccessType.NONE)
public class SurveyResult {
    private List<String> answers = new ArrayList<>();
    
    public SurveyResult(List<String> answers)
    {
        this.answers.addAll(answers);
    }
    
    @XmlElement(name="answers")
    public List<String> getAnswers() {
        return this.answers;
    }
}
