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
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.Message;


/**
 * <p>Java class for PartiallyEncryptedRSAKeyPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartiallyEncryptedRSAKeyPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="KeyType" type="{http://www.iatsoftware.net/schema}KeyType"/&gt;
 *         &lt;element name="Modulus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Exponent" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EncryptedKey" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartiallyEncryptedRSAKeyPojo", propOrder = {
    "keyType",
    "modulus",
    "exponent",
    "encryptedKey"
})
public abstract class PartiallyEncryptedRSAKeyPojo
    extends Message
{

    @XmlElement(name = "KeyType", required = true)
    @XmlSchemaType(name = "string")
    protected KeyType keyType;
    @XmlElement(name = "Modulus", required = true)
    protected String modulus;
    @XmlElement(name = "Exponent", required = true)
    protected String exponent;
    @XmlElement(name = "EncryptedKey", required = true)
    protected String encryptedKey;

    /**
     * Gets the value of the keyType property.
     * 
     * @return
     *     possible object is
     *     {@link KeyType }
     *     
     */
    public KeyType getKeyType() {
        return keyType;
    }

    /**
     * Sets the value of the keyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyType }
     *     
     */
    public void setKeyType(KeyType value) {
        this.keyType = value;
    }

    /**
     * Gets the value of the modulus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModulus() {
        return modulus;
    }

    /**
     * Sets the value of the modulus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModulus(String value) {
        this.modulus = value;
    }

    /**
     * Gets the value of the exponent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExponent() {
        return exponent;
    }

    /**
     * Sets the value of the exponent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExponent(String value) {
        this.exponent = value;
    }

    /**
     * Gets the value of the encryptedKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncryptedKey() {
        return encryptedKey;
    }

    /**
     * Sets the value of the encryptedKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncryptedKey(String value) {
        this.encryptedKey = value;
    }

}
