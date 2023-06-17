//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.17 at 05:48:02 AM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for GMultiBoolean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GMultiBoolean"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Format" type="{http://www.iatsoftware.net/schema}GFormat"/&gt;
 *         &lt;element name="NumValues" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="MinSelections" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="MaxSelections" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Labels"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Label" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;simpleContent&gt;
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/simpleContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GMultiBoolean", propOrder = {
    "format",
    "numValues",
    "minSelections",
    "maxSelections",
    "labels"
})
public class GMultiBoolean {

    @XmlElement(name = "Format", required = true)
    protected GFormat format;
    @XmlElement(name = "NumValues")
    protected int numValues;
    @XmlElement(name = "MinSelections")
    protected int minSelections;
    @XmlElement(name = "MaxSelections")
    protected int maxSelections;
    @XmlElement(name = "Labels", required = true)
    protected GMultiBoolean.Labels labels;

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link GFormat }
     *     
     */
    public GFormat getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link GFormat }
     *     
     */
    public void setFormat(GFormat value) {
        this.format = value;
    }

    /**
     * Gets the value of the numValues property.
     * 
     */
    public int getNumValues() {
        return numValues;
    }

    /**
     * Sets the value of the numValues property.
     * 
     */
    public void setNumValues(int value) {
        this.numValues = value;
    }

    /**
     * Gets the value of the minSelections property.
     * 
     */
    public int getMinSelections() {
        return minSelections;
    }

    /**
     * Sets the value of the minSelections property.
     * 
     */
    public void setMinSelections(int value) {
        this.minSelections = value;
    }

    /**
     * Gets the value of the maxSelections property.
     * 
     */
    public int getMaxSelections() {
        return maxSelections;
    }

    /**
     * Sets the value of the maxSelections property.
     * 
     */
    public void setMaxSelections(int value) {
        this.maxSelections = value;
    }

    /**
     * Gets the value of the labels property.
     * 
     * @return
     *     possible object is
     *     {@link GMultiBoolean.Labels }
     *     
     */
    public GMultiBoolean.Labels getLabels() {
        return labels;
    }

    /**
     * Sets the value of the labels property.
     * 
     * @param value
     *     allowed object is
     *     {@link GMultiBoolean.Labels }
     *     
     */
    public void setLabels(GMultiBoolean.Labels value) {
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
     *         &lt;element name="Label" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;simpleContent&gt;
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *               &lt;/extension&gt;
     *             &lt;/simpleContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
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

        @XmlElement(name = "Label")
        protected List<GMultiBoolean.Labels.Label> label;

        /**
         * Gets the value of the label property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the Jakarta XML Binding object.
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
         * {@link GMultiBoolean.Labels.Label }
         * 
         * 
         */
        public List<GMultiBoolean.Labels.Label> getLabel() {
            if (label == null) {
                label = new ArrayList<GMultiBoolean.Labels.Label>();
            }
            return this.label;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;simpleContent&gt;
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
         *     &lt;/extension&gt;
         *   &lt;/simpleContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Label {

            @XmlValue
            protected String value;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

        }

    }

}