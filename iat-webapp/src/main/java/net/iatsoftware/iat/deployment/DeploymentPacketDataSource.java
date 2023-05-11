/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.deployment;

/**
 *
 * @author Michael Janda
 */
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.iatsoftware.iat.services.PacketDataSource;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URI;

public class DeploymentPacketDataSource implements PacketDataSource {

    private static final Logger logger = LogManager.getLogger();
    private final URI dataSource;
    private boolean isOpen = false;
    private final long expectedLength;
    private RandomAccessFile dataFile = null;
    private boolean halted = false;

    public DeploymentPacketDataSource(URI dataSource, long expectedLength) {
        this.dataSource = dataSource;
        this.expectedLength = expectedLength;
    }

    @Override
    public void halt() {
        halted = true;
    }
    
    @Override
    public boolean isHalted() {
        return this.halted;
    }
    
    @Override
    public byte[] getMoreData() {
        try {
            if (!isOpen) {
                File f = new File(dataSource);
                if (!f.exists()) {
                    return null;
                }
                if (f.length() < expectedLength) {
                    return null;
                }
                this.dataFile = new RandomAccessFile(f, "r");
                isOpen = true;
            }
            byte[] data;
            if (dataFile.getFilePointer() + 65536L < dataFile.length()) {
                data = new byte[65536];
                this.dataFile.readFully(data);
            } else {
                data = new byte[(int) (dataFile.length() - dataFile.getFilePointer())];
                this.dataFile.readFully(data);
                dataFile.close();
                File deleted = new File(dataSource);
                deleted.delete();
            }
            if (halted)
                return null;
            return data;
        } catch (java.io.IOException ex) {
            logger.error("Error processing deployment file", ex);
            return null;
        }
    }
}
