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

import net.iatsoftware.iat.messaging.AjaxResponse;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import javax.inject.Inject;

@Component
@Scope(value="prototype")
public class CodeResult extends DeferredResult<AjaxResponse> implements Runnable {
    private final Long testSegmentID;

    @Inject IATRepositoryManager iatRepositoryManager;
    
    public CodeResult(Long tsID) {
        this.testSegmentID = tsID;
    }
    
    @Override
    public void run() {
        this.setResult(new AjaxResponse(iatRepositoryManager.getOrderedCodeLines(testSegmentID)));
    }
}
