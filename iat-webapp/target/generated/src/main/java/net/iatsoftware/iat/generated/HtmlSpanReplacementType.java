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
 * <p>Java class for HtmlSpanReplacementType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="HtmlSpanReplacementType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Title"/&gt;
 *     &lt;enumeration value="FName"/&gt;
 *     &lt;enumeration value="LName"/&gt;
 *     &lt;enumeration value="ClientName"/&gt;
 *     &lt;enumeration value="ClientId"/&gt;
 *     &lt;enumeration value="DownloadPassword"/&gt;
 *     &lt;enumeration value="ProductKey"/&gt;
 *     &lt;enumeration value="ActivationsConsumed"/&gt;
 *     &lt;enumeration value="ActivationsRemaining"/&gt;
 *     &lt;enumeration value="AdministrationsConsumed"/&gt;
 *     &lt;enumeration value="AdministrationsRemaining"/&gt;
 *     &lt;enumeration value="DownloadsConsumed"/&gt;
 *     &lt;enumeration value="DownloadsRemaining"/&gt;
 *     &lt;enumeration value="DiskSpaceAlottmentMB"/&gt;
 *     &lt;enumeration value="DiskSpaceConsumed"/&gt;
 *     &lt;enumeration value="NumIATSAlotted"/&gt;
 *     &lt;enumeration value="NumIATS"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "HtmlSpanReplacementType")
@XmlEnum
public enum HtmlSpanReplacementType {

    @XmlEnumValue("Title")
    TITLE("Title"),
    @XmlEnumValue("FName")
    F_NAME("FName"),
    @XmlEnumValue("LName")
    L_NAME("LName"),
    @XmlEnumValue("ClientName")
    CLIENT_NAME("ClientName"),
    @XmlEnumValue("ClientId")
    CLIENT_ID("ClientId"),
    @XmlEnumValue("DownloadPassword")
    DOWNLOAD_PASSWORD("DownloadPassword"),
    @XmlEnumValue("ProductKey")
    PRODUCT_KEY("ProductKey"),
    @XmlEnumValue("ActivationsConsumed")
    ACTIVATIONS_CONSUMED("ActivationsConsumed"),
    @XmlEnumValue("ActivationsRemaining")
    ACTIVATIONS_REMAINING("ActivationsRemaining"),
    @XmlEnumValue("AdministrationsConsumed")
    ADMINISTRATIONS_CONSUMED("AdministrationsConsumed"),
    @XmlEnumValue("AdministrationsRemaining")
    ADMINISTRATIONS_REMAINING("AdministrationsRemaining"),
    @XmlEnumValue("DownloadsConsumed")
    DOWNLOADS_CONSUMED("DownloadsConsumed"),
    @XmlEnumValue("DownloadsRemaining")
    DOWNLOADS_REMAINING("DownloadsRemaining"),
    @XmlEnumValue("DiskSpaceAlottmentMB")
    DISK_SPACE_ALOTTMENT_MB("DiskSpaceAlottmentMB"),
    @XmlEnumValue("DiskSpaceConsumed")
    DISK_SPACE_CONSUMED("DiskSpaceConsumed"),
    @XmlEnumValue("NumIATSAlotted")
    NUM_IATS_ALOTTED("NumIATSAlotted"),
    @XmlEnumValue("NumIATS")
    NUM_IATS("NumIATS");
    private final String value;

    HtmlSpanReplacementType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HtmlSpanReplacementType fromValue(String v) {
        for (HtmlSpanReplacementType c: HtmlSpanReplacementType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
