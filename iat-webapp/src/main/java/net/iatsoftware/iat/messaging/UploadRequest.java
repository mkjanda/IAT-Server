/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

/**
 *
 * @author Michael Janda
 */

import java.util.Base64;
import java.util.Random;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="UploadRequest")
@XmlAccessorType(XmlAccessType.NONE)
public class UploadRequest extends net.iatsoftware.iat.generated.GUploadRequest {
    private static final Base64.Encoder encoder = Base64.getEncoder();
    
    public UploadRequest() {}
    
    public UploadRequest(long deploymentID) {
        this.deploymentID = deploymentID;
        Random rand = new Random();
        byte []data = new byte[12];
        rand.nextBytes(data);
        this.dataUploadKey = encoder.encodeToString(data).replace('+', 'A').replace('/', 'B').replace('=', 'C');
        rand.nextBytes(data);
        this.itemSlideUploadKey = encoder.encodeToString(data).replace('+', 'A').replace('/', 'B').replace('=', 'C');
        rand.nextBytes(data);
        this.reconnectionKey = encoder.encodeToString(data).replace('+', 'A').replace('/', 'B').replace('=', 'C');
    }
    
    public UploadRequest(long deploymentID, String dataUploadKey, String itemSlideUploadKey, String reconnectionKey) {
        this.deploymentID = deploymentID;
        this.dataUploadKey = dataUploadKey;
        this.itemSlideUploadKey = itemSlideUploadKey;
        this.reconnectionKey = reconnectionKey;
    }
}
