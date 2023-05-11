/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultretrieval;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.services.PacketDataSource;

import java.io.File;
import java.io.FileOutputStream;

public class ItemSlidePacketQueue  {
    private final File outputFile;

    public ItemSlidePacketQueue(File f) {
        this.outputFile = f;
    }
    
    public void queueData(PacketDataSource pSource) 
            throws java.io.IOException {
        try (FileOutputStream fOut = new FileOutputStream(this.outputFile)) {
            byte []data = null;
            while ((data = pSource.getMoreData()) != null)
                fOut.write(data);
        }
        catch (java.io.IOException ex) {
            throw ex;
        }
    }
}
