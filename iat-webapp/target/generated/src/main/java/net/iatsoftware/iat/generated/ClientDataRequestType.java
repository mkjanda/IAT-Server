//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.17 at 05:48:02 AM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClientDataRequestType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="ClientDataRequestType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ClientUsage"/&gt;
 *     &lt;enumeration value="TestUsage"/&gt;
 *     &lt;enumeration value="ClientKeys"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ClientDataRequestType")
@XmlEnum
public enum ClientDataRequestType {

    @XmlEnumValue("ClientUsage")
    CLIENT_USAGE("ClientUsage"),
    @XmlEnumValue("TestUsage")
    TEST_USAGE("TestUsage"),
    @XmlEnumValue("ClientKeys")
    CLIENT_KEYS("ClientKeys");
    private final String value;

    ClientDataRequestType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ClientDataRequestType fromValue(String v) {
        for (ClientDataRequestType c: ClientDataRequestType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
