//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.13 at 12:28:08 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserDataPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserDataPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EMail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ActivationDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserDataPojo", propOrder = {
    "title",
    "fName",
    "lName",
    "eMail",
    "activationDate"
})
public abstract class UserDataPojo {

    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "FName", required = true)
    protected String fName;
    @XmlElement(name = "LName", required = true)
    protected String lName;
    @XmlElement(name = "EMail", required = true)
    protected String eMail;
    @XmlElement(name = "ActivationDate", required = true)
    protected String activationDate;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the fName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFName() {
        return fName;
    }

    /**
     * Sets the value of the fName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFName(String value) {
        this.fName = value;
    }

    /**
     * Gets the value of the lName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLName() {
        return lName;
    }

    /**
     * Sets the value of the lName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLName(String value) {
        this.lName = value;
    }

    /**
     * Gets the value of the eMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMail() {
        return eMail;
    }

    /**
     * Sets the value of the eMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMail(String value) {
        this.eMail = value;
    }

    /**
     * Gets the value of the activationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivationDate() {
        return activationDate;
    }

    /**
     * Sets the value of the activationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivationDate(String value) {
        this.activationDate = value;
    }

}
