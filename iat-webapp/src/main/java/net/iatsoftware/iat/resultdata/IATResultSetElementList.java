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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;
import java.util.ArrayList;

@XmlRootElement(name="IATResultSetElementList")
public class IATResultSetElementList {
    @XmlAttribute(name="NumElements")
    private int numElements;
    
    @XmlElements(
    @XmlElement)
    private List<IATResultSetElement> resultElements;
    
    public IATResultSetElementList() {}
    
    public IATResultSetElementList(List<IATResultSetElement> elems) {
        resultElements = new ArrayList<>(elems);
        numElements = resultElements.size();
    }
}
