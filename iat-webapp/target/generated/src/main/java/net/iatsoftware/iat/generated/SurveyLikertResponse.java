//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.12 at 11:08:36 PM EDT 
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
 * <p>Java class for SurveyLikertResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SurveyLikertResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}SurveyResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NumChoice" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="IsReverseScored" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ChoiceDescriptions"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Choice" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
@XmlType(name = "SurveyLikertResponse", propOrder = {
    "numChoice",
    "isReverseScored",
    "choiceDescriptions"
})
public class SurveyLikertResponse
    extends SurveyResponse
{

    @XmlElement(name = "NumChoice", required = true)
    protected BigInteger numChoice;
    @XmlElement(name = "IsReverseScored")
    protected boolean isReverseScored;
    @XmlElement(name = "ChoiceDescriptions", required = true)
    protected SurveyLikertResponse.ChoiceDescriptions choiceDescriptions;

    /**
     * Gets the value of the numChoice property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumChoice() {
        return numChoice;
    }

    /**
     * Sets the value of the numChoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumChoice(BigInteger value) {
        this.numChoice = value;
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
     *     {@link SurveyLikertResponse.ChoiceDescriptions }
     *     
     */
    public SurveyLikertResponse.ChoiceDescriptions getChoiceDescriptions() {
        return choiceDescriptions;
    }

    /**
     * Sets the value of the choiceDescriptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link SurveyLikertResponse.ChoiceDescriptions }
     *     
     */
    public void setChoiceDescriptions(SurveyLikertResponse.ChoiceDescriptions value) {
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
     *         &lt;element name="Choice" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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

        @XmlElement(name = "Choice", required = true)
        protected List<String> choice;

        /**
         * Gets the value of the choice property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
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
         * {@link String }
         * 
         * 
         */
        public List<String> getChoice() {
            if (choice == null) {
                choice = new ArrayList<String>();
            }
            return this.choice;
        }

    }

}
