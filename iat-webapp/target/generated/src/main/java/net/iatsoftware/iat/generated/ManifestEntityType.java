//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.24 at 07:02:58 PM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManifestEntityType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="ManifestEntityType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="File"/&gt;
 *     &lt;enumeration value="Directory"/&gt;
 *     &lt;enumeration value="UpdateFile"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ManifestEntityType")
@XmlEnum
public enum ManifestEntityType {

    @XmlEnumValue("File")
    FILE("File"),
    @XmlEnumValue("Directory")
    DIRECTORY("Directory"),
    @XmlEnumValue("UpdateFile")
    UPDATE_FILE("UpdateFile");
    private final String value;

    ManifestEntityType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ManifestEntityType fromValue(String v) {
        for (ManifestEntityType c: ManifestEntityType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
