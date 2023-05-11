//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.09 at 12:27:51 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.Message;


/**
 * <p>Java class for BasicClientInfoPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BasicClientInfoPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ClientID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="ProductKey" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DownloadPassword" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AdministrationsRemaining" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BasicClientInfoPojo", propOrder = {
    "firstName",
    "lastName",
    "clientID",
    "productKey",
    "downloadPassword",
    "administrationsRemaining"
})
public abstract class BasicClientInfoPojo
    extends Message
{

    @XmlElement(name = "FirstName", required = true)
    protected String firstName;
    @XmlElement(name = "LastName", required = true)
    protected String lastName;
    @XmlElement(name = "ClientID", required = true)
    protected BigInteger clientID;
    @XmlElement(name = "ProductKey", required = true)
    protected String productKey;
    @XmlElement(name = "DownloadPassword", required = true)
    protected String downloadPassword;
    @XmlElement(name = "AdministrationsRemaining", required = true)
    protected String administrationsRemaining;

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the clientID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getClientID() {
        return clientID;
    }

    /**
     * Sets the value of the clientID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setClientID(BigInteger value) {
        this.clientID = value;
    }

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
     * Gets the value of the downloadPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDownloadPassword() {
        return downloadPassword;
    }

    /**
     * Sets the value of the downloadPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDownloadPassword(String value) {
        this.downloadPassword = value;
    }

    /**
     * Gets the value of the administrationsRemaining property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdministrationsRemaining() {
        return administrationsRemaining;
    }

    /**
     * Sets the value of the administrationsRemaining property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdministrationsRemaining(String value) {
        this.administrationsRemaining = value;
    }

}
