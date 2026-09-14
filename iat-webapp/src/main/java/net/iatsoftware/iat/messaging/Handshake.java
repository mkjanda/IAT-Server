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
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "Handshake")
@XmlAccessorType(XmlAccessType.NONE)
public class Handshake extends net.iatsoftware.iat.generated.GHandshake implements java.io.Serializable {
    private static final long serialVersionUID = 1L;


}
