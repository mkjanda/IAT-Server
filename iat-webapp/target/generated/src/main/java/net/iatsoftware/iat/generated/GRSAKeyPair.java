//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.07.28 at 02:14:21 PM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.messaging.Message;


/**
 * <p>Java class for GRSAKeyPair complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GRSAKeyPair"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DataKey" type="{http://www.iatsoftware.net/schema}partially-encrypted-rsa-key-impl"/&gt;
 *         &lt;element name="AdminKey" type="{http://www.iatsoftware.net/schema}partially-encrypted-rsa-key-impl"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GRSAKeyPair", propOrder = {
    "dataKey",
    "adminKey"
})
public abstract class GRSAKeyPair
    extends Message
{

    @XmlElement(name = "DataKey", required = true)
    protected PartiallyEncryptedRSAKey dataKey;
    @XmlElement(name = "AdminKey", required = true)
    protected PartiallyEncryptedRSAKey adminKey;

    /**
     * Gets the value of the dataKey property.
     * 
     * @return
     *     possible object is
     *     {@link PartiallyEncryptedRSAKey }
     *     
     */
    public PartiallyEncryptedRSAKey getDataKey() {
        return dataKey;
    }

    /**
     * Sets the value of the dataKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartiallyEncryptedRSAKey }
     *     
     */
    public void setDataKey(PartiallyEncryptedRSAKey value) {
        this.dataKey = value;
    }

    /**
     * Gets the value of the adminKey property.
     * 
     * @return
     *     possible object is
     *     {@link PartiallyEncryptedRSAKey }
     *     
     */
    public PartiallyEncryptedRSAKey getAdminKey() {
        return adminKey;
    }

    /**
     * Sets the value of the adminKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartiallyEncryptedRSAKey }
     *     
     */
    public void setAdminKey(PartiallyEncryptedRSAKey value) {
        this.adminKey = value;
    }

}
