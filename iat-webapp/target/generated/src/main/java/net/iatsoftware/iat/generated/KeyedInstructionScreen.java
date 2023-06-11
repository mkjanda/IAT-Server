//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.06 at 10:05:44 PM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.configfile.IATInstructionScreen;


/**
 * <p>Java class for KeyedInstructionScreen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KeyedInstructionScreen"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}instruction-screen-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="LeftResponseDisplayID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RightResponseDisplayID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="InstructionsDisplayID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeyedInstructionScreen", propOrder = {
    "leftResponseDisplayID",
    "rightResponseDisplayID",
    "instructionsDisplayID"
})
public class KeyedInstructionScreen
    extends IATInstructionScreen
{

    @XmlElement(name = "LeftResponseDisplayID")
    protected int leftResponseDisplayID;
    @XmlElement(name = "RightResponseDisplayID")
    protected int rightResponseDisplayID;
    @XmlElement(name = "InstructionsDisplayID")
    protected int instructionsDisplayID;

    /**
     * Gets the value of the leftResponseDisplayID property.
     * 
     */
    public int getLeftResponseDisplayID() {
        return leftResponseDisplayID;
    }

    /**
     * Sets the value of the leftResponseDisplayID property.
     * 
     */
    public void setLeftResponseDisplayID(int value) {
        this.leftResponseDisplayID = value;
    }

    /**
     * Gets the value of the rightResponseDisplayID property.
     * 
     */
    public int getRightResponseDisplayID() {
        return rightResponseDisplayID;
    }

    /**
     * Sets the value of the rightResponseDisplayID property.
     * 
     */
    public void setRightResponseDisplayID(int value) {
        this.rightResponseDisplayID = value;
    }

    /**
     * Gets the value of the instructionsDisplayID property.
     * 
     */
    public int getInstructionsDisplayID() {
        return instructionsDisplayID;
    }

    /**
     * Sets the value of the instructionsDisplayID property.
     * 
     */
    public void setInstructionsDisplayID(int value) {
        this.instructionsDisplayID = value;
    }

}
