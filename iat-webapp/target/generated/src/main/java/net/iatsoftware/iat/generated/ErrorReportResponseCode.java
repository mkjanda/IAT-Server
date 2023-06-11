//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.06 at 10:05:44 PM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ErrorReportResponseCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="ErrorReportResponseCode"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="success"/&gt;
 *     &lt;enumeration value="invalidHandshake"/&gt;
 *     &lt;enumeration value="killFiled"/&gt;
 *     &lt;enumeration value="serverError"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ErrorReportResponseCode")
@XmlEnum
public enum ErrorReportResponseCode {

    @XmlEnumValue("success")
    SUCCESS("success"),
    @XmlEnumValue("invalidHandshake")
    INVALID_HANDSHAKE("invalidHandshake"),
    @XmlEnumValue("killFiled")
    KILL_FILED("killFiled"),
    @XmlEnumValue("serverError")
    SERVER_ERROR("serverError");
    private final String value;

    ErrorReportResponseCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ErrorReportResponseCode fromValue(String v) {
        for (ErrorReportResponseCode c: ErrorReportResponseCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
