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

@XmlRootElement(name = "IATEvent")
@XmlAccessorType(XmlAccessType.NONE)
public class KeyedInstructionScreen extends net.iatsoftware.iat.generated.KeyedInstructionScreen {
    public KeyedInstructionScreen() {
        this.eventType = IATEventType.KEYED_INSTRUCTION_SCREEN;
    }

    private boolean continueSet = false, leftSet = false, rightSet = false, instrSet = false;

    @Override
    public boolean setResource(int id, int resourceId) {
        if ((this.continueInstructionsID == id) && !continueSet) {
            this.continueInstructionsID = resourceId;
            continueSet = true;
        }
        else if ((this.leftResponseDisplayID == id) && !leftSet) {
            this.leftResponseDisplayID = resourceId;
            leftSet = true;
        }
        else if ((this.rightResponseDisplayID == id) && !rightSet) {
            this.rightResponseDisplayID = resourceId;
            rightSet = true;
        }
        else if ((this.instructionsDisplayID == id) && !instrSet) {
            this.instructionsDisplayID = resourceId;
            instrSet = true;
        } else
            return false;
        return true;
    }
}
