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

import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name="IATResultSetElementList")
@XmlAccessorType(XmlAccessType.NONE)
public class ResultSetElementList extends net.iatsoftware.iat.generated.IATResultSetElementListPojo {
    
    @Override
    public boolean doBeforeMarshal(Marshaller m) {
        this.numElements = this.getIATResultSetElement().size();
        return true;
    }
}
