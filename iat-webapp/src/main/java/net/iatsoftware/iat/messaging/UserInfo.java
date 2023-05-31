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

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.NONE)
public class UserInfo extends net.iatsoftware.iat.generated.UserInfoPojo {
    public UserInfo() {}
    
    public UserInfo(String title, String fname, String lname, String email)
    {
        this.fName = fname;
        this.lName = lname;
        this.title = title;
        this.eMail = email;
    }
}
