//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.24 at 07:02:58 PM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServerConfigurationPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServerConfigurationPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AesCoreFilename" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ErrorReportEmail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DataFormatVersion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Host" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Path" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="StaticContentPath" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SecureStaticContentPath" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="HttpsEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IatsUri" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IatDeploymentUri" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ItemSlideUri" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ClientSoftwareUri" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ClientSoftwareUpdatesUri" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InitialPublicityAdministrations" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="InitialPooledAdministrations" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServerConfigurationPojo", propOrder = {
    "aesCoreFilename",
    "errorReportEmail",
    "dataFormatVersion",
    "host",
    "path",
    "staticContentPath",
    "secureStaticContentPath",
    "httpsEnabled",
    "iatsUri",
    "iatDeploymentUri",
    "itemSlideUri",
    "clientSoftwareUri",
    "clientSoftwareUpdatesUri",
    "initialPublicityAdministrations",
    "initialPooledAdministrations",
    "version"
})
public abstract class ServerConfigurationPojo {

    @XmlElement(name = "AesCoreFilename", required = true)
    protected String aesCoreFilename;
    @XmlElement(name = "ErrorReportEmail", required = true)
    protected String errorReportEmail;
    @XmlElement(name = "DataFormatVersion")
    protected int dataFormatVersion;
    @XmlElement(name = "Host", required = true)
    protected String host;
    @XmlElement(name = "Path", required = true)
    protected String path;
    @XmlElement(name = "StaticContentPath", required = true)
    protected String staticContentPath;
    @XmlElement(name = "SecureStaticContentPath", required = true)
    protected String secureStaticContentPath;
    @XmlElement(name = "HttpsEnabled")
    protected boolean httpsEnabled;
    @XmlElement(name = "IatsUri", required = true)
    protected String iatsUri;
    @XmlElement(name = "IatDeploymentUri", required = true)
    protected String iatDeploymentUri;
    @XmlElement(name = "ItemSlideUri", required = true)
    protected String itemSlideUri;
    @XmlElement(name = "ClientSoftwareUri", required = true)
    protected String clientSoftwareUri;
    @XmlElement(name = "ClientSoftwareUpdatesUri", required = true)
    protected String clientSoftwareUpdatesUri;
    @XmlElement(name = "InitialPublicityAdministrations")
    protected int initialPublicityAdministrations;
    @XmlElement(name = "InitialPooledAdministrations")
    protected int initialPooledAdministrations;
    @XmlElement(name = "Version", required = true)
    protected String version;

    /**
     * Gets the value of the aesCoreFilename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAesCoreFilename() {
        return aesCoreFilename;
    }

    /**
     * Sets the value of the aesCoreFilename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAesCoreFilename(String value) {
        this.aesCoreFilename = value;
    }

    /**
     * Gets the value of the errorReportEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorReportEmail() {
        return errorReportEmail;
    }

    /**
     * Sets the value of the errorReportEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorReportEmail(String value) {
        this.errorReportEmail = value;
    }

    /**
     * Gets the value of the dataFormatVersion property.
     * 
     */
    public int getDataFormatVersion() {
        return dataFormatVersion;
    }

    /**
     * Sets the value of the dataFormatVersion property.
     * 
     */
    public void setDataFormatVersion(int value) {
        this.dataFormatVersion = value;
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
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the staticContentPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaticContentPath() {
        return staticContentPath;
    }

    /**
     * Sets the value of the staticContentPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaticContentPath(String value) {
        this.staticContentPath = value;
    }

    /**
     * Gets the value of the secureStaticContentPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecureStaticContentPath() {
        return secureStaticContentPath;
    }

    /**
     * Sets the value of the secureStaticContentPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecureStaticContentPath(String value) {
        this.secureStaticContentPath = value;
    }

    /**
     * Gets the value of the httpsEnabled property.
     * 
     */
    public boolean isHttpsEnabled() {
        return httpsEnabled;
    }

    /**
     * Sets the value of the httpsEnabled property.
     * 
     */
    public void setHttpsEnabled(boolean value) {
        this.httpsEnabled = value;
    }

    /**
     * Gets the value of the iatsUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIatsUri() {
        return iatsUri;
    }

    /**
     * Sets the value of the iatsUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIatsUri(String value) {
        this.iatsUri = value;
    }

    /**
     * Gets the value of the iatDeploymentUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIatDeploymentUri() {
        return iatDeploymentUri;
    }

    /**
     * Sets the value of the iatDeploymentUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIatDeploymentUri(String value) {
        this.iatDeploymentUri = value;
    }

    /**
     * Gets the value of the itemSlideUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemSlideUri() {
        return itemSlideUri;
    }

    /**
     * Sets the value of the itemSlideUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemSlideUri(String value) {
        this.itemSlideUri = value;
    }

    /**
     * Gets the value of the clientSoftwareUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientSoftwareUri() {
        return clientSoftwareUri;
    }

    /**
     * Sets the value of the clientSoftwareUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientSoftwareUri(String value) {
        this.clientSoftwareUri = value;
    }

    /**
     * Gets the value of the clientSoftwareUpdatesUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientSoftwareUpdatesUri() {
        return clientSoftwareUpdatesUri;
    }

    /**
     * Sets the value of the clientSoftwareUpdatesUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientSoftwareUpdatesUri(String value) {
        this.clientSoftwareUpdatesUri = value;
    }

    /**
     * Gets the value of the initialPublicityAdministrations property.
     * 
     */
    public int getInitialPublicityAdministrations() {
        return initialPublicityAdministrations;
    }

    /**
     * Sets the value of the initialPublicityAdministrations property.
     * 
     */
    public void setInitialPublicityAdministrations(int value) {
        this.initialPublicityAdministrations = value;
    }

    /**
     * Gets the value of the initialPooledAdministrations property.
     * 
     */
    public int getInitialPooledAdministrations() {
        return initialPooledAdministrations;
    }

    /**
     * Sets the value of the initialPooledAdministrations property.
     * 
     */
    public void setInitialPooledAdministrations(int value) {
        this.initialPooledAdministrations = value;
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

}
