//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.17 at 05:48:02 AM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GGlobalVar complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GGlobalVar"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OrigName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NewName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Assign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GGlobalVar", propOrder = {
    "origName",
    "newName",
    "assign"
})
public class GGlobalVar {

    @XmlElement(name = "OrigName", required = true)
    protected String origName;
    @XmlElement(name = "NewName", required = true)
    protected String newName;
    @XmlElement(name = "Assign")
    protected String assign;

    /**
     * Gets the value of the origName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigName() {
        return origName;
    }

    /**
     * Sets the value of the origName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigName(String value) {
        this.origName = value;
    }

    /**
     * Gets the value of the newName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewName() {
        return newName;
    }

    /**
     * Sets the value of the newName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewName(String value) {
        this.newName = value;
    }

    /**
     * Gets the value of the assign property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssign() {
        return assign;
    }

    /**
     * Sets the value of the assign property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssign(String value) {
        this.assign = value;
    }

}