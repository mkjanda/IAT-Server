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

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="SurveyResponse")
@XmlAccessorType(XmlAccessType.NONE)
public class SurveyResponseSet extends net.iatsoftware.iat.generated.SurveyResponseSetPojo {
    public SurveyResponseSet(){}
  
    public SurveyResponseSet(String surveyName, List<String> responses)
    {
        this.surveyName = surveyName;
        this.numSurveyResults = responses.size();
        this.surveyResults = new ArrayList<>(responses);
    }
}
