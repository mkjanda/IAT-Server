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
 * <p>Java class for IATEventType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="IATEventType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="BeginIATBlock"/&gt;
 *     &lt;enumeration value="EndIATBlock"/&gt;
 *     &lt;enumeration value="IATItem"/&gt;
 *     &lt;enumeration value="BeginInstructionBlock"/&gt;
 *     &lt;enumeration value="TextInstructionScreen"/&gt;
 *     &lt;enumeration value="KeyedInstructionScreen"/&gt;
 *     &lt;enumeration value="MockItemInstructionScreen"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "IATEventType")
@XmlEnum
public enum IATEventType {

    @XmlEnumValue("BeginIATBlock")
    BEGIN_IAT_BLOCK("BeginIATBlock"),
    @XmlEnumValue("EndIATBlock")
    END_IAT_BLOCK("EndIATBlock"),
    @XmlEnumValue("IATItem")
    IAT_ITEM("IATItem"),
    @XmlEnumValue("BeginInstructionBlock")
    BEGIN_INSTRUCTION_BLOCK("BeginInstructionBlock"),
    @XmlEnumValue("TextInstructionScreen")
    TEXT_INSTRUCTION_SCREEN("TextInstructionScreen"),
    @XmlEnumValue("KeyedInstructionScreen")
    KEYED_INSTRUCTION_SCREEN("KeyedInstructionScreen"),
    @XmlEnumValue("MockItemInstructionScreen")
    MOCK_ITEM_INSTRUCTION_SCREEN("MockItemInstructionScreen");
    private final String value;

    IATEventType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static IATEventType fromValue(String v) {
        for (IATEventType c: IATEventType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
