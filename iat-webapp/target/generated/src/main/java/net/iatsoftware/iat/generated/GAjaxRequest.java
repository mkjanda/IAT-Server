//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.17 at 05:48:02 AM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GAjaxRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GAjaxRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Request" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RequestData" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ClientID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IATName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Host" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RootContext" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TestSegmentID" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="RequestType" use="required" type="{http://www.iatsoftware.net/schema}AjaxRequestType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GAjaxRequest", propOrder = {
    "request",
    "requestData",
    "clientID",
    "iatName",
    "host",
    "rootContext",
    "testSegmentID"
})
public class GAjaxRequest {

    @XmlElement(name = "Request", required = true)
    protected String request;
    @XmlElement(name = "RequestData", required = true)
    protected String requestData;
    @XmlElement(name = "ClientID")
    protected int clientID;
    @XmlElement(name = "IATName", required = true)
    protected String iatName;
    @XmlElement(name = "Host", required = true)
    protected String host;
    @XmlElement(name = "RootContext", required = true)
    protected String rootContext;
    @XmlElement(name = "TestSegmentID")
    protected long testSegmentID;
    @XmlAttribute(name = "RequestType", required = true)
    protected AjaxRequestType requestType;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequest(String value) {
        this.request = value;
    }

    /**
     * Gets the value of the requestData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestData() {
        return requestData;
    }

    /**
     * Sets the value of the requestData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestData(String value) {
        this.requestData = value;
    }

    /**
     * Gets the value of the clientID property.
     * 
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * Sets the value of the clientID property.
     * 
     */
    public void setClientID(int value) {
        this.clientID = value;
    }

    /**
     * Gets the value of the iatName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIATName() {
        return iatName;
    }

    /**
     * Sets the value of the iatName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIATName(String value) {
        this.iatName = value;
    }

    /**
     * Gets the value of the host property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the value of the host property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHost(String value) {
        this.host = value;
    }

    /**
     * Gets the value of the rootContext property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRootContext() {
        return rootContext;
    }

    /**
     * Sets the value of the rootContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRootContext(String value) {
        this.rootContext = value;
    }

    /**
     * Gets the value of the testSegmentID property.
     * 
     */
    public long getTestSegmentID() {
        return testSegmentID;
    }

    /**
     * Sets the value of the testSegmentID property.
     * 
     */
    public void setTestSegmentID(long value) {
        this.testSegmentID = value;
    }

    /**
     * Gets the value of the requestType property.
     * 
     * @return
     *     possible object is
     *     {@link AjaxRequestType }
     *     
     */
    public AjaxRequestType getRequestType() {
        return requestType;
    }

    /**
     * Sets the value of the requestType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AjaxRequestType }
     *     
     */
    public void setRequestType(AjaxRequestType value) {
        this.requestType = value;
    }

}