//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.09 at 12:27:51 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MiscResultSetDataPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MiscResultSetDataPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TestAuthor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="NumEntried" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MiscResultSetDataPojo", propOrder = {
    "testAuthor"
})
public abstract class MiscResultSetDataPojo {

    @XmlElement(name = "TestAuthor", required = true)
    protected String testAuthor;
    @XmlAttribute(name = "NumEntried", required = true)
    protected int numEntried;

    /**
     * Gets the value of the testAuthor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestAuthor() {
        return testAuthor;
    }

    /**
     * Sets the value of the testAuthor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestAuthor(String value) {
        this.testAuthor = value;
    }

    /**
     * Gets the value of the numEntried property.
     * 
     */
    public int getNumEntried() {
        return numEntried;
    }

    /**
     * Sets the value of the numEntried property.
     * 
     */
    public void setNumEntried(int value) {
        this.numEntried = value;
    }

}
