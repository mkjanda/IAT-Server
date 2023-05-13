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
 * <p>Java class for IATItemPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IATItemPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}iat-event-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ItemNum" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="BlockNum" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="StimulusDisplayID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="OriginatingBlock" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="KeyedDir"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="Left"/&gt;
 *               &lt;enumeration value="Right"/&gt;
 *               &lt;enumeration value="DynamicLeft"/&gt;
 *               &lt;enumeration value="DynamicRight"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IATItemPojo", propOrder = {
    "itemNum",
    "blockNum",
    "stimulusDisplayID",
    "originatingBlock",
    "keyedDir"
})
public class IATItemPojo
    extends IATEvent
{

    @XmlElement(name = "ItemNum")
    protected int itemNum;
    @XmlElement(name = "BlockNum")
    protected int blockNum;
    @XmlElement(name = "StimulusDisplayID")
    protected int stimulusDisplayID;
    @XmlElement(name = "OriginatingBlock")
    protected int originatingBlock;
    @XmlElement(name = "KeyedDir", required = true)
    protected String keyedDir;

    /**
     * Gets the value of the itemNum property.
     * 
     */
    public int getItemNum() {
        return itemNum;
    }

    /**
     * Sets the value of the itemNum property.
     * 
     */
    public void setItemNum(int value) {
        this.itemNum = value;
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
     * Gets the value of the originatingBlock property.
     * 
     */
    public int getOriginatingBlock() {
        return originatingBlock;
    }

    /**
     * Sets the value of the originatingBlock property.
     * 
     */
    public void setOriginatingBlock(int value) {
        this.originatingBlock = value;
    }

    /**
     * Gets the value of the keyedDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyedDir() {
        return keyedDir;
    }

    /**
     * Sets the value of the keyedDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyedDir(String value) {
        this.keyedDir = value;
    }

}
