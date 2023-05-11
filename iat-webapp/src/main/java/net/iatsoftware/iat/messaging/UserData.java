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
import javax.xml.bind.annotation.XmlType;

@XmlType(name="UserData")
@XmlAccessorType(XmlAccessType.NONE)
public class UserData extends net.iatsoftware.iat.generated.UserDataPojo {
    public UserData() {}
    
    public UserData(User u) {
        this.setEMail(u.getEMail());
        this.setFName(u.getFName());
        this.setLName(u.getLName());
        this.setTitle(u.getTitle());
    }
}
