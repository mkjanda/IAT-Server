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

import net.iatsoftware.iat.entities.User;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.NONE)
public class IATListEntry extends net.iatsoftware.iat.generated.IATListEntryPojo {
    public IATListEntry() {
    }

    public IATListEntry(String testName, User ui) {
        iatName = testName;
        userInfo = ui;
    }
}
