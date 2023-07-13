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
 * <p>Java class for AjaxResponseType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="AjaxResponseType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Error"/&gt;
 *     &lt;enumeration value="Text"/&gt;
 *     &lt;enumeration value="XML"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "AjaxResponseType")
@XmlEnum
public enum AjaxResponseType {

    @XmlEnumValue("Error")
    ERROR("Error"),
    @XmlEnumValue("Text")
    TEXT("Text"),
    XML("XML");
    private final String value;

    AjaxResponseType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AjaxResponseType fromValue(String v) {
        for (AjaxResponseType c: AjaxResponseType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
