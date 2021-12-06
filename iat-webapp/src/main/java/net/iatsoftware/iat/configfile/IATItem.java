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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="IATEvent")
@XmlAccessorType(XmlAccessType.NONE)
public class IATItem extends net.iatsoftware.iat.generated.IATItemPojo {
    public IATItem() {
        this.eventType = IATEventType.IAT_ITEM;
    }
}
