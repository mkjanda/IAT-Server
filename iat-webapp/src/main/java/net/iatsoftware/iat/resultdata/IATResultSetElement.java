/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultdata;

/**
 *
 * @author Michael Janda
 */

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="IATResultSetElement")
@XmlAccessorType(XmlAccessType.NONE)
public class IATResultSetElement extends IATResultSetElementV2 {
    public IATResultSetElement() {}
    
    public IATResultSetElement(int item, int block, long latency, int presentationNum, boolean error) {
        super(item, block, latency, presentationNum, error);
    }
}
