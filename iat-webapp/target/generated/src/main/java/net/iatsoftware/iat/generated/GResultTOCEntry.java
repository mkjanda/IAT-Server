//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.09.25 at 12:11:19 AM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GResultTOCEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GResultTOCEntry"&gt;
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
@XmlType(name = "GResultTOCEntry", propOrder = {
    "keyOffset",
    "keyLength",
    "ivOffset",
    "ivLength",
    "dataOffset",
    "dataLength"
})
public class GResultTOCEntry {

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
