//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.07.10 at 04:13:23 AM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.configfile.IATEvent;


/**
 * <p>Java class for GBeginInstructionBlock complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GBeginInstructionBlock"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}iat-event-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NumInstructionScreens" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="AlternatedWith" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GBeginInstructionBlock", propOrder = {
    "numInstructionScreens",
    "alternatedWith"
})
public class GBeginInstructionBlock
    extends IATEvent
{

    @XmlElement(name = "NumInstructionScreens")
    protected int numInstructionScreens;
    @XmlElement(name = "AlternatedWith")
    protected int alternatedWith;

    /**
     * Gets the value of the numInstructionScreens property.
     * 
     */
    public int getNumInstructionScreens() {
        return numInstructionScreens;
    }

    /**
     * Sets the value of the numInstructionScreens property.
     * 
     */
    public void setNumInstructionScreens(int value) {
        this.numInstructionScreens = value;
    }

    /**
     * Gets the value of the alternatedWith property.
     * 
     */
    public int getAlternatedWith() {
        return alternatedWith;
    }

    /**
     * Sets the value of the alternatedWith property.
     * 
     */
    public void setAlternatedWith(int value) {
        this.alternatedWith = value;
    }

}
