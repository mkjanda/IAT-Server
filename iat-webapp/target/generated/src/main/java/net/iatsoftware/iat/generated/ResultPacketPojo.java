//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.15 at 04:11:12 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.Packet;
import net.iatsoftware.iat.resultdata.ResultTOC;


/**
 * <p>Java class for ResultPacketPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResultPacketPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}packet-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TableOfContents" type="{http://www.iatsoftware.net/schema}result-toc-impl"/&gt;
 *         &lt;element name="Token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="ResultID" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="Timestamp" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultPacketPojo", propOrder = {
    "tableOfContents",
    "token"
})
public abstract class ResultPacketPojo
    extends Packet
{

    @XmlElement(name = "TableOfContents", required = true)
    protected ResultTOC tableOfContents;
    @XmlElement(name = "Token")
    protected String token;
    @XmlAttribute(name = "ResultID", required = true)
    protected long resultID;
    @XmlAttribute(name = "Timestamp", required = true)
    protected String timestamp;

    /**
     * Gets the value of the tableOfContents property.
     * 
     * @return
     *     possible object is
     *     {@link ResultTOC }
     *     
     */
    public ResultTOC getTableOfContents() {
        return tableOfContents;
    }

    /**
     * Sets the value of the tableOfContents property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultTOC }
     *     
     */
    public void setTableOfContents(ResultTOC value) {
        this.tableOfContents = value;
    }

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the resultID property.
     * 
     */
    public long getResultID() {
        return resultID;
    }

    /**
     * Sets the value of the resultID property.
     * 
     */
    public void setResultID(long value) {
        this.resultID = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimestamp(String value) {
        this.timestamp = value;
    }

}
