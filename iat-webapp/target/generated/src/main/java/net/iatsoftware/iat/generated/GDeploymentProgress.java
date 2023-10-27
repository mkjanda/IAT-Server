//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.09.25 at 12:11:19 AM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.Message;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;


/**
 * <p>Java class for GDeploymentProgress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GDeploymentProgress"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Stage" type="{http://www.iatsoftware.net/schema}DeploymentStage"/&gt;
 *         &lt;element name="ActiveElement" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProgressMin" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ProgressMax" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ProgressVal" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="LastUpdate" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ServerException" type="{http://www.iatsoftware.net/schema}server-exception-message-impl" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GDeploymentProgress", propOrder = {
    "stage",
    "activeElement",
    "progressMin",
    "progressMax",
    "progressVal",
    "lastUpdate",
    "serverException"
})
public abstract class GDeploymentProgress
    extends Message
{

    @XmlElement(name = "Stage", required = true)
    @XmlSchemaType(name = "string")
    protected DeploymentStage stage;
    @XmlElement(name = "ActiveElement", required = true)
    protected String activeElement;
    @XmlElement(name = "ProgressMin")
    protected int progressMin;
    @XmlElement(name = "ProgressMax")
    protected int progressMax;
    @XmlElement(name = "ProgressVal")
    protected int progressVal;
    @XmlElement(name = "LastUpdate")
    protected boolean lastUpdate;
    @XmlElement(name = "ServerException")
    protected ServerExceptionMessage serverException;

    /**
     * Gets the value of the stage property.
     * 
     * @return
     *     possible object is
     *     {@link DeploymentStage }
     *     
     */
    public DeploymentStage getStage() {
        return stage;
    }

    /**
     * Sets the value of the stage property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeploymentStage }
     *     
     */
    public void setStage(DeploymentStage value) {
        this.stage = value;
    }

    /**
     * Gets the value of the activeElement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActiveElement() {
        return activeElement;
    }

    /**
     * Sets the value of the activeElement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActiveElement(String value) {
        this.activeElement = value;
    }

    /**
     * Gets the value of the progressMin property.
     * 
     */
    public int getProgressMin() {
        return progressMin;
    }

    /**
     * Sets the value of the progressMin property.
     * 
     */
    public void setProgressMin(int value) {
        this.progressMin = value;
    }

    /**
     * Gets the value of the progressMax property.
     * 
     */
    public int getProgressMax() {
        return progressMax;
    }

    /**
     * Sets the value of the progressMax property.
     * 
     */
    public void setProgressMax(int value) {
        this.progressMax = value;
    }

    /**
     * Gets the value of the progressVal property.
     * 
     */
    public int getProgressVal() {
        return progressVal;
    }

    /**
     * Sets the value of the progressVal property.
     * 
     */
    public void setProgressVal(int value) {
        this.progressVal = value;
    }

    /**
     * Gets the value of the lastUpdate property.
     * 
     */
    public boolean isLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the value of the lastUpdate property.
     * 
     */
    public void setLastUpdate(boolean value) {
        this.lastUpdate = value;
    }

    /**
     * Gets the value of the serverException property.
     * 
     * @return
     *     possible object is
     *     {@link ServerExceptionMessage }
     *     
     */
    public ServerExceptionMessage getServerException() {
        return serverException;
    }

    /**
     * Sets the value of the serverException property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServerExceptionMessage }
     *     
     */
    public void setServerException(ServerExceptionMessage value) {
        this.serverException = value;
    }

}
