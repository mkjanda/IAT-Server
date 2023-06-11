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
import net.iatsoftware.iat.messaging.ClientActivityEvent;
import net.iatsoftware.iat.messaging.ExceptionMessage;


/**
 * <p>Java class for ClientException complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClientException"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ClientMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProductCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ActivationKey" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="UserEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SaveFileVersion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TimeOpened" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ErrorCount" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ErrorsReported" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="HistoryEntry" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="TimeOpened" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ProductKey" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ErrorCount" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                   &lt;element name="ErrorsReported" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                   &lt;element name="SaveFileVersion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Exception" type="{http://www.iatsoftware.net/schema}exception-message-impl"/&gt;
 *         &lt;element name="EventLog"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Event" type="{http://www.iatsoftware.net/schema}client-activity-event-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientException", propOrder = {
    "clientMessage",
    "productCode",
    "activationKey",
    "userEmail",
    "version",
    "saveFileVersion",
    "timeOpened",
    "errorCount",
    "errorsReported",
    "historyEntry",
    "exception",
    "eventLog"
})
public abstract class ClientException {

    @XmlElement(name = "ClientMessage", required = true)
    protected String clientMessage;
    @XmlElement(name = "ProductCode", required = true)
    protected String productCode;
    @XmlElement(name = "ActivationKey", required = true)
    protected String activationKey;
    @XmlElement(name = "UserEmail")
    protected String userEmail;
    @XmlElement(name = "Version", required = true)
    protected String version;
    @XmlElement(name = "SaveFileVersion", required = true)
    protected String saveFileVersion;
    @XmlElement(name = "TimeOpened", required = true)
    protected String timeOpened;
    @XmlElement(name = "ErrorCount")
    protected int errorCount;
    @XmlElement(name = "ErrorsReported")
    protected int errorsReported;
    @XmlElement(name = "HistoryEntry")
    protected List<ClientException.HistoryEntry> historyEntry;
    @XmlElement(name = "Exception", required = true)
    protected ExceptionMessage exception;
    @XmlElement(name = "EventLog", required = true)
    protected ClientException.EventLog eventLog;

    /**
     * Gets the value of the clientMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientMessage() {
        return clientMessage;
    }

    /**
     * Sets the value of the clientMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientMessage(String value) {
        this.clientMessage = value;
    }

    /**
     * Gets the value of the productCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Sets the value of the productCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductCode(String value) {
        this.productCode = value;
    }

    /**
     * Gets the value of the activationKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivationKey() {
        return activationKey;
    }

    /**
     * Sets the value of the activationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivationKey(String value) {
        this.activationKey = value;
    }

    /**
     * Gets the value of the userEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Sets the value of the userEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserEmail(String value) {
        this.userEmail = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the saveFileVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSaveFileVersion() {
        return saveFileVersion;
    }

    /**
     * Sets the value of the saveFileVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSaveFileVersion(String value) {
        this.saveFileVersion = value;
    }

    /**
     * Gets the value of the timeOpened property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeOpened() {
        return timeOpened;
    }

    /**
     * Sets the value of the timeOpened property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeOpened(String value) {
        this.timeOpened = value;
    }

    /**
     * Gets the value of the errorCount property.
     * 
     */
    public int getErrorCount() {
        return errorCount;
    }

    /**
     * Sets the value of the errorCount property.
     * 
     */
    public void setErrorCount(int value) {
        this.errorCount = value;
    }

    /**
     * Gets the value of the errorsReported property.
     * 
     */
    public int getErrorsReported() {
        return errorsReported;
    }

    /**
     * Sets the value of the errorsReported property.
     * 
     */
    public void setErrorsReported(int value) {
        this.errorsReported = value;
    }

    /**
     * Gets the value of the historyEntry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the historyEntry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHistoryEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClientException.HistoryEntry }
     * 
     * 
     */
    public List<ClientException.HistoryEntry> getHistoryEntry() {
        if (historyEntry == null) {
            historyEntry = new ArrayList<ClientException.HistoryEntry>();
        }
        return this.historyEntry;
    }

    /**
     * Gets the value of the exception property.
     * 
     * @return
     *     possible object is
     *     {@link ExceptionMessage }
     *     
     */
    public ExceptionMessage getException() {
        return exception;
    }

    /**
     * Sets the value of the exception property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExceptionMessage }
     *     
     */
    public void setException(ExceptionMessage value) {
        this.exception = value;
    }

    /**
     * Gets the value of the eventLog property.
     * 
     * @return
     *     possible object is
     *     {@link ClientException.EventLog }
     *     
     */
    public ClientException.EventLog getEventLog() {
        return eventLog;
    }

    /**
     * Sets the value of the eventLog property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientException.EventLog }
     *     
     */
    public void setEventLog(ClientException.EventLog value) {
        this.eventLog = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Event" type="{http://www.iatsoftware.net/schema}client-activity-event-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "event"
    })
    public static class EventLog {

        @XmlElement(name = "Event")
        protected List<ClientActivityEvent> event;

        /**
         * Gets the value of the event property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the Jakarta XML Binding object.
         * This is why there is not a <CODE>set</CODE> method for the event property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEvent().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ClientActivityEvent }
         * 
         * 
         */
        public List<ClientActivityEvent> getEvent() {
            if (event == null) {
                event = new ArrayList<ClientActivityEvent>();
            }
            return this.event;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="TimeOpened" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ProductKey" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ErrorCount" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;element name="ErrorsReported" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;element name="SaveFileVersion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "timeOpened",
        "productKey",
        "errorCount",
        "errorsReported",
        "saveFileVersion"
    })
    public static class HistoryEntry {

        @XmlElement(name = "TimeOpened", required = true)
        protected String timeOpened;
        @XmlElement(name = "ProductKey", required = true)
        protected String productKey;
        @XmlElement(name = "ErrorCount")
        protected int errorCount;
        @XmlElement(name = "ErrorsReported")
        protected int errorsReported;
        @XmlElement(name = "SaveFileVersion", required = true)
        protected String saveFileVersion;

        /**
         * Gets the value of the timeOpened property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTimeOpened() {
            return timeOpened;
        }

        /**
         * Sets the value of the timeOpened property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTimeOpened(String value) {
            this.timeOpened = value;
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
         * Gets the value of the errorCount property.
         * 
         */
        public int getErrorCount() {
            return errorCount;
        }

        /**
         * Sets the value of the errorCount property.
         * 
         */
        public void setErrorCount(int value) {
            this.errorCount = value;
        }

        /**
         * Gets the value of the errorsReported property.
         * 
         */
        public int getErrorsReported() {
            return errorsReported;
        }

        /**
         * Sets the value of the errorsReported property.
         * 
         */
        public void setErrorsReported(int value) {
            this.errorsReported = value;
        }

        /**
         * Gets the value of the saveFileVersion property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSaveFileVersion() {
            return saveFileVersion;
        }

        /**
         * Sets the value of the saveFileVersion property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSaveFileVersion(String value) {
            this.saveFileVersion = value;
        }

    }

}
