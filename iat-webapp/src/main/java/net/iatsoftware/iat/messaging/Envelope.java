/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.configfile.ConfigFile;
import net.iatsoftware.iat.deployment.DeploymentProgress;
import net.iatsoftware.iat.generated.EnvelopeType;
import net.iatsoftware.iat.resultdata.ResultPacket;
import net.iatsoftware.iat.resultdata.ResultSetDescriptor;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "Envelope")
@XmlAccessorType(XmlAccessType.NONE)
public class Envelope extends net.iatsoftware.iat.generated.GEnvelope {
    private EnvelopeType envelopeType = EnvelopeType.NONE;

    public Envelope() {
    }

    public EnvelopeType getEnvelopeType() {
        if (!envelopeType.equals(EnvelopeType.NONE))
            return envelopeType;
        if (this.activationRequest != null)
            envelopeType = EnvelopeType.ACTIVATION_REQUEST;
        if (this.activationResponse != null)
            envelopeType = EnvelopeType.ACTIVATION_RESPONSE;
        if (this.configFile != null)
            envelopeType = EnvelopeType.CONFIG_FILE;
        if (this.deploymentProgress != null)
            envelopeType = EnvelopeType.DEPLOYMENT_PROGRESS;
        if (this.handshake != null)
            envelopeType = EnvelopeType.HANDSHAKE;
        if (this.manifest != null)
            envelopeType = EnvelopeType.MANIFEST;
        if (this.resultPacket != null)
            envelopeType = EnvelopeType.RESULT_PACKET;
        if (this.packet != null)
            envelopeType = EnvelopeType.PACKET;
        if (this.resultSetDescriptor != null)
            envelopeType = EnvelopeType.RESULT_SET_DESCRIPTOR;
        if (this.rsaKeyPair != null)
            envelopeType = EnvelopeType.RSA_KEY_PAIR;
        if (this.serverReport != null)
            envelopeType = EnvelopeType.SERVER_REPORT;
        if (this.serverException != null)
            envelopeType = EnvelopeType.SERVER_EXCEPTION;
        if (this.uploadRequest != null)
            envelopeType = EnvelopeType.UPLOAD_REQUEST;
        if (this.adminListener != null)
            envelopeType = EnvelopeType.ADMIN_LISTENER;
        return envelopeType;
    }

    public Envelope(Message msg) {
        if (msg instanceof ActivationRequest)
            setActivationRequest((ActivationRequest) msg);
        if (msg instanceof ActivationResponse)
            setActivationResponse((ActivationResponse) msg);
        if (msg instanceof ConfigFile)
            setConfigFile((ConfigFile) msg);
        if (msg instanceof DeploymentProgress)
            setDeploymentProgress((DeploymentProgress) msg);
        if (msg instanceof Handshake)
            setHandshake((Handshake) msg);
        if (msg instanceof Manifest)
            setManifest((Manifest) msg);
        if (msg instanceof ResultPacket)
            setResultPacket((ResultPacket) msg);
        else if (msg instanceof Packet)
            setPacket((Packet) msg);
        if (msg instanceof ResultSetDescriptor)
            setResultSetDescriptor((ResultSetDescriptor) msg);
        if (msg instanceof TransactionRequest)
            setTransactionRequest((TransactionRequest) msg);
        if (msg instanceof RSAKeyPair)
            setRSAKeyPair((RSAKeyPair) msg);
        if (msg instanceof ServerReport)
            setServerReport((ServerReport) msg);
        if (msg instanceof ServerExceptionMessage)
            setServerException((ServerExceptionMessage) msg);
        if (msg instanceof UploadRequest)
            setUploadRequest((UploadRequest) msg);
    }

    public Message getMessage() {
        if (this.activationRequest != null)
            return getActivationRequest();
        if (this.activationResponse != null)
            return getActivationResponse();
        if (this.configFile != null)
            return getConfigFile();
        if (this.deploymentProgress != null)
            return getDeploymentProgress();
        if (this.handshake != null)
            return getHandshake();
        if (this.manifest != null)
            return getManifest();
        if (this.packet != null)
            return getPacket();
        if (this.resultPacket != null)
            return getResultPacket();
        if (this.queryIATExists != null)
            return getQueryIATExists();
        if (this.resultSetDescriptor != null)
            return getResultSetDescriptor();
        if (this.transactionRequest != null)
            return getTransactionRequest();
        if (this.rsaKeyPair != null)
            return getRSAKeyPair();
        if (this.serverReport != null)
            return getServerReport();
        if (this.uploadRequest != null)
            return uploadRequest;
        if (this.tokenDefinition != null)
            return this.tokenDefinition;
        return null;
    }
}
