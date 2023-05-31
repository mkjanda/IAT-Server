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


import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;


@XmlRootElement(name="RequestHandshake")
@XmlAccessorType(XmlAccessType.NONE)
public class RequestHandshake extends net.iatsoftware.iat.generated.RequestHandshakePojo {
    public RequestHandshake() {}
}
