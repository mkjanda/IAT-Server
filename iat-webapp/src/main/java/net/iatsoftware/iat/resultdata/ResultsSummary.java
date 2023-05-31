/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultdata;


/**
 *
 * @author michael
 */

import net.iatsoftware.iat.controllers.RestException;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class ResultsSummary extends DScore {
    private List<SurveyResult> surveyResults = new ArrayList<>();
    
    public ResultsSummary(){}
    
    @Override
    public void compute(byte[] resultData, int iatPos) throws RestException {
        super.compute(resultData, iatPos);
        this.surveyResults = new ArrayList<>();
        for (int ctr = 0; ctr < this.toc.getResultTOCEntry().size(); ctr++) {
            if (ctr != iatPos) {
                SurveyResponseSet srs = decryptSurveyResults(resultData, ctr);
                this.surveyResults.add(new SurveyResult(srs.getSurveyResults()));
            }
        }
    }
    
    @XmlElement(name="surveyResults")
    public List<SurveyResult> getSurveyResults() {
        return this.surveyResults;
    }
}
