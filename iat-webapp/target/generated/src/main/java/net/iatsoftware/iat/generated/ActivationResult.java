//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.13 at 12:28:08 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActivationResult.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActivationResult"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Unset"/&gt;
 *     &lt;enumeration value="NoSuchClient"/&gt;
 *     &lt;enumeration value="InvalidRequest"/&gt;
 *     &lt;enumeration value="ServerFailure"/&gt;
 *     &lt;enumeration value="NoActivationsRemaining"/&gt;
 *     &lt;enumeration value="InvalidProductCode"/&gt;
 *     &lt;enumeration value="ClientFrozen"/&gt;
 *     &lt;enumeration value="ClientDeleted"/&gt;
 *     &lt;enumeration value="InconsistentUserData"/&gt;
 *     &lt;enumeration value="Success"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ActivationResult")
@XmlEnum
public enum ActivationResult {

    @XmlEnumValue("Unset")
    UNSET("Unset"),
    @XmlEnumValue("NoSuchClient")
    NO_SUCH_CLIENT("NoSuchClient"),
    @XmlEnumValue("InvalidRequest")
    INVALID_REQUEST("InvalidRequest"),
    @XmlEnumValue("ServerFailure")
    SERVER_FAILURE("ServerFailure"),
    @XmlEnumValue("NoActivationsRemaining")
    NO_ACTIVATIONS_REMAINING("NoActivationsRemaining"),
    @XmlEnumValue("InvalidProductCode")
    INVALID_PRODUCT_CODE("InvalidProductCode"),
    @XmlEnumValue("ClientFrozen")
    CLIENT_FROZEN("ClientFrozen"),
    @XmlEnumValue("ClientDeleted")
    CLIENT_DELETED("ClientDeleted"),
    @XmlEnumValue("InconsistentUserData")
    INCONSISTENT_USER_DATA("InconsistentUserData"),
    @XmlEnumValue("Success")
    SUCCESS("Success");
    private final String value;

    ActivationResult(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ActivationResult fromValue(String v) {
        for (ActivationResult c: ActivationResult.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
