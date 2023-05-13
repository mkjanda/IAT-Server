//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.12 at 11:08:36 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.configfile.ConfigFile;
import net.iatsoftware.iat.deployment.DeploymentProgress;
import net.iatsoftware.iat.messaging.ActivationRequest;
import net.iatsoftware.iat.messaging.ActivationResponse;
import net.iatsoftware.iat.messaging.AdminListener;
import net.iatsoftware.iat.messaging.Handshake;
import net.iatsoftware.iat.messaging.IATList;
import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.messaging.Packet;
import net.iatsoftware.iat.messaging.QueryIATExists;
import net.iatsoftware.iat.messaging.RSAKeyPair;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;
import net.iatsoftware.iat.messaging.ServerReport;
import net.iatsoftware.iat.messaging.TokenDefinition;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.messaging.UploadRequest;
import net.iatsoftware.iat.resultdata.ResultPacket;
import net.iatsoftware.iat.resultdata.ResultSetDescriptor;


/**
 * <p>Java class for Envelope complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Envelope"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="AdminListener" type="{http://www.iatsoftware.net/schema}admin-listener-impl"/&gt;
 *           &lt;element name="ResultSetDescriptor" type="{http://www.iatsoftware.net/schema}result-set-descriptor-impl"/&gt;
 *           &lt;element name="ConfigFile" type="{http://www.iatsoftware.net/schema}config-file-impl"/&gt;
 *           &lt;element name="ActivationResponse" type="{http://www.iatsoftware.net/schema}activation-response-impl"/&gt;
 *           &lt;element name="ActivationRequest" type="{http://www.iatsoftware.net/schema}activation-request-impl"/&gt;
 *           &lt;element name="DeploymentProgress" type="{http://www.iatsoftware.net/schema}deployment-progress-impl"/&gt;
 *           &lt;element name="Handshake" type="{http://www.iatsoftware.net/schema}handshake-impl"/&gt;
 *           &lt;element name="IATList" type="{http://www.iatsoftware.net/schema}iat-list-impl"/&gt;
 *           &lt;element name="Manifest" type="{http://www.iatsoftware.net/schema}manifest-impl"/&gt;
 *           &lt;element name="Packet" type="{http://www.iatsoftware.net/schema}packet-impl"/&gt;
 *           &lt;element name="TokenDefinition" type="{http://www.iatsoftware.net/schema}token-definition-impl"/&gt;
 *           &lt;element name="ResultPacket" type="{http://www.iatsoftware.net/schema}result-packet-impl"/&gt;
 *           &lt;element name="RSAKeyPair" type="{http://www.iatsoftware.net/schema}rsa-key-pair-impl"/&gt;
 *           &lt;element name="QueryIATExists" type="{http://www.iatsoftware.net/schema}query-iat-exists-impl"/&gt;
 *           &lt;element name="TransactionRequest" type="{http://www.iatsoftware.net/schema}transaction-request-impl"/&gt;
 *           &lt;element name="ServerReport" type="{http://www.iatsoftware.net/schema}server-report-impl"/&gt;
 *           &lt;element name="ServerException" type="{http://www.iatsoftware.net/schema}server-exception-message-impl"/&gt;
 *           &lt;element name="UploadRequest" type="{http://www.iatsoftware.net/schema}upload-request-impl"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Envelope", propOrder = {
    "adminListener",
    "resultSetDescriptor",
    "configFile",
    "activationResponse",
    "activationRequest",
    "deploymentProgress",
    "handshake",
    "iatList",
    "manifest",
    "packet",
    "tokenDefinition",
    "resultPacket",
    "rsaKeyPair",
    "queryIATExists",
    "transactionRequest",
    "serverReport",
    "serverException",
    "uploadRequest"
})
public abstract class Envelope {

    @XmlElement(name = "AdminListener")
    protected AdminListener adminListener;
    @XmlElement(name = "ResultSetDescriptor")
    protected ResultSetDescriptor resultSetDescriptor;
    @XmlElement(name = "ConfigFile")
    protected ConfigFile configFile;
    @XmlElement(name = "ActivationResponse")
    protected ActivationResponse activationResponse;
    @XmlElement(name = "ActivationRequest")
    protected ActivationRequest activationRequest;
    @XmlElement(name = "DeploymentProgress")
    protected DeploymentProgress deploymentProgress;
    @XmlElement(name = "Handshake")
    protected Handshake handshake;
    @XmlElement(name = "IATList")
    protected IATList iatList;
    @XmlElement(name = "Manifest")
    protected Manifest manifest;
    @XmlElement(name = "Packet")
    protected Packet packet;
    @XmlElement(name = "TokenDefinition")
    protected TokenDefinition tokenDefinition;
    @XmlElement(name = "ResultPacket")
    protected ResultPacket resultPacket;
    @XmlElement(name = "RSAKeyPair")
    protected RSAKeyPair rsaKeyPair;
    @XmlElement(name = "QueryIATExists")
    protected QueryIATExists queryIATExists;
    @XmlElement(name = "TransactionRequest")
    protected TransactionRequest transactionRequest;
    @XmlElement(name = "ServerReport")
    protected ServerReport serverReport;
    @XmlElement(name = "ServerException")
    protected ServerExceptionMessage serverException;
    @XmlElement(name = "UploadRequest")
    protected UploadRequest uploadRequest;

    /**
     * Gets the value of the adminListener property.
     * 
     * @return
     *     possible object is
     *     {@link AdminListener }
     *     
     */
    public AdminListener getAdminListener() {
        return adminListener;
    }

    /**
     * Sets the value of the adminListener property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdminListener }
     *     
     */
    public void setAdminListener(AdminListener value) {
        this.adminListener = value;
    }

    /**
     * Gets the value of the resultSetDescriptor property.
     * 
     * @return
     *     possible object is
     *     {@link ResultSetDescriptor }
     *     
     */
    public ResultSetDescriptor getResultSetDescriptor() {
        return resultSetDescriptor;
    }

    /**
     * Sets the value of the resultSetDescriptor property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultSetDescriptor }
     *     
     */
    public void setResultSetDescriptor(ResultSetDescriptor value) {
        this.resultSetDescriptor = value;
    }

    /**
     * Gets the value of the configFile property.
     * 
     * @return
     *     possible object is
     *     {@link ConfigFile }
     *     
     */
    public ConfigFile getConfigFile() {
        return configFile;
    }

    /**
     * Sets the value of the configFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfigFile }
     *     
     */
    public void setConfigFile(ConfigFile value) {
        this.configFile = value;
    }

    /**
     * Gets the value of the activationResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ActivationResponse }
     *     
     */
    public ActivationResponse getActivationResponse() {
        return activationResponse;
    }

    /**
     * Sets the value of the activationResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActivationResponse }
     *     
     */
    public void setActivationResponse(ActivationResponse value) {
        this.activationResponse = value;
    }

    /**
     * Gets the value of the activationRequest property.
     * 
     * @return
     *     possible object is
     *     {@link ActivationRequest }
     *     
     */
    public ActivationRequest getActivationRequest() {
        return activationRequest;
    }

    /**
     * Sets the value of the activationRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActivationRequest }
     *     
     */
    public void setActivationRequest(ActivationRequest value) {
        this.activationRequest = value;
    }

    /**
     * Gets the value of the deploymentProgress property.
     * 
     * @return
     *     possible object is
     *     {@link DeploymentProgress }
     *     
     */
    public DeploymentProgress getDeploymentProgress() {
        return deploymentProgress;
    }

    /**
     * Sets the value of the deploymentProgress property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeploymentProgress }
     *     
     */
    public void setDeploymentProgress(DeploymentProgress value) {
        this.deploymentProgress = value;
    }

    /**
     * Gets the value of the handshake property.
     * 
     * @return
     *     possible object is
     *     {@link Handshake }
     *     
     */
    public Handshake getHandshake() {
        return handshake;
    }

    /**
     * Sets the value of the handshake property.
     * 
     * @param value
     *     allowed object is
     *     {@link Handshake }
     *     
     */
    public void setHandshake(Handshake value) {
        this.handshake = value;
    }

    /**
     * Gets the value of the iatList property.
     * 
     * @return
     *     possible object is
     *     {@link IATList }
     *     
     */
    public IATList getIATList() {
        return iatList;
    }

    /**
     * Sets the value of the iatList property.
     * 
     * @param value
     *     allowed object is
     *     {@link IATList }
     *     
     */
    public void setIATList(IATList value) {
        this.iatList = value;
    }

    /**
     * Gets the value of the manifest property.
     * 
     * @return
     *     possible object is
     *     {@link Manifest }
     *     
     */
    public Manifest getManifest() {
        return manifest;
    }

    /**
     * Sets the value of the manifest property.
     * 
     * @param value
     *     allowed object is
     *     {@link Manifest }
     *     
     */
    public void setManifest(Manifest value) {
        this.manifest = value;
    }

    /**
     * Gets the value of the packet property.
     * 
     * @return
     *     possible object is
     *     {@link Packet }
     *     
     */
    public Packet getPacket() {
        return packet;
    }

    /**
     * Sets the value of the packet property.
     * 
     * @param value
     *     allowed object is
     *     {@link Packet }
     *     
     */
    public void setPacket(Packet value) {
        this.packet = value;
    }

    /**
     * Gets the value of the tokenDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link TokenDefinition }
     *     
     */
    public TokenDefinition getTokenDefinition() {
        return tokenDefinition;
    }

    /**
     * Sets the value of the tokenDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link TokenDefinition }
     *     
     */
    public void setTokenDefinition(TokenDefinition value) {
        this.tokenDefinition = value;
    }

    /**
     * Gets the value of the resultPacket property.
     * 
     * @return
     *     possible object is
     *     {@link ResultPacket }
     *     
     */
    public ResultPacket getResultPacket() {
        return resultPacket;
    }

    /**
     * Sets the value of the resultPacket property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultPacket }
     *     
     */
    public void setResultPacket(ResultPacket value) {
        this.resultPacket = value;
    }

    /**
     * Gets the value of the rsaKeyPair property.
     * 
     * @return
     *     possible object is
     *     {@link RSAKeyPair }
     *     
     */
    public RSAKeyPair getRSAKeyPair() {
        return rsaKeyPair;
    }

    /**
     * Sets the value of the rsaKeyPair property.
     * 
     * @param value
     *     allowed object is
     *     {@link RSAKeyPair }
     *     
     */
    public void setRSAKeyPair(RSAKeyPair value) {
        this.rsaKeyPair = value;
    }

    /**
     * Gets the value of the queryIATExists property.
     * 
     * @return
     *     possible object is
     *     {@link QueryIATExists }
     *     
     */
    public QueryIATExists getQueryIATExists() {
        return queryIATExists;
    }

    /**
     * Sets the value of the queryIATExists property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryIATExists }
     *     
     */
    public void setQueryIATExists(QueryIATExists value) {
        this.queryIATExists = value;
    }

    /**
     * Gets the value of the transactionRequest property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionRequest }
     *     
     */
    public TransactionRequest getTransactionRequest() {
        return transactionRequest;
    }

    /**
     * Sets the value of the transactionRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionRequest }
     *     
     */
    public void setTransactionRequest(TransactionRequest value) {
        this.transactionRequest = value;
    }

    /**
     * Gets the value of the serverReport property.
     * 
     * @return
     *     possible object is
     *     {@link ServerReport }
     *     
     */
    public ServerReport getServerReport() {
        return serverReport;
    }

    /**
     * Sets the value of the serverReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServerReport }
     *     
     */
    public void setServerReport(ServerReport value) {
        this.serverReport = value;
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

    /**
     * Gets the value of the uploadRequest property.
     * 
     * @return
     *     possible object is
     *     {@link UploadRequest }
     *     
     */
    public UploadRequest getUploadRequest() {
        return uploadRequest;
    }

    /**
     * Sets the value of the uploadRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link UploadRequest }
     *     
     */
    public void setUploadRequest(UploadRequest value) {
        this.uploadRequest = value;
    }

}