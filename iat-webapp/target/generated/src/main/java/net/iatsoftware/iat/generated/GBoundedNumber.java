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


/**
 * <p>Java class for GBoundedNumber complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GBoundedNumber"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Format" type="{http://www.iatsoftware.net/schema}GFormat"/&gt;
 *         &lt;element name="MinValue" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="MaxValue" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GBoundedNumber", propOrder = {
    "format",
    "minValue",
    "maxValue"
})
public class GBoundedNumber {

    @XmlElement(name = "Format", required = true)
    protected GFormat format;
    @XmlElement(name = "MinValue")
    protected int minValue;
    @XmlElement(name = "MaxValue")
    protected int maxValue;

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link GFormat }
     *     
     */
    public GFormat getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link GFormat }
     *     
     */
    public void setFormat(GFormat value) {
        this.format = value;
    }

    /**
     * Gets the value of the minValue property.
     * 
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * Sets the value of the minValue property.
     * 
     */
    public void setMinValue(int value) {
        this.minValue = value;
    }

    /**
     * Gets the value of the maxValue property.
     * 
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the value of the maxValue property.
     * 
     */
    public void setMaxValue(int value) {
        this.maxValue = value;
    }

}