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
 * <p>Java class for ClientStateAlteration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="ClientStateAlteration"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="None"/&gt;
 *     &lt;enumeration value="Freeze"/&gt;
 *     &lt;enumeration value="Delete"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ClientStateAlteration")
@XmlEnum
public enum ClientStateAlteration {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Freeze")
    FREEZE("Freeze"),
    @XmlEnumValue("Delete")
    DELETE("Delete");
    private final String value;

    ClientStateAlteration(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ClientStateAlteration fromValue(String v) {
        for (ClientStateAlteration c: ClientStateAlteration.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
