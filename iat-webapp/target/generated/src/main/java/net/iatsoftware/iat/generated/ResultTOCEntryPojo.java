//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.09 at 12:27:51 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResultTOCEntryPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResultTOCEntryPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="KeyOffset" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="KeyLength" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IVOffset" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="IVLength" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DataOffset" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="DataLength" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultTOCEntryPojo", propOrder = {
    "keyOffset",
    "keyLength",
    "ivOffset",
    "ivLength",
    "dataOffset",
    "dataLength"
})
public class ResultTOCEntryPojo {

    @XmlElement(name = "KeyOffset")
    protected long keyOffset;
    @XmlElement(name = "KeyLength")
    protected int keyLength;
    @XmlElement(name = "IVOffset")
    protected long ivOffset;
    @XmlElement(name = "IVLength")
    protected int ivLength;
    @XmlElement(name = "DataOffset")
    protected long dataOffset;
    @XmlElement(name = "DataLength")
    protected int dataLength;

    /**
     * Gets the value of the keyOffset property.
     * 
     */
    public long getKeyOffset() {
        return keyOffset;
    }

    /**
     * Sets the value of the keyOffset property.
     * 
     */
    public void setKeyOffset(long value) {
        this.keyOffset = value;
    }

    /**
     * Gets the value of the keyLength property.
     * 
     */
    public int getKeyLength() {
        return keyLength;
    }

    /**
     * Sets the value of the keyLength property.
     * 
     */
    public void setKeyLength(int value) {
        this.keyLength = value;
    }

    /**
     * Gets the value of the ivOffset property.
     * 
     */
    public long getIVOffset() {
        return ivOffset;
    }

    /**
     * Sets the value of the ivOffset property.
     * 
     */
    public void setIVOffset(long value) {
        this.ivOffset = value;
    }

    /**
     * Gets the value of the ivLength property.
     * 
     */
    public int getIVLength() {
        return ivLength;
    }

    /**
     * Sets the value of the ivLength property.
     * 
     */
    public void setIVLength(int value) {
        this.ivLength = value;
    }

    /**
     * Gets the value of the dataOffset property.
     * 
     */
    public long getDataOffset() {
        return dataOffset;
    }

    /**
     * Sets the value of the dataOffset property.
     * 
     */
    public void setDataOffset(long value) {
        this.dataOffset = value;
    }

    /**
     * Gets the value of the dataLength property.
     * 
     */
    public int getDataLength() {
        return dataLength;
    }

    /**
     * Sets the value of the dataLength property.
     * 
     */
    public void setDataLength(int value) {
        this.dataLength = value;
    }

}
