//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.13 at 12:28:08 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.configfile.IATEvent;


/**
 * <p>Java class for IATInstructionScreenPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IATInstructionScreenPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}iat-event-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ContinueASCIIKeyCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ContinueInstructionsID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IATInstructionScreenPojo", propOrder = {
    "continueASCIIKeyCode",
    "continueInstructionsID"
})
public abstract class IATInstructionScreenPojo
    extends IATEvent
{

    @XmlElement(name = "ContinueASCIIKeyCode")
    protected int continueASCIIKeyCode;
    @XmlElement(name = "ContinueInstructionsID")
    protected int continueInstructionsID;

    /**
     * Gets the value of the continueASCIIKeyCode property.
     * 
     */
    public int getContinueASCIIKeyCode() {
        return continueASCIIKeyCode;
    }

    /**
     * Sets the value of the continueASCIIKeyCode property.
     * 
     */
    public void setContinueASCIIKeyCode(int value) {
        this.continueASCIIKeyCode = value;
    }

    /**
     * Gets the value of the continueInstructionsID property.
     * 
     */
    public int getContinueInstructionsID() {
        return continueInstructionsID;
    }

    /**
     * Sets the value of the continueInstructionsID property.
     * 
     */
    public void setContinueInstructionsID(int value) {
        this.continueInstructionsID = value;
    }

}
