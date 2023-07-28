//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.07.28 at 02:14:21 PM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResourceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="ResourceType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="image"/&gt;
 *     &lt;enumeration value="itemSlide"/&gt;
 *     &lt;enumeration value="javascript"/&gt;
 *     &lt;enumeration value="updateFile"/&gt;
 *     &lt;enumeration value="testConfiguration"/&gt;
 *     &lt;enumeration value="errorMark"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ResourceType")
@XmlEnum
public enum ResourceType {

    @XmlEnumValue("image")
    IMAGE("image"),
    @XmlEnumValue("itemSlide")
    ITEM_SLIDE("itemSlide"),
    @XmlEnumValue("javascript")
    JAVASCRIPT("javascript"),
    @XmlEnumValue("updateFile")
    UPDATE_FILE("updateFile"),
    @XmlEnumValue("testConfiguration")
    TEST_CONFIGURATION("testConfiguration"),
    @XmlEnumValue("errorMark")
    ERROR_MARK("errorMark");
    private final String value;

    ResourceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ResourceType fromValue(String v) {
        for (ResourceType c: ResourceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
