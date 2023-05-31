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
 * <p>Java class for SurveyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="SurveyType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="BeforeSurvey"/&gt;
 *     &lt;enumeration value="AfterSurvey"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SurveyType")
@XmlEnum
public enum SurveyType {

    @XmlEnumValue("BeforeSurvey")
    BEFORE_SURVEY("BeforeSurvey"),
    @XmlEnumValue("AfterSurvey")
    AFTER_SURVEY("AfterSurvey");
    private final String value;

    SurveyType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SurveyType fromValue(String v) {
        for (SurveyType c: SurveyType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
