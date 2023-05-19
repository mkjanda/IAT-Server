//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.15 at 04:11:12 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.entities.User;


/**
 * <p>Java class for IATListEntryPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IATListEntryPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IATName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="UserInfo" type="{http://www.iatsoftware.net/schema}user-info-impl"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IATListEntryPojo", propOrder = {
    "iatName",
    "userInfo"
})
public abstract class IATListEntryPojo {

    @XmlElement(name = "IATName", required = true)
    protected String iatName;
    @XmlElement(name = "UserInfo", required = true)
    protected User userInfo;

    /**
     * Gets the value of the iatName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIATName() {
        return iatName;
    }

    /**
     * Sets the value of the iatName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIATName(String value) {
        this.iatName = value;
    }

    /**
     * Gets the value of the userInfo property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUserInfo() {
        return userInfo;
    }

    /**
     * Sets the value of the userInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUserInfo(User value) {
        this.userInfo = value;
    }

}
