//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.17 at 05:48:02 AM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.configfile.IATEvent;


/**
 * <p>Java class for GEndIATBlock complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GEndIATBlock"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}iat-event-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DummyValue" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GEndIATBlock", propOrder = {
    "dummyValue"
})
public class GEndIATBlock
    extends IATEvent
{

    @XmlElement(name = "DummyValue", required = true)
    protected String dummyValue;

    /**
     * Gets the value of the dummyValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDummyValue() {
        return dummyValue;
    }

    /**
     * Sets the value of the dummyValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDummyValue(String value) {
        this.dummyValue = value;
    }

}