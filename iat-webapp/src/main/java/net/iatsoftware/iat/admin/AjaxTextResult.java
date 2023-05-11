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
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import javax.inject.Inject;

import net.iatsoftware.iat.entities.TestSegment;
import net.iatsoftware.iat.messaging.AjaxResponse;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

@Component
@Scope(value = "prototype")
public class AjaxTextResult extends DeferredResult<AjaxResponse> implements Runnable {

    private static final Logger log = LogManager.getLogger();
    private Long testSegmentID;

    @Inject
    IATRepositoryManager iatRepositoryManager;

    public AjaxTextResult(Long testSegmentID) {
        this.testSegmentID = testSegmentID;
    }

    @Override
    public void run() {
        try {
            TestSegment ts = iatRepositoryManager.getTestSegmentByID(testSegmentID);
            this.setResult(new AjaxResponse(ts.getTest().getAESCode()));
        } catch (Exception ex) {
            log.error("Error retreiving AES code", ex);
            this.setResult(new AjaxResponse(""));
        }
    }
}
