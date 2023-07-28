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
import net.iatsoftware.iat.messaging.Message;


/**
 * <p>Java class for GUploadRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GUploadRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DeploymentID" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="DataUploadKey" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ItemSlideUploadKey" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ReconnectionKey" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GUploadRequest", propOrder = {
    "deploymentID",
    "dataUploadKey",
    "itemSlideUploadKey",
    "reconnectionKey"
})
public abstract class GUploadRequest
    extends Message
{

    @XmlElement(name = "DeploymentID")
    protected long deploymentID;
    @XmlElement(name = "DataUploadKey", required = true)
    protected String dataUploadKey;
    @XmlElement(name = "ItemSlideUploadKey", required = true)
    protected String itemSlideUploadKey;
    @XmlElement(name = "ReconnectionKey", required = true)
    protected String reconnectionKey;

    /**
     * Gets the value of the deploymentID property.
     * 
     */
    public long getDeploymentID() {
        return deploymentID;
    }

    /**
     * Sets the value of the deploymentID property.
     * 
     */
    public void setDeploymentID(long value) {
        this.deploymentID = value;
    }

    /**
     * Gets the value of the dataUploadKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataUploadKey() {
        return dataUploadKey;
    }

    /**
     * Sets the value of the dataUploadKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataUploadKey(String value) {
        this.dataUploadKey = value;
    }

    /**
     * Gets the value of the itemSlideUploadKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemSlideUploadKey() {
        return itemSlideUploadKey;
    }

    /**
     * Sets the value of the itemSlideUploadKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemSlideUploadKey(String value) {
        this.itemSlideUploadKey = value;
    }

    /**
     * Gets the value of the reconnectionKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReconnectionKey() {
        return reconnectionKey;
    }

    /**
     * Sets the value of the reconnectionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReconnectionKey(String value) {
        this.reconnectionKey = value;
    }

}
