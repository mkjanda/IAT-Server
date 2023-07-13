//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.07.10 at 04:13:23 AM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnvelopeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="EnvelopeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="ActivationRequest"/&gt;
 *     &lt;enumeration value="ActivationResponse"/&gt;
 *     &lt;enumeration value="AdminListener"/&gt;
 *     &lt;enumeration value="ConfigFile"/&gt;
 *     &lt;enumeration value="DeploymentProgress"/&gt;
 *     &lt;enumeration value="Handshake"/&gt;
 *     &lt;enumeration value="Manifest"/&gt;
 *     &lt;enumeration value="ResultPacket"/&gt;
 *     &lt;enumeration value="Packet"/&gt;
 *     &lt;enumeration value="ResultSetDescriptor"/&gt;
 *     &lt;enumeration value="TransactionRequest"/&gt;
 *     &lt;enumeration value="RSAKeyPair"/&gt;
 *     &lt;enumeration value="ServerReport"/&gt;
 *     &lt;enumeration value="ServerException"/&gt;
 *     &lt;enumeration value="UploadRequest"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "EnvelopeType")
@XmlEnum
public enum EnvelopeType {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("ActivationRequest")
    ACTIVATION_REQUEST("ActivationRequest"),
    @XmlEnumValue("ActivationResponse")
    ACTIVATION_RESPONSE("ActivationResponse"),
    @XmlEnumValue("AdminListener")
    ADMIN_LISTENER("AdminListener"),
    @XmlEnumValue("ConfigFile")
    CONFIG_FILE("ConfigFile"),
    @XmlEnumValue("DeploymentProgress")
    DEPLOYMENT_PROGRESS("DeploymentProgress"),
    @XmlEnumValue("Handshake")
    HANDSHAKE("Handshake"),
    @XmlEnumValue("Manifest")
    MANIFEST("Manifest"),
    @XmlEnumValue("ResultPacket")
    RESULT_PACKET("ResultPacket"),
    @XmlEnumValue("Packet")
    PACKET("Packet"),
    @XmlEnumValue("ResultSetDescriptor")
    RESULT_SET_DESCRIPTOR("ResultSetDescriptor"),
    @XmlEnumValue("TransactionRequest")
    TRANSACTION_REQUEST("TransactionRequest"),
    @XmlEnumValue("RSAKeyPair")
    RSA_KEY_PAIR("RSAKeyPair"),
    @XmlEnumValue("ServerReport")
    SERVER_REPORT("ServerReport"),
    @XmlEnumValue("ServerException")
    SERVER_EXCEPTION("ServerException"),
    @XmlEnumValue("UploadRequest")
    UPLOAD_REQUEST("UploadRequest");
    private final String value;

    EnvelopeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnvelopeType fromValue(String v) {
        for (EnvelopeType c: EnvelopeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
