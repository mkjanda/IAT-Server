/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.configfile;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.generated.IATEventType;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="BeginIATBlock")
@XmlAccessorType(XmlAccessType.NONE)
public class BeginIATBlock extends net.iatsoftware.iat.generated.GBeginIATBlock {
    public BeginIATBlock()
    {
        this.eventType = IATEventType.BEGIN_IAT_BLOCK;
    }

    private boolean instrSet = false, leftRespSet = false, rightRespSet = false;

    @Override
    public boolean setResource(int id, int resourceId) {
        if ((this.instructionsDisplayID == id) && !instrSet) {
            this.instructionsDisplayID = resourceId;
            instrSet = true;
        }
        else if ((this.leftResponseDisplayID == id) && !leftRespSet) {
            this.leftResponseDisplayID = resourceId;
            leftRespSet = true;
        }
        else if ((this.rightResponseDisplayID == id) && !rightRespSet) {
            this.rightResponseDisplayID = resourceId;
            rightRespSet = true;
        }
        else
            return false;
        return true;
    }
}
