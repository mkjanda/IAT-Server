//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.07.10 at 04:13:23 AM EDT 
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
 * <p>Java class for GLikert complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLikert"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Format" type="{http://www.iatsoftware.net/schema}GFormat"/&gt;
 *         &lt;element name="NumChoices" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsReverseScored" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ChoiceDescriptions"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Choice" maxOccurs="unbounded" minOccurs="0"&gt;
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
@XmlType(name = "GLikert", propOrder = {
    "format",
    "numChoices",
    "isReverseScored",
    "choiceDescriptions"
})
public class GLikert {

    @XmlElement(name = "Format", required = true)
    protected GFormat format;
    @XmlElement(name = "NumChoices")
    protected int numChoices;
    @XmlElement(name = "IsReverseScored")
    protected boolean isReverseScored;
    @XmlElement(name = "ChoiceDescriptions", required = true)
    protected GLikert.ChoiceDescriptions choiceDescriptions;

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
     * Gets the value of the numChoices property.
     * 
     */
    public int getNumChoices() {
        return numChoices;
    }

    /**
     * Sets the value of the numChoices property.
     * 
     */
    public void setNumChoices(int value) {
        this.numChoices = value;
    }

    /**
     * Gets the value of the isReverseScored property.
     * 
     */
    public boolean isIsReverseScored() {
        return isReverseScored;
    }

    /**
     * Sets the value of the isReverseScored property.
     * 
     */
    public void setIsReverseScored(boolean value) {
        this.isReverseScored = value;
    }

    /**
     * Gets the value of the choiceDescriptions property.
     * 
     * @return
     *     possible object is
     *     {@link GLikert.ChoiceDescriptions }
     *     
     */
    public GLikert.ChoiceDescriptions getChoiceDescriptions() {
        return choiceDescriptions;
    }

    /**
     * Sets the value of the choiceDescriptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLikert.ChoiceDescriptions }
     *     
     */
    public void setChoiceDescriptions(GLikert.ChoiceDescriptions value) {
        this.choiceDescriptions = value;
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
     *         &lt;element name="Choice" maxOccurs="unbounded" minOccurs="0"&gt;
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
        "choice"
    })
    public static class ChoiceDescriptions {

        @XmlElement(name = "Choice")
        protected List<GLikert.ChoiceDescriptions.Choice> choice;

        /**
         * Gets the value of the choice property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the Jakarta XML Binding object.
         * This is why there is not a <CODE>set</CODE> method for the choice property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getChoice().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link GLikert.ChoiceDescriptions.Choice }
         * 
         * 
         */
        public List<GLikert.ChoiceDescriptions.Choice> getChoice() {
            if (choice == null) {
                choice = new ArrayList<GLikert.ChoiceDescriptions.Choice>();
            }
            return this.choice;
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
        public static class Choice {

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
