//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.13 at 08:23:51 AM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.Message;


/**
 * <p>Java class for EncryptedDESCipherPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EncryptedDESCipherPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Cipher" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IV" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EncryptedDESCipherPojo", propOrder = {
    "cipher",
    "iv"
})
public class EncryptedDESCipherPojo
    extends Message
{

    @XmlElement(name = "Cipher", required = true)
    protected String cipher;
    @XmlElement(name = "IV", required = true)
    protected String iv;

    /**
     * Gets the value of the cipher property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCipher() {
        return cipher;
    }

    /**
     * Sets the value of the cipher property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCipher(String value) {
        this.cipher = value;
    }

    /**
     * Gets the value of the iv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIV() {
        return iv;
    }

    /**
     * Sets the value of the iv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIV(String value) {
        this.iv = value;
    }

}
