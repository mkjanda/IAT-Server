//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.12 at 11:08:36 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SurveyDate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SurveyDate"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}SurveyResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="StartDate"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Year" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                   &lt;element name="Month" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                   &lt;element name="Day" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="HasValue" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="EndDate"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Year" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                   &lt;element name="Month" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                   &lt;element name="Day" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="HasValue" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
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
@XmlType(name = "SurveyDate", propOrder = {
    "startDate",
    "endDate"
})
public class SurveyDate
    extends SurveyResponse
{

    @XmlElement(name = "StartDate", required = true)
    protected SurveyDate.StartDate startDate;
    @XmlElement(name = "EndDate", required = true)
    protected SurveyDate.EndDate endDate;

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link SurveyDate.StartDate }
     *     
     */
    public SurveyDate.StartDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link SurveyDate.StartDate }
     *     
     */
    public void setStartDate(SurveyDate.StartDate value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link SurveyDate.EndDate }
     *     
     */
    public SurveyDate.EndDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link SurveyDate.EndDate }
     *     
     */
    public void setEndDate(SurveyDate.EndDate value) {
        this.endDate = value;
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
     *         &lt;element name="Year" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *         &lt;element name="Month" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *         &lt;element name="Day" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="HasValue" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "year",
        "month",
        "day"
    })
    public static class EndDate {

        @XmlElement(name = "Year", required = true)
        protected BigInteger year;
        @XmlElement(name = "Month", required = true)
        protected BigInteger month;
        @XmlElement(name = "Day", required = true)
        protected BigInteger day;
        @XmlAttribute(name = "HasValue", required = true)
        protected boolean hasValue;

        /**
         * Gets the value of the year property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getYear() {
            return year;
        }

        /**
         * Sets the value of the year property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setYear(BigInteger value) {
            this.year = value;
        }

        /**
         * Gets the value of the month property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMonth() {
            return month;
        }

        /**
         * Sets the value of the month property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMonth(BigInteger value) {
            this.month = value;
        }

        /**
         * Gets the value of the day property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDay() {
            return day;
        }

        /**
         * Sets the value of the day property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDay(BigInteger value) {
            this.day = value;
        }

        /**
         * Gets the value of the hasValue property.
         * 
         */
        public boolean isHasValue() {
            return hasValue;
        }

        /**
         * Sets the value of the hasValue property.
         * 
         */
        public void setHasValue(boolean value) {
            this.hasValue = value;
        }

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
     *         &lt;element name="Year" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *         &lt;element name="Month" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *         &lt;element name="Day" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="HasValue" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "year",
        "month",
        "day"
    })
    public static class StartDate {

        @XmlElement(name = "Year", required = true)
        protected BigInteger year;
        @XmlElement(name = "Month", required = true)
        protected BigInteger month;
        @XmlElement(name = "Day", required = true)
        protected BigInteger day;
        @XmlAttribute(name = "HasValue", required = true)
        protected boolean hasValue;

        /**
         * Gets the value of the year property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getYear() {
            return year;
        }

        /**
         * Sets the value of the year property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setYear(BigInteger value) {
            this.year = value;
        }

        /**
         * Gets the value of the month property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMonth() {
            return month;
        }

        /**
         * Sets the value of the month property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMonth(BigInteger value) {
            this.month = value;
        }

        /**
         * Gets the value of the day property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDay() {
            return day;
        }

        /**
         * Sets the value of the day property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDay(BigInteger value) {
            this.day = value;
        }

        /**
         * Gets the value of the hasValue property.
         * 
         */
        public boolean isHasValue() {
            return hasValue;
        }

        /**
         * Sets the value of the hasValue property.
         * 
         */
        public void setHasValue(boolean value) {
            this.hasValue = value;
        }

    }

}
