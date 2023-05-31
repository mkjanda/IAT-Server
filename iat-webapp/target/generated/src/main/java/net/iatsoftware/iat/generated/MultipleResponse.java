//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.24 at 07:02:58 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for MultipleResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MultipleResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SurveyItemFormat" type="{http://www.iatsoftware.net/schema}Format"/&gt;
 *         &lt;element name="NumChoices" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Choices"&gt;
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
 *       &lt;attribute name="Type" use="required" type="{http://www.iatsoftware.net/schema}ResponseType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MultipleResponse", propOrder = {
    "surveyItemFormat",
    "numChoices",
    "choices"
})
public class MultipleResponse {

    @XmlElement(name = "SurveyItemFormat", required = true)
    protected Format surveyItemFormat;
    @XmlElement(name = "NumChoices")
    protected int numChoices;
    @XmlElement(name = "Choices", required = true)
    protected MultipleResponse.Choices choices;
    @XmlAttribute(name = "Type", required = true)
    protected ResponseType type;

    /**
     * Gets the value of the surveyItemFormat property.
     * 
     * @return
     *     possible object is
     *     {@link Format }
     *     
     */
    public Format getSurveyItemFormat() {
        return surveyItemFormat;
    }

    /**
     * Sets the value of the surveyItemFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Format }
     *     
     */
    public void setSurveyItemFormat(Format value) {
        this.surveyItemFormat = value;
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
     * Gets the value of the choices property.
     * 
     * @return
     *     possible object is
     *     {@link MultipleResponse.Choices }
     *     
     */
    public MultipleResponse.Choices getChoices() {
        return choices;
    }

    /**
     * Sets the value of the choices property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultipleResponse.Choices }
     *     
     */
    public void setChoices(MultipleResponse.Choices value) {
        this.choices = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseType }
     *     
     */
    public ResponseType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseType }
     *     
     */
    public void setType(ResponseType value) {
        this.type = value;
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
    public static class Choices {

        @XmlElement(name = "Choice")
        protected List<MultipleResponse.Choices.Choice> choice;

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
         * {@link MultipleResponse.Choices.Choice }
         * 
         * 
         */
        public List<MultipleResponse.Choices.Choice> getChoice() {
            if (choice == null) {
                choice = new ArrayList<MultipleResponse.Choices.Choice>();
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
