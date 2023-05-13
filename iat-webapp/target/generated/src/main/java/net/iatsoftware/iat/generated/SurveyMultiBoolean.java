//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.13 at 12:28:08 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SurveyMultiBoolean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SurveyMultiBoolean"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}SurveyResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NumValues" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="MinSelections" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="MaxSelections" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="Labels"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Label" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SurveyMultiBoolean", propOrder = {
    "numValues",
    "minSelections",
    "maxSelections",
    "labels"
})
public class SurveyMultiBoolean
    extends SurveyResponse
{

    @XmlElement(name = "NumValues", required = true)
    protected BigInteger numValues;
    @XmlElement(name = "MinSelections", required = true)
    protected BigInteger minSelections;
    @XmlElement(name = "MaxSelections", required = true)
    protected BigInteger maxSelections;
    @XmlElement(name = "Labels", required = true)
    protected SurveyMultiBoolean.Labels labels;

    /**
     * Gets the value of the numValues property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumValues() {
        return numValues;
    }

    /**
     * Sets the value of the numValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumValues(BigInteger value) {
        this.numValues = value;
    }

    /**
     * Gets the value of the minSelections property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMinSelections() {
        return minSelections;
    }

    /**
     * Sets the value of the minSelections property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMinSelections(BigInteger value) {
        this.minSelections = value;
    }

    /**
     * Gets the value of the maxSelections property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxSelections() {
        return maxSelections;
    }

    /**
     * Sets the value of the maxSelections property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxSelections(BigInteger value) {
        this.maxSelections = value;
    }

    /**
     * Gets the value of the labels property.
     * 
     * @return
     *     possible object is
     *     {@link SurveyMultiBoolean.Labels }
     *     
     */
    public SurveyMultiBoolean.Labels getLabels() {
        return labels;
    }

    /**
     * Sets the value of the labels property.
     * 
     * @param value
     *     allowed object is
     *     {@link SurveyMultiBoolean.Labels }
     *     
     */
    public void setLabels(SurveyMultiBoolean.Labels value) {
        this.labels = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Label" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "label"
    })
    public static class Labels {

        @XmlElement(name = "Label", required = true)
        protected List<String> label;

        /**
         * Gets the value of the label property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the label property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLabel().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getLabel() {
            if (label == null) {
                label = new ArrayList<String>();
            }
            return this.label;
        }

    }

}
