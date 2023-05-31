/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.admin;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.entities.DynamicSpecifier;
import net.iatsoftware.iat.entities.TestSegment;
import net.iatsoftware.iat.resultdata.SurveyResponseSet;

import org.springframework.context.annotation.Scope;
import org.springframework.oxm.Marshaller;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import java.util.stream.IntStream;
import javax.xml.transform.stream.StreamResult;

@Component(value="DefaultSurveyResultRecorder")
@Scope(value = "prototype")
public class DefaultSurveyResultRecorder extends ResultRecorder implements SurveyResultRecorder {

    @Inject
    Marshaller marshaller;

    private int numItems;
    private long adminID;
    private String testElem;
    private final Map<String, String> resultData;
    
    public DefaultSurveyResultRecorder() {
        super(null, false);
        resultData = null;
    }

    public DefaultSurveyResultRecorder(TestSegment testSeg, long adminID, int numItems, Map<String, String> resultMap, boolean lastFragment) {
        super(testSeg.getTest(), lastFragment);
        this.adminID = adminID;
        this.numItems = numItems;
        this.testElem = testSeg.getElemName();
        this.resultData = new HashMap<>();
        for (String key : resultMap.keySet())
            resultData.put(key, resultMap.get(key));
    }

    private SurveyResponseSet parseResults() {
        final List<String> responses = new ArrayList<>();
        IntStream.rangeClosed(1, numItems).forEach((ndx) -> {
            final String resp = resultData.get("Item" + Integer.toString(ndx));
            responses.add(resp);
        });
        return new SurveyResponseSet(testElem, responses);
    }

    @Async
    @Override
    public void run() {
        try {
            StringWriter sWriter = new StringWriter();
            StreamResult sResult = new StreamResult(sWriter);
            marshaller.marshal(parseResults(), sResult);
            sWriter.flush();
            encryptResults(sWriter.toString().getBytes("UTF-8"));
            iatRepositoryManager.storeResultFragment(this.adminID, testElem, getEncDESBytes(), getEncIVBytes(), getEncData(), this.lastFragment);
        } catch (java.io.IOException ex) {
            logger.error("Error storing survey result fragment", ex);
        }
    }
}
