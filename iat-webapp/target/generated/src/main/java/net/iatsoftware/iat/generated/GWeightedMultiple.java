//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.07.28 at 02:14:21 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GWeightedMultiple complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GWeightedMultiple"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Format" type="{http://www.iatsoftware.net/schema}GFormat"/&gt;
 *         &lt;element name="WeightedChoices"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="WeightedChoice" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Choice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Weight" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="Type" use="required" type="{http://www.iatsoftware.net/schema}ResponseType" /&gt;
 *                 &lt;attribute name="NumChoices" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *                 &lt;attribute name="Scored" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
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
@XmlType(name = "GWeightedMultiple", propOrder = {
    "format",
    "weightedChoices"
})
public class GWeightedMultiple {

    @XmlElement(name = "Format", required = true)
    protected GFormat format;
    @XmlElement(name = "WeightedChoices", required = true)
    protected GWeightedMultiple.WeightedChoices weightedChoices;

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
     * Gets the value of the weightedChoices property.
     * 
     * @return
     *     possible object is
     *     {@link GWeightedMultiple.WeightedChoices }
     *     
     */
    public GWeightedMultiple.WeightedChoices getWeightedChoices() {
        return weightedChoices;
    }

    /**
     * Sets the value of the weightedChoices property.
     * 
     * @param value
     *     allowed object is
     *     {@link GWeightedMultiple.WeightedChoices }
     *     
     */
    public void setWeightedChoices(GWeightedMultiple.WeightedChoices value) {
        this.weightedChoices = value;
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
     *         &lt;element name="WeightedChoice" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Choice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Weight" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="Type" use="required" type="{http://www.iatsoftware.net/schema}ResponseType" /&gt;
     *       &lt;attribute name="NumChoices" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
     *       &lt;attribute name="Scored" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "weightedChoice"
    })
    public static class WeightedChoices {

        @XmlElement(name = "WeightedChoice")
        protected List<GWeightedMultiple.WeightedChoices.WeightedChoice> weightedChoice;
        @XmlAttribute(name = "Type", required = true)
        protected ResponseType type;
        @XmlAttribute(name = "NumChoices", required = true)
        protected int numChoices;
        @XmlAttribute(name = "Scored", required = true)
        protected boolean scored;

        /**
         * Gets the value of the weightedChoice property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the Jakarta XML Binding object.
         * This is why there is not a <CODE>set</CODE> method for the weightedChoice property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getWeightedChoice().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link GWeightedMultiple.WeightedChoices.WeightedChoice }
         * 
         * 
         */
        public List<GWeightedMultiple.WeightedChoices.WeightedChoice> getWeightedChoice() {
            if (weightedChoice == null) {
                weightedChoice = new ArrayList<GWeightedMultiple.WeightedChoices.WeightedChoice>();
            }
            return this.weightedChoice;
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
         * Gets the value of the scored property.
         * 
         */
        public boolean isScored() {
            return scored;
        }

        /**
         * Sets the value of the scored property.
         * 
         */
        public void setScored(boolean value) {
            this.scored = value;
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
         *         &lt;element name="Choice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Weight" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
            "choice",
            "weight"
        })
        public static class WeightedChoice {

            @XmlElement(name = "Choice", required = true)
            protected String choice;
            @XmlElement(name = "Weight")
            protected int weight;

            /**
             * Gets the value of the choice property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getChoice() {
                return choice;
            }

            /**
             * Sets the value of the choice property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setChoice(String value) {
                this.choice = value;
            }

            /**
             * Gets the value of the weight property.
             * 
             */
            public int getWeight() {
                return weight;
            }

            /**
             * Sets the value of the weight property.
             * 
             */
            public void setWeight(int value) {
                this.weight = value;
            }

        }

    }

}
