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

import net.iatsoftware.iat.resultdata.IATResultSetElement;
import net.iatsoftware.iat.resultdata.ResultSetElementList;
import net.iatsoftware.iat.entities.TestSegment;

import org.springframework.context.annotation.Scope;
import org.springframework.oxm.Marshaller;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.inject.Inject;
import javax.xml.transform.stream.StreamResult;


@Component(value="DefaultIATResultRecorder")
@Scope(value="prototype")
public class DefaultIATResultRecorder extends ResultRecorder implements IATResultRecorder {
    @Inject Marshaller marshaller;
    private Map<String, String> responseData;
    private ResultSetElementList resultData;
    private int numItems;
    private long adminID;
    private String testElem;
    
    public DefaultIATResultRecorder()
    {
        super(null, false);
    }
    
    
    public DefaultIATResultRecorder(TestSegment testSeg, Long adminID, int numItems, Map<String, String> responseData, boolean lastFragment) {
        super(testSeg.getTest(), lastFragment);
        this.adminID = adminID;
        this.responseData = responseData;
        this.numItems = numItems;
        this.testElem = testSeg.getElemName();
    }

    private void parseResults() {
        this.resultData = new ResultSetElementList();
        try {
        for (int ctr = 0; ctr < numItems; ctr++) {
            int itemNum = Integer.parseInt(responseData.get("Item" + Integer.toString(ctr)));
            long latency = Long.parseLong(responseData.get("Latency" + Integer.toString(ctr)));
            int block = Integer.parseInt(responseData.get("Block" + Integer.toString(ctr)));
            boolean error = Boolean.parseBoolean(responseData.get("Error" + Integer.toString(ctr)));
            this.resultData.getIATResultSetElement().add(new IATResultSetElement(itemNum, block, latency, ctr + 1, error));
        }
        }
        catch (NumberFormatException ex) {
            logger.error("Error parsing IAT result data", ex);
        }
    }
    
    @Async
    @Override
    public void run() {
        try {
            parseResults();
            StringWriter sWriter = new StringWriter();
            StreamResult sResult = new StreamResult(sWriter);
            marshaller.marshal(this.resultData, sResult);
            sWriter.flush();
            encryptResults(sWriter.toString().getBytes(StandardCharsets.UTF_8));
            iatRepositoryManager.storeResultFragment(adminID, testElem, getEncDESBytes(), getEncIVBytes(), getEncData(), lastFragment);
        }
        catch (java.io.IOException ex) {
            logger.error("Error recording result fragment", ex);
        }
    }
}
