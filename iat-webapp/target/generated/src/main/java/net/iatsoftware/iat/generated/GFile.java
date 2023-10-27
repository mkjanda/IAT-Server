//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.09.25 at 12:11:19 AM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.FileEntity;


/**
 * <p>Java class for GFile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GFile"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}file-entity-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MimeType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Size" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ResourceType" type="{http://www.iatsoftware.net/schema}ResourceType"/&gt;
 *         &lt;element name="ResourceId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ReferenceId" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GFile", propOrder = {
    "mimeType",
    "size",
    "resourceType",
    "resourceId",
    "referenceId"
})
public abstract class GFile
    extends FileEntity
{

    @XmlElement(name = "MimeType", required = true)
    protected String mimeType;
    @XmlElement(name = "Size")
    protected int size;
    @XmlElement(name = "ResourceType", required = true)
    @XmlSchemaType(name = "string")
    protected ResourceType resourceType;
    @XmlElement(name = "ResourceId")
    protected int resourceId;
    @XmlElement(name = "ReferenceId", type = Integer.class)
    protected List<Integer> referenceId;

    /**
     * Gets the value of the mimeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the value of the mimeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeType(String value) {
        this.mimeType = value;
    }

    /**
     * Gets the value of the size property.
     * 
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     */
    public void setSize(int value) {
        this.size = value;
    }

    /**
     * Gets the value of the resourceType property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceType }
     *     
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * Sets the value of the resourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceType }
     *     
     */
    public void setResourceType(ResourceType value) {
        this.resourceType = value;
    }

    /**
     * Gets the value of the resourceId property.
     * 
     */
    public int getResourceId() {
        return resourceId;
    }

    /**
     * Sets the value of the resourceId property.
     * 
     */
    public void setResourceId(int value) {
        this.resourceId = value;
    }

    /**
     * Gets the value of the referenceId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the referenceId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getReferenceId() {
        if (referenceId == null) {
            referenceId = new ArrayList<Integer>();
        }
        return this.referenceId;
    }

}
