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

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;      

@XmlRootElement(name="SurveyResult")
@XmlAccessorType(XmlAccessType.NONE)
public class SurveyResult extends net.iatsoftware.iat.generated.GSurveyResult {
    
    public SurveyResult() {}

    public SurveyResult(String surveyName, List<String> answers)
    {
        this.surveyName = surveyName;
        this.getAnswer().addAll(answers);
    }

    public SurveyResult(String surveyName, Map<String, String> parameters) {
        IntStream.rangeClosed(1, parameters.size()).forEach((ndx) -> {
            if (parameters.containsKey("Item" + Integer.toString(ndx))) {
                final String resp = parameters.get("Item" + Integer.toString(ndx));
                this.getAnswer().add(resp);
            }
        });
    }

    public void parseAnswers(Map<String, String> parameters) {
        IntStream.rangeClosed(1, parameters.size()).forEach((ndx) -> {
            if (parameters.containsKey("Item" + Integer.toString(ndx))) {
                final String resp = parameters.get("Item" + Integer.toString(ndx));
                this.getAnswer().add(resp);
            }
        });
    }
}
