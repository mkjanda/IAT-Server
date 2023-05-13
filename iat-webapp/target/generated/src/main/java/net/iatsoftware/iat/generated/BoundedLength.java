//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.12 at 11:08:36 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BoundedLength complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BoundedLength"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Format" type="{http://www.iatsoftware.net/schema}Format"/&gt;
 *         &lt;element name="MinLength" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="MaxLength" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Type" use="required" type="{http://www.iatsoftware.net/schema}ResponseType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BoundedLength", propOrder = {
    "format",
    "minLength",
    "maxLength"
})
public class BoundedLength {

    @XmlElement(name = "Format", required = true)
    protected Format format;
    @XmlElement(name = "MinLength")
    protected int minLength;
    @XmlElement(name = "MaxLength")
    protected int maxLength;
    @XmlAttribute(name = "Type", required = true)
    protected ResponseType type;

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link Format }
     *     
     */
    public Format getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link Format }
     *     
     */
    public void setFormat(Format value) {
        this.format = value;
    }

    /**
     * Gets the value of the minLength property.
     * 
     */
    public int getMinLength() {
        return minLength;
    }

    /**
     * Sets the value of the minLength property.
     * 
     */
    public void setMinLength(int value) {
        this.minLength = value;
    }

    /**
     * Gets the value of the maxLength property.
     * 
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the value of the maxLength property.
     * 
     */
    public void setMaxLength(int value) {
        this.maxLength = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseType }
     *     
     */
    public ResponseType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseType }
     *     
     */
    public void setType(ResponseType value) {
        this.type = value;
    }

}