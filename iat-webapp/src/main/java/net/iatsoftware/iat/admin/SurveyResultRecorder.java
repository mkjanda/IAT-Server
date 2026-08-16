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

import java.util.Map;

public interface SurveyResultRecorder extends Runnable {
    void setNumItems(int numItems);
    void setResponseData(Map<String, String> responseData);
    void setTestSegment(net.iatsoftware.iat.entities.TestSegment testSeg);
    void setAdminID(Long adminID);   
    void setLastFragment(boolean val);
}
