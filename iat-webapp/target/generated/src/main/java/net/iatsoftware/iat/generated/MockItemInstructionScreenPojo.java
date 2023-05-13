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
import net.iatsoftware.iat.configfile.IATInstructionScreen;


/**
 * <p>Java class for MockItemInstructionScreenPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MockItemInstructionScreenPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}instruction-screen-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="LeftResponseDisplayID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RightResponseDisplayID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="StimulusDisplayID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="InstructionsDisplayID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ErrorMarkIsDisplayed" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="OutlineLeftResponse" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="OutlineRightResponse" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MockItemInstructionScreenPojo", propOrder = {
    "leftResponseDisplayID",
    "rightResponseDisplayID",
    "stimulusDisplayID",
    "instructionsDisplayID",
    "errorMarkIsDisplayed",
    "outlineLeftResponse",
    "outlineRightResponse"
})
public class MockItemInstructionScreenPojo
    extends IATInstructionScreen
{

    @XmlElement(name = "LeftResponseDisplayID")
    protected int leftResponseDisplayID;
    @XmlElement(name = "RightResponseDisplayID")
    protected int rightResponseDisplayID;
    @XmlElement(name = "StimulusDisplayID")
    protected int stimulusDisplayID;
    @XmlElement(name = "InstructionsDisplayID")
    protected int instructionsDisplayID;
    @XmlElement(name = "ErrorMarkIsDisplayed")
    protected boolean errorMarkIsDisplayed;
    @XmlElement(name = "OutlineLeftResponse")
    protected boolean outlineLeftResponse;
    @XmlElement(name = "OutlineRightResponse")
    protected boolean outlineRightResponse;

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
     * Gets the value of the stimulusDisplayID property.
     * 
     */
    public int getStimulusDisplayID() {
        return stimulusDisplayID;
    }

    /**
     * Sets the value of the stimulusDisplayID property.
     * 
     */
    public void setStimulusDisplayID(int value) {
        this.stimulusDisplayID = value;
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

    /**
     * Gets the value of the errorMarkIsDisplayed property.
     * 
     */
    public boolean isErrorMarkIsDisplayed() {
        return errorMarkIsDisplayed;
    }

    /**
     * Sets the value of the errorMarkIsDisplayed property.
     * 
     */
    public void setErrorMarkIsDisplayed(boolean value) {
        this.errorMarkIsDisplayed = value;
    }

    /**
     * Gets the value of the outlineLeftResponse property.
     * 
     */
    public boolean isOutlineLeftResponse() {
        return outlineLeftResponse;
    }

    /**
     * Sets the value of the outlineLeftResponse property.
     * 
     */
    public void setOutlineLeftResponse(boolean value) {
        this.outlineLeftResponse = value;
    }

    /**
     * Gets the value of the outlineRightResponse property.
     * 
     */
    public boolean isOutlineRightResponse() {
        return outlineRightResponse;
    }

    /**
     * Sets the value of the outlineRightResponse property.
     * 
     */
    public void setOutlineRightResponse(boolean value) {
        this.outlineRightResponse = value;
    }

}
