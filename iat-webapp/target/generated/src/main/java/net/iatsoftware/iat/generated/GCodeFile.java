//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.07.28 at 02:14:21 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.deployment.ProcessedCode;


/**
 * <p>Java class for GCodeFile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GCodeFile"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GlobalVarDecl" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="GlobalCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProcessedCode" type="{http://www.iatsoftware.net/schema}processed-code-impl" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="ElementName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GCodeFile", propOrder = {
    "globalVarDecl",
    "globalCode",
    "processedCode"
})
public class GCodeFile {

    @XmlElement(name = "GlobalVarDecl", required = true)
    protected String globalVarDecl;
    @XmlElement(name = "GlobalCode", required = true)
    protected String globalCode;
    @XmlElement(name = "ProcessedCode", required = true)
    protected List<ProcessedCode> processedCode;
    @XmlAttribute(name = "ElementName", required = true)
    protected String elementName;

    /**
     * Gets the value of the globalVarDecl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlobalVarDecl() {
        return globalVarDecl;
    }

    /**
     * Sets the value of the globalVarDecl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobalVarDecl(String value) {
        this.globalVarDecl = value;
    }

    /**
     * Gets the value of the globalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlobalCode() {
        return globalCode;
    }

    /**
     * Sets the value of the globalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobalCode(String value) {
        this.globalCode = value;
    }

    /**
     * Gets the value of the processedCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the processedCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProcessedCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProcessedCode }
     * 
     * 
     */
    public List<ProcessedCode> getProcessedCode() {
        if (processedCode == null) {
            processedCode = new ArrayList<ProcessedCode>();
        }
        return this.processedCode;
    }

    /**
     * Gets the value of the elementName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElementName() {
        return elementName;
    }

    /**
     * Sets the value of the elementName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElementName(String value) {
        this.elementName = value;
    }

}
