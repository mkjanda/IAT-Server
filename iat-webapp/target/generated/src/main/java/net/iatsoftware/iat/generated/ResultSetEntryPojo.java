//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.13 at 12:28:08 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.resultdata.ResultTOC;


/**
 * <p>Java class for ResultSetEntryPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResultSetEntryPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TOC" type="{http://www.iatsoftware.net/schema}result-toc-impl"/&gt;
 *         &lt;element name="ResultData" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AdminTime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Token" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ResultId" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultSetEntryPojo", propOrder = {
    "toc",
    "resultData",
    "adminTime"
})
public class ResultSetEntryPojo {

    @XmlElement(name = "TOC", required = true)
    protected ResultTOC toc;
    @XmlElement(name = "ResultData", required = true)
    protected String resultData;
    @XmlElement(name = "AdminTime", required = true)
    protected String adminTime;
    @XmlAttribute(name = "Token")
    protected String token;
    @XmlAttribute(name = "ResultId", required = true)
    protected long resultId;

    /**
     * Gets the value of the toc property.
     * 
     * @return
     *     possible object is
     *     {@link ResultTOC }
     *     
     */
    public ResultTOC getTOC() {
        return toc;
    }

    /**
     * Sets the value of the toc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultTOC }
     *     
     */
    public void setTOC(ResultTOC value) {
        this.toc = value;
    }

    /**
     * Gets the value of the resultData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultData() {
        return resultData;
    }

    /**
     * Sets the value of the resultData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultData(String value) {
        this.resultData = value;
    }

    /**
     * Gets the value of the adminTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdminTime() {
        return adminTime;
    }

    /**
     * Sets the value of the adminTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdminTime(String value) {
        this.adminTime = value;
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
     * Gets the value of the resultId property.
     * 
     */
    public long getResultId() {
        return resultId;
    }

    /**
     * Sets the value of the resultId property.
     * 
     */
    public void setResultId(long value) {
        this.resultId = value;
    }

}
