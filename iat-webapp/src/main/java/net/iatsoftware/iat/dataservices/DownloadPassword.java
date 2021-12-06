/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.dataservices;

/**
 *
 * @author Michael Janda too
 */

import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DownloadPassword {
    private static Base64.Encoder encoder = Base64.getEncoder();
    private String downloadPassword;
    
    private boolean isAlphaNum(String str) {
        Pattern regEx = Pattern.compile("^[0-9A-Za-z]{20}$");
        Matcher match = regEx.matcher(str);
        if (match.matches()) {
            return true;
        }
        return false;
    }

    public String generateDownloadPassword() {
        byte[] downloadPassData = new byte[15];
        Random rand = new Random();
        String pass = "";
        do {
            rand.nextBytes(downloadPassData);
            pass = encoder.encodeToString(downloadPassData);
        } while (!isAlphaNum(pass));
        this.downloadPassword = pass;
        return pass;
    }
    
    public String getDownloadPassword() {
        return this.downloadPassword;
    }
}
