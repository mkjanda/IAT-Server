//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.12 at 11:08:36 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.IATReport;
import net.iatsoftware.iat.messaging.Message;


/**
 * <p>Java class for ServerReportPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServerReportPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ContactFName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ContactLName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Organization" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NumIATsAlotted" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="NumAdministrations" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="NumAdministrationsRemaining" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DiskAlottmentMB" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DiskAlottmentRemainingKB" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="IATReport" type="{http://www.iatsoftware.net/schema}iat-report-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServerReportPojo", propOrder = {
    "contactFName",
    "contactLName",
    "organization",
    "numIATsAlotted",
    "numAdministrations",
    "numAdministrationsRemaining",
    "diskAlottmentMB",
    "diskAlottmentRemainingKB",
    "iatReport"
})
public abstract class ServerReportPojo
    extends Message
{

    @XmlElement(name = "ContactFName", required = true)
    protected String contactFName;
    @XmlElement(name = "ContactLName", required = true)
    protected String contactLName;
    @XmlElement(name = "Organization", required = true)
    protected String organization;
    @XmlElement(name = "NumIATsAlotted")
    protected int numIATsAlotted;
    @XmlElement(name = "NumAdministrations")
    protected int numAdministrations;
    @XmlElement(name = "NumAdministrationsRemaining")
    protected int numAdministrationsRemaining;
    @XmlElement(name = "DiskAlottmentMB")
    protected int diskAlottmentMB;
    @XmlElement(name = "DiskAlottmentRemainingKB")
    protected long diskAlottmentRemainingKB;
    @XmlElement(name = "IATReport")
    protected List<IATReport> iatReport;

    /**
     * Gets the value of the contactFName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactFName() {
        return contactFName;
    }

    /**
     * Sets the value of the contactFName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactFName(String value) {
        this.contactFName = value;
    }

    /**
     * Gets the value of the contactLName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactLName() {
        return contactLName;
    }

    /**
     * Sets the value of the contactLName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactLName(String value) {
        this.contactLName = value;
    }

    /**
     * Gets the value of the organization property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Sets the value of the organization property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganization(String value) {
        this.organization = value;
    }

    /**
     * Gets the value of the numIATsAlotted property.
     * 
     */
    public int getNumIATsAlotted() {
        return numIATsAlotted;
    }

    /**
     * Sets the value of the numIATsAlotted property.
     * 
     */
    public void setNumIATsAlotted(int value) {
        this.numIATsAlotted = value;
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
     * Gets the value of the numAdministrationsRemaining property.
     * 
     */
    public int getNumAdministrationsRemaining() {
        return numAdministrationsRemaining;
    }

    /**
     * Sets the value of the numAdministrationsRemaining property.
     * 
     */
    public void setNumAdministrationsRemaining(int value) {
        this.numAdministrationsRemaining = value;
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
     * Gets the value of the diskAlottmentRemainingKB property.
     * 
     */
    public long getDiskAlottmentRemainingKB() {
        return diskAlottmentRemainingKB;
    }

    /**
     * Sets the value of the diskAlottmentRemainingKB property.
     * 
     */
    public void setDiskAlottmentRemainingKB(long value) {
        this.diskAlottmentRemainingKB = value;
    }

    /**
     * Gets the value of the iatReport property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the iatReport property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIATReport().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IATReport }
     * 
     * 
     */
    public List<IATReport> getIATReport() {
        if (iatReport == null) {
            iatReport = new ArrayList<IATReport>();
        }
        return this.iatReport;
    }

}