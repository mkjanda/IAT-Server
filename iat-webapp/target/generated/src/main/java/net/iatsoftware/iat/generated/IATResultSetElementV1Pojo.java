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


/**
 * <p>Java class for IATResultSetElementV1Pojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IATResultSetElementV1Pojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BlockNum" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ItemNum" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ResponseTime" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="PresentationNum" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IATResultSetElementV1Pojo", propOrder = {
    "blockNum",
    "itemNum",
    "responseTime",
    "presentationNum"
})
public abstract class IATResultSetElementV1Pojo {

    @XmlElement(name = "BlockNum")
    protected int blockNum;
    @XmlElement(name = "ItemNum")
    protected int itemNum;
    @XmlElement(name = "ResponseTime")
    protected long responseTime;
    @XmlElement(name = "PresentationNum")
    protected int presentationNum;

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
     * Gets the value of the responseTime property.
     * 
     */
    public long getResponseTime() {
        return responseTime;
    }

    /**
     * Sets the value of the responseTime property.
     * 
     */
    public void setResponseTime(long value) {
        this.responseTime = value;
    }

    /**
     * Gets the value of the presentationNum property.
     * 
     */
    public int getPresentationNum() {
        return presentationNum;
    }

    /**
     * Sets the value of the presentationNum property.
     * 
     */
    public void setPresentationNum(int value) {
        this.presentationNum = value;
    }

}
