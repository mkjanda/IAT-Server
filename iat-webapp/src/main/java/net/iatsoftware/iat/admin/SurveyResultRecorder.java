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
    Map<Integer, String> getSpecifierValues();
}
