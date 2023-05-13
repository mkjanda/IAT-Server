//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.12 at 11:08:36 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.configfile.IATEvent;


/**
 * <p>Java class for BeginIATBlockPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BeginIATBlockPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}iat-event-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NumPresentations" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="AlternatedWith" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="BlockNum" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="NumItems" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="PracticeBlock" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="InstructionsDisplayID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="LeftResponseDisplayID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RightResponseDisplayID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BeginIATBlockPojo", propOrder = {
    "numPresentations",
    "alternatedWith",
    "blockNum",
    "numItems",
    "practiceBlock",
    "instructionsDisplayID",
    "leftResponseDisplayID",
    "rightResponseDisplayID"
})
public class BeginIATBlockPojo
    extends IATEvent
{

    @XmlElement(name = "NumPresentations")
    protected int numPresentations;
    @XmlElement(name = "AlternatedWith")
    protected int alternatedWith;
    @XmlElement(name = "BlockNum")
    protected int blockNum;
    @XmlElement(name = "NumItems")
    protected int numItems;
    @XmlElement(name = "PracticeBlock")
    protected boolean practiceBlock;
    @XmlElement(name = "InstructionsDisplayID")
    protected int instructionsDisplayID;
    @XmlElement(name = "LeftResponseDisplayID")
    protected int leftResponseDisplayID;
    @XmlElement(name = "RightResponseDisplayID")
    protected int rightResponseDisplayID;

    /**
     * Gets the value of the numPresentations property.
     * 
     */
    public int getNumPresentations() {
        return numPresentations;
    }

    /**
     * Sets the value of the numPresentations property.
     * 
     */
    public void setNumPresentations(int value) {
        this.numPresentations = value;
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

    /**
     * Gets the value of the blockNum property.
     * 
     */
    public int getBlockNum() {
        return blockNum;
    }

    /**
     * Sets the value of the blockNum property.
     * 
     */
    public void setBlockNum(int value) {
        this.blockNum = value;
    }

    /**
     * Gets the value of the numItems property.
     * 
     */
    public int getNumItems() {
        return numItems;
    }

    /**
     * Sets the value of the numItems property.
     * 
     */
    public void setNumItems(int value) {
        this.numItems = value;
    }

    /**
     * Gets the value of the practiceBlock property.
     * 
     */
    public boolean isPracticeBlock() {
        return practiceBlock;
    }

    /**
     * Sets the value of the practiceBlock property.
     * 
     */
    public void setPracticeBlock(boolean value) {
        this.practiceBlock = value;
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

}