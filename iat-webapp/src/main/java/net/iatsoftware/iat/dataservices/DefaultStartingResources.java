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

import org.springframework.stereotype.Component;

@Component("StartingResources")
public class DefaultStartingResources implements StartingResources {
    private static final Integer numActivations = 100, numDownloads = 100, numIATs = 1, diskSpace = 15, numAdministrations = 500;
    
    @Override
    public Integer getNumAdministrations() {
        return numAdministrations;
    }
    
    @Override
    public Integer getNumIATs() {
        return numIATs;
    }
    
    @Override
    public Integer getDiskSpace() {
        return diskSpace;
    }
    
    @Override
    public Integer getDownloads() {
        return numDownloads;
    }
    
    @Override
    public Integer getActivations() {
        return numActivations;
    }
}
