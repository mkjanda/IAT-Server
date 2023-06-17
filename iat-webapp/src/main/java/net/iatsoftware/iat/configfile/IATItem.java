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

@XmlRootElement(name="IATItem")
@XmlAccessorType(XmlAccessType.NONE)
public class IATItem extends net.iatsoftware.iat.generated.GIATItem {
    public IATItem() {
        this.eventType = IATEventType.IAT_ITEM;
    }

    private boolean instrSet = false;

    @Override
    public boolean setResource(int id, int resourceId) {
        if ((this.stimulusDisplayID == id) && !instrSet) {
            this.stimulusDisplayID = resourceId;
            instrSet = true;
        }
        else
            return false;
        return true;
    }
}
