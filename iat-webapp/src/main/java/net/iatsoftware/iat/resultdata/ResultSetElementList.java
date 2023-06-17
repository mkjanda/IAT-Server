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

import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name="IATResultSetElementList")
@XmlAccessorType(XmlAccessType.NONE)
public class ResultSetElementList extends net.iatsoftware.iat.generated.GIATResultSetElementList {
    
    @Override
    public boolean doBeforeMarshal(Marshaller m) {
        this.numElements = this.getIATResultSetElement().size();
        return true;
    }
}
