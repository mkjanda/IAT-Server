//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.17 at 05:48:02 AM EDT 
//


package net.iatsoftware.iat.generated;

import java.math.BigInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GSurveyDate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GSurveyDate"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}GSurveyResponse"&gt;
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
@XmlType(name = "GSurveyDate", propOrder = {
    "startDate",
    "endDate"
})
public class GSurveyDate
    extends GSurveyResponse
{

    @XmlElement(name = "StartDate", required = true)
    protected GSurveyDate.StartDate startDate;
    @XmlElement(name = "EndDate", required = true)
    protected GSurveyDate.EndDate endDate;

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link GSurveyDate.StartDate }
     *     
     */
    public GSurveyDate.StartDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link GSurveyDate.StartDate }
     *     
     */
    public void setStartDate(GSurveyDate.StartDate value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link GSurveyDate.EndDate }
     *     
     */
    public GSurveyDate.EndDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link GSurveyDate.EndDate }
     *     
     */
    public void setEndDate(GSurveyDate.EndDate value) {
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