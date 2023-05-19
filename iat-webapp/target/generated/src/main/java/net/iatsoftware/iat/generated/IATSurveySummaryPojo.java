//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.15 at 04:11:12 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IATSurveySummaryPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IATSurveySummaryPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SurveyName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FileNameBase" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NumItems" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsBeforeSurvey" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="AlternationSet" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ResponseTypes"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ResponseType" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;enumeration value="None"/&gt;
 *                         &lt;enumeration value="Boolean"/&gt;
 *                         &lt;enumeration value="Likert"/&gt;
 *                         &lt;enumeration value="Date"/&gt;
 *                         &lt;enumeration value="Multiple"/&gt;
 *                         &lt;enumeration value="WeightedMultiple"/&gt;
 *                         &lt;enumeration value="RegEx"/&gt;
 *                         &lt;enumeration value="MultiBoolean"/&gt;
 *                         &lt;enumeration value="FixedDig"/&gt;
 *                         &lt;enumeration value="BoundedNum"/&gt;
 *                         &lt;enumeration value="Likert"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ScriptId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="SurveyType" use="required" type="{http://www.iatsoftware.net/schema}SurveyType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IATSurveySummaryPojo", propOrder = {
    "surveyName",
    "fileNameBase",
    "numItems",
    "isBeforeSurvey",
    "alternationSet",
    "responseTypes"
})
public class IATSurveySummaryPojo {

    @XmlElement(name = "SurveyName", required = true)
    protected String surveyName;
    @XmlElement(name = "FileNameBase", required = true)
    protected String fileNameBase;
    @XmlElement(name = "NumItems")
    protected int numItems;
    @XmlElement(name = "IsBeforeSurvey")
    protected boolean isBeforeSurvey;
    @XmlElement(name = "AlternationSet")
    protected int alternationSet;
    @XmlElement(name = "ResponseTypes", required = true)
    protected IATSurveySummaryPojo.ResponseTypes responseTypes;
    @XmlAttribute(name = "SurveyType", required = true)
    protected SurveyType surveyType;

    /**
     * Gets the value of the surveyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurveyName() {
        return surveyName;
    }

    /**
     * Sets the value of the surveyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurveyName(String value) {
        this.surveyName = value;
    }

    /**
     * Gets the value of the fileNameBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileNameBase() {
        return fileNameBase;
    }

    /**
     * Sets the value of the fileNameBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileNameBase(String value) {
        this.fileNameBase = value;
    }

    /**
     * Gets the value of the numItems property.
     * 
     */
    public int getNumItems() {
        return numItems;
    }

    /**
     * Sets the value of the numItems property.
     * 
     */
    public void setNumItems(int value) {
        this.numItems = value;
    }

    /**
     * Gets the value of the isBeforeSurvey property.
     * 
     */
    public boolean isIsBeforeSurvey() {
        return isBeforeSurvey;
    }

    /**
     * Sets the value of the isBeforeSurvey property.
     * 
     */
    public void setIsBeforeSurvey(boolean value) {
        this.isBeforeSurvey = value;
    }

    /**
     * Gets the value of the alternationSet property.
     * 
     */
    public int getAlternationSet() {
        return alternationSet;
    }

    /**
     * Sets the value of the alternationSet property.
     * 
     */
    public void setAlternationSet(int value) {
        this.alternationSet = value;
    }

    /**
     * Gets the value of the responseTypes property.
     * 
     * @return
     *     possible object is
     *     {@link IATSurveySummaryPojo.ResponseTypes }
     *     
     */
    public IATSurveySummaryPojo.ResponseTypes getResponseTypes() {
        return responseTypes;
    }

    /**
     * Sets the value of the responseTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link IATSurveySummaryPojo.ResponseTypes }
     *     
     */
    public void setResponseTypes(IATSurveySummaryPojo.ResponseTypes value) {
        this.responseTypes = value;
    }

    /**
     * Gets the value of the surveyType property.
     * 
     * @return
     *     possible object is
     *     {@link SurveyType }
     *     
     */
    public SurveyType getSurveyType() {
        return surveyType;
    }

    /**
     * Sets the value of the surveyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SurveyType }
     *     
     */
    public void setSurveyType(SurveyType value) {
        this.surveyType = value;
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
     *         &lt;element name="ResponseType" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;enumeration value="None"/&gt;
     *               &lt;enumeration value="Boolean"/&gt;
     *               &lt;enumeration value="Likert"/&gt;
     *               &lt;enumeration value="Date"/&gt;
     *               &lt;enumeration value="Multiple"/&gt;
     *               &lt;enumeration value="WeightedMultiple"/&gt;
     *               &lt;enumeration value="RegEx"/&gt;
     *               &lt;enumeration value="MultiBoolean"/&gt;
     *               &lt;enumeration value="FixedDig"/&gt;
     *               &lt;enumeration value="BoundedNum"/&gt;
     *               &lt;enumeration value="Likert"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ScriptId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
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
        "responseType",
        "code",
        "scriptId"
    })
    public static class ResponseTypes {

        @XmlElement(name = "ResponseType")
        protected List<String> responseType;
        @XmlElement(name = "Code")
        protected String code;
        @XmlElement(name = "ScriptId")
        protected Long scriptId;

        /**
         * Gets the value of the responseType property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the responseType property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getResponseType().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getResponseType() {
            if (responseType == null) {
                responseType = new ArrayList<String>();
            }
            return this.responseType;
        }

        /**
         * Gets the value of the code property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCode() {
            return code;
        }

        /**
         * Sets the value of the code property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCode(String value) {
            this.code = value;
        }

        /**
         * Gets the value of the scriptId property.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getScriptId() {
            return scriptId;
        }

        /**
         * Sets the value of the scriptId property.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setScriptId(Long value) {
            this.scriptId = value;
        }

    }

}
