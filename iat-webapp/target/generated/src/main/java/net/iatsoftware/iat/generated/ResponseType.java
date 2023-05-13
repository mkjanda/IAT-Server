//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.12 at 11:08:36 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResponseType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ResponseType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Boolean"/&gt;
 *     &lt;enumeration value="Bounded Length"/&gt;
 *     &lt;enumeration value="Bounded Number"/&gt;
 *     &lt;enumeration value="Date"/&gt;
 *     &lt;enumeration value="Fixed Digit"/&gt;
 *     &lt;enumeration value="Instruction"/&gt;
 *     &lt;enumeration value="Likert"/&gt;
 *     &lt;enumeration value="Multiple Selection"/&gt;
 *     &lt;enumeration value="Weighted Multiple Choice"/&gt;
 *     &lt;enumeration value="Regular Expression"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ResponseType")
@XmlEnum
public enum ResponseType {

    @XmlEnumValue("Boolean")
    BOOLEAN("Boolean"),
    @XmlEnumValue("Bounded Length")
    BOUNDED_LENGTH("Bounded Length"),
    @XmlEnumValue("Bounded Number")
    BOUNDED_NUMBER("Bounded Number"),
    @XmlEnumValue("Date")
    DATE("Date"),
    @XmlEnumValue("Fixed Digit")
    FIXED_DIGIT("Fixed Digit"),
    @XmlEnumValue("Instruction")
    INSTRUCTION("Instruction"),
    @XmlEnumValue("Likert")
    LIKERT("Likert"),
    @XmlEnumValue("Multiple Selection")
    MULTIPLE_SELECTION("Multiple Selection"),
    @XmlEnumValue("Weighted Multiple Choice")
    WEIGHTED_MULTIPLE_CHOICE("Weighted Multiple Choice"),
    @XmlEnumValue("Regular Expression")
    REGULAR_EXPRESSION("Regular Expression");
    private final String value;

    ResponseType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ResponseType fromValue(String v) {
        for (ResponseType c: ResponseType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
