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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.Message;


/**
 * <p>Java class for AlterClientStatePojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AlterClientStatePojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProductKey" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Alteration" type="{http://www.iatsoftware.net/schema}ClientStateAlteration"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlterClientStatePojo", propOrder = {
    "productKey",
    "alteration"
})
public abstract class AlterClientStatePojo
    extends Message
{

    @XmlElement(name = "ProductKey", required = true)
    protected String productKey;
    @XmlElement(name = "Alteration", required = true)
    @XmlSchemaType(name = "string")
    protected ClientStateAlteration alteration;

    /**
     * Gets the value of the productKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductKey() {
        return productKey;
    }

    /**
     * Sets the value of the productKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductKey(String value) {
        this.productKey = value;
    }

    /**
     * Gets the value of the alteration property.
     * 
     * @return
     *     possible object is
     *     {@link ClientStateAlteration }
     *     
     */
    public ClientStateAlteration getAlteration() {
        return alteration;
    }

    /**
     * Sets the value of the alteration property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientStateAlteration }
     *     
     */
    public void setAlteration(ClientStateAlteration value) {
        this.alteration = value;
    }

}
