//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.12 at 11:08:36 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.Message;
import net.iatsoftware.iat.resultdata.IATResultSetElementV2;


/**
 * <p>Java class for IATResultSetElementListPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IATResultSetElementListPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IATResultSetElement" type="{http://www.iatsoftware.net/schema}result-set-element-v2-impl" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="NumElements" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IATResultSetElementListPojo", propOrder = {
    "iatResultSetElement"
})
public class IATResultSetElementListPojo
    extends Message
{

    @XmlElement(name = "IATResultSetElement", required = true)
    protected List<IATResultSetElementV2> iatResultSetElement;
    @XmlAttribute(name = "NumElements", required = true)
    protected int numElements;

    /**
     * Gets the value of the iatResultSetElement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the iatResultSetElement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIATResultSetElement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IATResultSetElementV2 }
     * 
     * 
     */
    public List<IATResultSetElementV2> getIATResultSetElement() {
        if (iatResultSetElement == null) {
            iatResultSetElement = new ArrayList<IATResultSetElementV2>();
        }
        return this.iatResultSetElement;
    }

    /**
     * Gets the value of the numElements property.
     * 
     */
    public int getNumElements() {
        return numElements;
    }

    /**
     * Sets the value of the numElements property.
     * 
     */
    public void setNumElements(int value) {
        this.numElements = value;
    }

}
