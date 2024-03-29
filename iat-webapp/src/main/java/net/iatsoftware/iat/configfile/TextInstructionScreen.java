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

@XmlRootElement(name="TextInstructionScreen")
@XmlAccessorType(XmlAccessType.NONE)
public class TextInstructionScreen extends net.iatsoftware.iat.generated.GTextInstructionScreen {
    public TextInstructionScreen() {
        this.eventType = IATEventType.TEXT_INSTRUCTION_SCREEN;
    }

    private boolean instrSet = false, continueSet = false;


    @Override
    public boolean setResource(int id, int resourceId) {
        if ((this.instructionsDisplayID == id) && !instrSet) {
            this.instructionsDisplayID = resourceId;
            instrSet = true;
        } else if ((this.continueInstructionsID == id) && !continueSet) {
            this.continueInstructionsID = resourceId;
            continueSet = true;
        }
        else
            return false;
        return true;
    }
}
