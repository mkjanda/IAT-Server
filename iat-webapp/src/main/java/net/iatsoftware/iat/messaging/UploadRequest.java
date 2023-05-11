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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="UploadRequest")
@XmlAccessorType(XmlAccessType.NONE)
public class UploadRequest extends net.iatsoftware.iat.generated.UploadRequestPojo {
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
