//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.09.25 at 12:11:19 AM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GSurveyBoolean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GSurveyBoolean"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}GSurveyResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TrueStatement" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FalseStatement" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GSurveyBoolean", propOrder = {
    "trueStatement",
    "falseStatement"
})
public class GSurveyBoolean
    extends GSurveyResponse
{

    @XmlElement(name = "TrueStatement", required = true)
    protected String trueStatement;
    @XmlElement(name = "FalseStatement", required = true)
    protected String falseStatement;

    /**
     * Gets the value of the trueStatement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrueStatement() {
        return trueStatement;
    }

    /**
     * Sets the value of the trueStatement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrueStatement(String value) {
        this.trueStatement = value;
    }

    /**
     * Gets the value of the falseStatement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFalseStatement() {
        return falseStatement;
    }

    /**
     * Sets the value of the falseStatement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFalseStatement(String value) {
        this.falseStatement = value;
    }

}
