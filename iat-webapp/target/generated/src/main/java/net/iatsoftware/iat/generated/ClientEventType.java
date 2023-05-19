//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.15 at 04:11:12 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClientEventType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ClientEventType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Create"/&gt;
 *     &lt;enumeration value="Delete"/&gt;
 *     &lt;enumeration value="Open"/&gt;
 *     &lt;enumeration value="Close"/&gt;
 *     &lt;enumeration value="Display"/&gt;
 *     &lt;enumeration value="ImageLoad"/&gt;
 *     &lt;enumeration value="Attached"/&gt;
 *     &lt;enumeration value="Detached"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ClientEventType")
@XmlEnum
public enum ClientEventType {

    @XmlEnumValue("Create")
    CREATE("Create"),
    @XmlEnumValue("Delete")
    DELETE("Delete"),
    @XmlEnumValue("Open")
    OPEN("Open"),
    @XmlEnumValue("Close")
    CLOSE("Close"),
    @XmlEnumValue("Display")
    DISPLAY("Display"),
    @XmlEnumValue("ImageLoad")
    IMAGE_LOAD("ImageLoad"),
    @XmlEnumValue("Attached")
    ATTACHED("Attached"),
    @XmlEnumValue("Detached")
    DETACHED("Detached");
    private final String value;

    ClientEventType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ClientEventType fromValue(String v) {
        for (ClientEventType c: ClientEventType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
