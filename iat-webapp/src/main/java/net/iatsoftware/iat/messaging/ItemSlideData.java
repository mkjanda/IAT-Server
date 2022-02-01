package net.iatsoftware.iat.messaging;

import net.iatsoftware.iat.generated.ItemSlideDataPojo;

public class ItemSlideData extends ItemSlideDataPojo implements java.io.Serializable {
    private static final long serialVersionUID = 1;    

    public ItemSlideData() {
        this.sessionId = "";
        this.deploymentId = 0;
    }

    public ItemSlideData(String sessionId, Long deploymentId) {
        this.sessionId = sessionId;
        this.deploymentId = deploymentId;
    }
}
