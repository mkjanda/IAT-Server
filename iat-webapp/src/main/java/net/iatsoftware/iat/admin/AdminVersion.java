/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.admin;

/**
 *
 * @author michael
 */


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminVersion {
    private int version, major, minor;
    
    public AdminVersion(String versionStr) {
        Pattern patt = Pattern.compile("iat-([1-9][0-9]*)\\.([0-9]+)\\.([0-9]+)");
        Matcher m = patt.matcher(versionStr);
        if (!m.matches()) {
            version = -1;
            major = -1;
            minor = -1;
        }
        version = Integer.parseInt(m.group(1));
        major = Integer.parseInt(m.group(2));
        minor = Integer.parseInt(m.group(3));
    }
    
    @Override
    public String toString() {
        return "iat-" + Integer.toString(version) + "." + Integer.toString(major) + "." + Integer.toString(minor);
    }
    
    public static int compare(AdminVersion v1, AdminVersion v2) {
        if (v1.version != v2.version)
            return v2.version - v1.version;
        else if (v1.major != v2.major)
            return v2.major - v1.major;
        else if (v1.minor != v2.minor)
            return v2.minor - v1.minor;
        return 0;
    }
}
