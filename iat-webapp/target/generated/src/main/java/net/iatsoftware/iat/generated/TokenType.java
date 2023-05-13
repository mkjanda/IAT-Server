//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.13 at 08:23:51 AM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TokenType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TokenType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="NONE"/&gt;
 *     &lt;enumeration value="VALUE"/&gt;
 *     &lt;enumeration value="HEX"/&gt;
 *     &lt;enumeration value="BASE64"/&gt;
 *     &lt;enumeration value="BASE64_UTF8"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TokenType")
@XmlEnum
public enum TokenType {

    NONE("NONE"),
    VALUE("VALUE"),
    HEX("HEX"),
    @XmlEnumValue("BASE64")
    BASE_64("BASE64"),
    @XmlEnumValue("BASE64_UTF8")
    BASE_64_UTF_8("BASE64_UTF8");
    private final String value;

    TokenType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TokenType fromValue(String v) {
        for (TokenType c: TokenType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
