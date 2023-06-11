//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.06 at 10:05:44 PM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.Message;


/**
 * <p>Java class for AcceptRequestPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AcceptRequestPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RequestID" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="NumAdministrations" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="NumActivations" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="NumDownloads" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DiskAlottmentMB" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="NumIATs" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcceptRequestPojo", propOrder = {
    "requestID",
    "numAdministrations",
    "numActivations",
    "numDownloads",
    "diskAlottmentMB",
    "numIATs"
})
public abstract class AcceptRequestPojo
    extends Message
{

    @XmlElement(name = "RequestID")
    protected long requestID;
    @XmlElement(name = "NumAdministrations")
    protected int numAdministrations;
    @XmlElement(name = "NumActivations")
    protected int numActivations;
    @XmlElement(name = "NumDownloads")
    protected int numDownloads;
    @XmlElement(name = "DiskAlottmentMB")
    protected int diskAlottmentMB;
    @XmlElement(name = "NumIATs")
    protected int numIATs;

    /**
     * Gets the value of the requestID property.
     * 
     */
    public long getRequestID() {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     * 
     */
    public void setRequestID(long value) {
        this.requestID = value;
    }

    /**
     * Gets the value of the numAdministrations property.
     * 
     */
    public int getNumAdministrations() {
        return numAdministrations;
    }

    /**
     * Sets the value of the numAdministrations property.
     * 
     */
    public void setNumAdministrations(int value) {
        this.numAdministrations = value;
    }

    /**
     * Gets the value of the numActivations property.
     * 
     */
    public int getNumActivations() {
        return numActivations;
    }

    /**
     * Sets the value of the numActivations property.
     * 
     */
    public void setNumActivations(int value) {
        this.numActivations = value;
    }

    /**
     * Gets the value of the numDownloads property.
     * 
     */
    public int getNumDownloads() {
        return numDownloads;
    }

    /**
     * Sets the value of the numDownloads property.
     * 
     */
    public void setNumDownloads(int value) {
        this.numDownloads = value;
    }

    /**
     * Gets the value of the diskAlottmentMB property.
     * 
     */
    public int getDiskAlottmentMB() {
        return diskAlottmentMB;
    }

    /**
     * Sets the value of the diskAlottmentMB property.
     * 
     */
    public void setDiskAlottmentMB(int value) {
        this.diskAlottmentMB = value;
    }

    /**
     * Gets the value of the numIATs property.
     * 
     */
    public int getNumIATs() {
        return numIATs;
    }

    /**
     * Sets the value of the numIATs property.
     * 
     */
    public void setNumIATs(int value) {
        this.numIATs = value;
    }

}
