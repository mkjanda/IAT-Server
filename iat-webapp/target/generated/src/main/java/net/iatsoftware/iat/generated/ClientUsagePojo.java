//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.06 at 10:05:44 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.Message;
import net.iatsoftware.iat.messaging.TestUsage;
import net.iatsoftware.iat.messaging.UserData;


/**
 * <p>Java class for ClientUsagePojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClientUsagePojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UserData" type="{http://www.iatsoftware.net/schema}user-data-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="TestUsage" type="{http://www.iatsoftware.net/schema}test-usage-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="ClientID" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="ProductKey" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Frozen" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Deleted" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ContactFName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ContactLName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EMail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RegistrationDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LastIATUploadDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LastIATAdministrationDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LastDataRetrievalDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DiskAlottmentMB" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NumIATsAlotted" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Administrations" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AdministrationsRemaining" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TotalDiskUsageMB" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NumIATs" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ActivationsRemaining" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ActivationsConsumed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DownloadsRemaining" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DownloadsConsumed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DownloadPassword" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProductUse" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientUsagePojo", propOrder = {
    "userData",
    "testUsage",
    "clientID",
    "productKey",
    "frozen",
    "deleted",
    "contactFName",
    "contactLName",
    "eMail",
    "registrationDate",
    "lastIATUploadDate",
    "lastIATAdministrationDate",
    "lastDataRetrievalDate",
    "diskAlottmentMB",
    "numIATsAlotted",
    "administrations",
    "administrationsRemaining",
    "totalDiskUsageMB",
    "numIATs",
    "activationsRemaining",
    "activationsConsumed",
    "downloadsRemaining",
    "downloadsConsumed",
    "downloadPassword",
    "productUse"
})
public abstract class ClientUsagePojo
    extends Message
{

    @XmlElement(name = "UserData")
    protected List<UserData> userData;
    @XmlElement(name = "TestUsage")
    protected List<TestUsage> testUsage;
    @XmlElement(name = "ClientID")
    protected long clientID;
    @XmlElement(name = "ProductKey", required = true)
    protected String productKey;
    @XmlElement(name = "Frozen")
    protected boolean frozen;
    @XmlElement(name = "Deleted")
    protected boolean deleted;
    @XmlElement(name = "ContactFName", required = true)
    protected String contactFName;
    @XmlElement(name = "ContactLName", required = true)
    protected String contactLName;
    @XmlElement(name = "EMail", required = true)
    protected String eMail;
    @XmlElement(name = "RegistrationDate", required = true)
    protected String registrationDate;
    @XmlElement(name = "LastIATUploadDate", required = true)
    protected String lastIATUploadDate;
    @XmlElement(name = "LastIATAdministrationDate", required = true)
    protected String lastIATAdministrationDate;
    @XmlElement(name = "LastDataRetrievalDate", required = true)
    protected String lastDataRetrievalDate;
    @XmlElement(name = "DiskAlottmentMB", required = true)
    protected String diskAlottmentMB;
    @XmlElement(name = "NumIATsAlotted", required = true)
    protected String numIATsAlotted;
    @XmlElement(name = "Administrations", required = true)
    protected String administrations;
    @XmlElement(name = "AdministrationsRemaining", required = true)
    protected String administrationsRemaining;
    @XmlElement(name = "TotalDiskUsageMB", required = true)
    protected String totalDiskUsageMB;
    @XmlElement(name = "NumIATs", required = true)
    protected String numIATs;
    @XmlElement(name = "ActivationsRemaining", required = true)
    protected String activationsRemaining;
    @XmlElement(name = "ActivationsConsumed", required = true)
    protected String activationsConsumed;
    @XmlElement(name = "DownloadsRemaining", required = true)
    protected String downloadsRemaining;
    @XmlElement(name = "DownloadsConsumed", required = true)
    protected String downloadsConsumed;
    @XmlElement(name = "DownloadPassword", required = true)
    protected String downloadPassword;
    @XmlElement(name = "ProductUse", required = true)
    protected String productUse;

    /**
     * Gets the value of the userData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the userData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserData }
     * 
     * 
     */
    public List<UserData> getUserData() {
        if (userData == null) {
            userData = new ArrayList<UserData>();
        }
        return this.userData;
    }

    /**
     * Gets the value of the testUsage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the testUsage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTestUsage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TestUsage }
     * 
     * 
     */
    public List<TestUsage> getTestUsage() {
        if (testUsage == null) {
            testUsage = new ArrayList<TestUsage>();
        }
        return this.testUsage;
    }

    /**
     * Gets the value of the clientID property.
     * 
     */
    public long getClientID() {
        return clientID;
    }

    /**
     * Sets the value of the clientID property.
     * 
     */
    public void setClientID(long value) {
        this.clientID = value;
    }

    /**
     * Gets the value of the productKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductKey() {
        return productKey;
    }

    /**
     * Sets the value of the productKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductKey(String value) {
        this.productKey = value;
    }

    /**
     * Gets the value of the frozen property.
     * 
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Sets the value of the frozen property.
     * 
     */
    public void setFrozen(boolean value) {
        this.frozen = value;
    }

    /**
     * Gets the value of the deleted property.
     * 
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the value of the deleted property.
     * 
     */
    public void setDeleted(boolean value) {
        this.deleted = value;
    }

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
     * Gets the value of the registrationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets the value of the registrationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrationDate(String value) {
        this.registrationDate = value;
    }

    /**
     * Gets the value of the lastIATUploadDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastIATUploadDate() {
        return lastIATUploadDate;
    }

    /**
     * Sets the value of the lastIATUploadDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastIATUploadDate(String value) {
        this.lastIATUploadDate = value;
    }

    /**
     * Gets the value of the lastIATAdministrationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastIATAdministrationDate() {
        return lastIATAdministrationDate;
    }

    /**
     * Sets the value of the lastIATAdministrationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastIATAdministrationDate(String value) {
        this.lastIATAdministrationDate = value;
    }

    /**
     * Gets the value of the lastDataRetrievalDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastDataRetrievalDate() {
        return lastDataRetrievalDate;
    }

    /**
     * Sets the value of the lastDataRetrievalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastDataRetrievalDate(String value) {
        this.lastDataRetrievalDate = value;
    }

    /**
     * Gets the value of the diskAlottmentMB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiskAlottmentMB() {
        return diskAlottmentMB;
    }

    /**
     * Sets the value of the diskAlottmentMB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiskAlottmentMB(String value) {
        this.diskAlottmentMB = value;
    }

    /**
     * Gets the value of the numIATsAlotted property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumIATsAlotted() {
        return numIATsAlotted;
    }

    /**
     * Sets the value of the numIATsAlotted property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumIATsAlotted(String value) {
        this.numIATsAlotted = value;
    }

    /**
     * Gets the value of the administrations property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdministrations() {
        return administrations;
    }

    /**
     * Sets the value of the administrations property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdministrations(String value) {
        this.administrations = value;
    }

    /**
     * Gets the value of the administrationsRemaining property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdministrationsRemaining() {
        return administrationsRemaining;
    }

    /**
     * Sets the value of the administrationsRemaining property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdministrationsRemaining(String value) {
        this.administrationsRemaining = value;
    }

    /**
     * Gets the value of the totalDiskUsageMB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalDiskUsageMB() {
        return totalDiskUsageMB;
    }

    /**
     * Sets the value of the totalDiskUsageMB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalDiskUsageMB(String value) {
        this.totalDiskUsageMB = value;
    }

    /**
     * Gets the value of the numIATs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumIATs() {
        return numIATs;
    }

    /**
     * Sets the value of the numIATs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumIATs(String value) {
        this.numIATs = value;
    }

    /**
     * Gets the value of the activationsRemaining property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivationsRemaining() {
        return activationsRemaining;
    }

    /**
     * Sets the value of the activationsRemaining property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivationsRemaining(String value) {
        this.activationsRemaining = value;
    }

    /**
     * Gets the value of the activationsConsumed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivationsConsumed() {
        return activationsConsumed;
    }

    /**
     * Sets the value of the activationsConsumed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivationsConsumed(String value) {
        this.activationsConsumed = value;
    }

    /**
     * Gets the value of the downloadsRemaining property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDownloadsRemaining() {
        return downloadsRemaining;
    }

    /**
     * Sets the value of the downloadsRemaining property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDownloadsRemaining(String value) {
        this.downloadsRemaining = value;
    }

    /**
     * Gets the value of the downloadsConsumed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDownloadsConsumed() {
        return downloadsConsumed;
    }

    /**
     * Sets the value of the downloadsConsumed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDownloadsConsumed(String value) {
        this.downloadsConsumed = value;
    }

    /**
     * Gets the value of the downloadPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDownloadPassword() {
        return downloadPassword;
    }

    /**
     * Sets the value of the downloadPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDownloadPassword(String value) {
        this.downloadPassword = value;
    }

    /**
     * Gets the value of the productUse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductUse() {
        return productUse;
    }

    /**
     * Sets the value of the productUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductUse(String value) {
        this.productUse = value;
    }

}
