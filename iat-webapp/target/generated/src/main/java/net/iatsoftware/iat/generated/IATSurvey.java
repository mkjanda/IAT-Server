//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.12 at 11:08:36 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IATSurvey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IATSurvey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IAT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FileName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SurveyName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InitialPosition" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="AlternationGroup" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;group ref="{http://www.iatsoftware.net/schema}SurveyItemType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="TimeoutMillis" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="UniqueResponse" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IATSurvey", propOrder = {
    "iat",
    "fileName",
    "surveyName",
    "initialPosition",
    "alternationGroup",
    "surveyItemType"
})
public class IATSurvey {

    @XmlElement(name = "IAT", required = true)
    protected String iat;
    @XmlElement(name = "FileName", required = true)
    protected String fileName;
    @XmlElement(name = "SurveyName", required = true)
    protected String surveyName;
    @XmlElement(name = "InitialPosition")
    protected int initialPosition;
    @XmlElement(name = "AlternationGroup")
    protected int alternationGroup;
    @XmlElements({
        @XmlElement(name = "SurveyItem", type = IATSurvey.SurveyItem.class),
        @XmlElement(name = "Caption", type = IATSurvey.Caption.class),
        @XmlElement(name = "SurveyImage", type = IATSurvey.SurveyImage.class)
    })
    protected List<Object> surveyItemType;
    @XmlAttribute(name = "TimeoutMillis", required = true)
    protected long timeoutMillis;
    @XmlAttribute(name = "UniqueResponse", required = true)
    protected int uniqueResponse;

    /**
     * Gets the value of the iat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIAT() {
        return iat;
    }

    /**
     * Sets the value of the iat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIAT(String value) {
        this.iat = value;
    }

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

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
     * Gets the value of the initialPosition property.
     * 
     */
    public int getInitialPosition() {
        return initialPosition;
    }

    /**
     * Sets the value of the initialPosition property.
     * 
     */
    public void setInitialPosition(int value) {
        this.initialPosition = value;
    }

    /**
     * Gets the value of the alternationGroup property.
     * 
     */
    public int getAlternationGroup() {
        return alternationGroup;
    }

    /**
     * Sets the value of the alternationGroup property.
     * 
     */
    public void setAlternationGroup(int value) {
        this.alternationGroup = value;
    }

    /**
     * Gets the value of the surveyItemType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the surveyItemType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSurveyItemType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IATSurvey.SurveyItem }
     * {@link IATSurvey.Caption }
     * {@link IATSurvey.SurveyImage }
     * 
     * 
     */
    public List<Object> getSurveyItemType() {
        if (surveyItemType == null) {
            surveyItemType = new ArrayList<Object>();
        }
        return this.surveyItemType;
    }

    /**
     * Gets the value of the timeoutMillis property.
     * 
     */
    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    /**
     * Sets the value of the timeoutMillis property.
     * 
     */
    public void setTimeoutMillis(long value) {
        this.timeoutMillis = value;
    }

    /**
     * Gets the value of the uniqueResponse property.
     * 
     */
    public int getUniqueResponse() {
        return uniqueResponse;
    }

    /**
     * Sets the value of the uniqueResponse property.
     * 
     */
    public void setUniqueResponse(int value) {
        this.uniqueResponse = value;
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
     *         &lt;element name="TextWidth" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;element name="BorderWidth" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;element name="FontName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="LineHeight" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;element name="FontSize" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;element name="FontColorR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="FontColorG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="FontColorB" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="BackColorR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="BackColorG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="BackColorB" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="BorderColorR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="BorderColorG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="BorderColorB" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="Optional" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *       &lt;attribute name="Image" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "textWidth",
        "borderWidth",
        "fontName",
        "lineHeight",
        "fontSize",
        "fontColorR",
        "fontColorG",
        "fontColorB",
        "backColorR",
        "backColorG",
        "backColorB",
        "borderColorR",
        "borderColorG",
        "borderColorB"
    })
    public static class Caption {

        @XmlElement(name = "TextWidth")
        protected int textWidth;
        @XmlElement(name = "BorderWidth")
        protected int borderWidth;
        @XmlElement(name = "FontName", required = true)
        protected String fontName;
        @XmlElement(name = "LineHeight")
        protected int lineHeight;
        @XmlElement(name = "FontSize")
        protected int fontSize;
        @XmlElement(name = "FontColorR", required = true)
        protected String fontColorR;
        @XmlElement(name = "FontColorG", required = true)
        protected String fontColorG;
        @XmlElement(name = "FontColorB", required = true)
        protected String fontColorB;
        @XmlElement(name = "BackColorR", required = true)
        protected String backColorR;
        @XmlElement(name = "BackColorG", required = true)
        protected String backColorG;
        @XmlElement(name = "BackColorB", required = true)
        protected String backColorB;
        @XmlElement(name = "BorderColorR", required = true)
        protected String borderColorR;
        @XmlElement(name = "BorderColorG", required = true)
        protected String borderColorG;
        @XmlElement(name = "BorderColorB", required = true)
        protected String borderColorB;
        @XmlAttribute(name = "Optional")
        protected java.lang.Boolean optional;
        @XmlAttribute(name = "Image")
        protected java.lang.Boolean image;

        /**
         * Gets the value of the textWidth property.
         * 
         */
        public int getTextWidth() {
            return textWidth;
        }

        /**
         * Sets the value of the textWidth property.
         * 
         */
        public void setTextWidth(int value) {
            this.textWidth = value;
        }

        /**
         * Gets the value of the borderWidth property.
         * 
         */
        public int getBorderWidth() {
            return borderWidth;
        }

        /**
         * Sets the value of the borderWidth property.
         * 
         */
        public void setBorderWidth(int value) {
            this.borderWidth = value;
        }

        /**
         * Gets the value of the fontName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFontName() {
            return fontName;
        }

        /**
         * Sets the value of the fontName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFontName(String value) {
            this.fontName = value;
        }

        /**
         * Gets the value of the lineHeight property.
         * 
         */
        public int getLineHeight() {
            return lineHeight;
        }

        /**
         * Sets the value of the lineHeight property.
         * 
         */
        public void setLineHeight(int value) {
            this.lineHeight = value;
        }

        /**
         * Gets the value of the fontSize property.
         * 
         */
        public int getFontSize() {
            return fontSize;
        }

        /**
         * Sets the value of the fontSize property.
         * 
         */
        public void setFontSize(int value) {
            this.fontSize = value;
        }

        /**
         * Gets the value of the fontColorR property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFontColorR() {
            return fontColorR;
        }

        /**
         * Sets the value of the fontColorR property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFontColorR(String value) {
            this.fontColorR = value;
        }

        /**
         * Gets the value of the fontColorG property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFontColorG() {
            return fontColorG;
        }

        /**
         * Sets the value of the fontColorG property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFontColorG(String value) {
            this.fontColorG = value;
        }

        /**
         * Gets the value of the fontColorB property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFontColorB() {
            return fontColorB;
        }

        /**
         * Sets the value of the fontColorB property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFontColorB(String value) {
            this.fontColorB = value;
        }

        /**
         * Gets the value of the backColorR property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBackColorR() {
            return backColorR;
        }

        /**
         * Sets the value of the backColorR property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBackColorR(String value) {
            this.backColorR = value;
        }

        /**
         * Gets the value of the backColorG property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBackColorG() {
            return backColorG;
        }

        /**
         * Sets the value of the backColorG property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBackColorG(String value) {
            this.backColorG = value;
        }

        /**
         * Gets the value of the backColorB property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBackColorB() {
            return backColorB;
        }

        /**
         * Sets the value of the backColorB property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBackColorB(String value) {
            this.backColorB = value;
        }

        /**
         * Gets the value of the borderColorR property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBorderColorR() {
            return borderColorR;
        }

        /**
         * Sets the value of the borderColorR property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBorderColorR(String value) {
            this.borderColorR = value;
        }

        /**
         * Gets the value of the borderColorG property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBorderColorG() {
            return borderColorG;
        }

        /**
         * Sets the value of the borderColorG property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBorderColorG(String value) {
            this.borderColorG = value;
        }

        /**
         * Gets the value of the borderColorB property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBorderColorB() {
            return borderColorB;
        }

        /**
         * Sets the value of the borderColorB property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBorderColorB(String value) {
            this.borderColorB = value;
        }

        /**
         * Gets the value of the optional property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Boolean }
         *     
         */
        public java.lang.Boolean isOptional() {
            return optional;
        }

        /**
         * Sets the value of the optional property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Boolean }
         *     
         */
        public void setOptional(java.lang.Boolean value) {
            this.optional = value;
        }

        /**
         * Gets the value of the image property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Boolean }
         *     
         */
        public java.lang.Boolean isImage() {
            return image;
        }

        /**
         * Sets the value of the image property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Boolean }
         *     
         */
        public void setImage(java.lang.Boolean value) {
            this.image = value;
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
     *         &lt;element name="MimeType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ResourceIId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="Optional" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *       &lt;attribute name="Image" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "mimeType",
        "resourceIId"
    })
    public static class SurveyImage {

        @XmlElement(name = "MimeType", required = true)
        protected String mimeType;
        @XmlElement(name = "ResourceIId")
        protected long resourceIId;
        @XmlAttribute(name = "Optional")
        protected java.lang.Boolean optional;
        @XmlAttribute(name = "Image")
        protected java.lang.Boolean image;

        /**
         * Gets the value of the mimeType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMimeType() {
            return mimeType;
        }

        /**
         * Sets the value of the mimeType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMimeType(String value) {
            this.mimeType = value;
        }

        /**
         * Gets the value of the resourceIId property.
         * 
         */
        public long getResourceIId() {
            return resourceIId;
        }

        /**
         * Sets the value of the resourceIId property.
         * 
         */
        public void setResourceIId(long value) {
            this.resourceIId = value;
        }

        /**
         * Gets the value of the optional property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Boolean }
         *     
         */
        public java.lang.Boolean isOptional() {
            return optional;
        }

        /**
         * Sets the value of the optional property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Boolean }
         *     
         */
        public void setOptional(java.lang.Boolean value) {
            this.optional = value;
        }

        /**
         * Gets the value of the image property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Boolean }
         *     
         */
        public java.lang.Boolean isImage() {
            return image;
        }

        /**
         * Sets the value of the image property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Boolean }
         *     
         */
        public void setImage(java.lang.Boolean value) {
            this.image = value;
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
     *         &lt;element name="Text" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;choice minOccurs="0"&gt;
     *           &lt;element name="Boolean" type="{http://www.iatsoftware.net/schema}Boolean"/&gt;
     *           &lt;element name="Date" type="{http://www.iatsoftware.net/schema}DateResponse"/&gt;
     *           &lt;element name="Instruction" type="{http://www.iatsoftware.net/schema}Instruction"/&gt;
     *           &lt;element name="Likert" type="{http://www.iatsoftware.net/schema}Likert"/&gt;
     *           &lt;element name="MultiBoolean" type="{http://www.iatsoftware.net/schema}MultiBoolean"/&gt;
     *           &lt;element name="BoundedLength" type="{http://www.iatsoftware.net/schema}BoundedLength"/&gt;
     *           &lt;element name="BoundedNumber" type="{http://www.iatsoftware.net/schema}BoundedNumber"/&gt;
     *           &lt;element name="FixedDigit" type="{http://www.iatsoftware.net/schema}FixedDigit"/&gt;
     *           &lt;element name="MultipleResponse" type="{http://www.iatsoftware.net/schema}MultipleResponse"/&gt;
     *           &lt;element name="WeightedMultipleResponse" type="{http://www.iatsoftware.net/schema}WeightedMultiple"/&gt;
     *         &lt;/choice&gt;
     *         &lt;element name="SurveyItemFormat" type="{http://www.iatsoftware.net/schema}Format"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="Optional" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *       &lt;attribute name="Image" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "text",
        "_boolean",
        "date",
        "instruction",
        "likert",
        "multiBoolean",
        "boundedLength",
        "boundedNumber",
        "fixedDigit",
        "multipleResponse",
        "weightedMultipleResponse",
        "surveyItemFormat"
    })
    public static class SurveyItem {

        @XmlElement(name = "Text", required = true)
        protected String text;
        @XmlElement(name = "Boolean")
        protected net.iatsoftware.iat.generated.Boolean _boolean;
        @XmlElement(name = "Date")
        protected DateResponse date;
        @XmlElement(name = "Instruction")
        protected Instruction instruction;
        @XmlElement(name = "Likert")
        protected Likert likert;
        @XmlElement(name = "MultiBoolean")
        protected MultiBoolean multiBoolean;
        @XmlElement(name = "BoundedLength")
        protected BoundedLength boundedLength;
        @XmlElement(name = "BoundedNumber")
        protected BoundedNumber boundedNumber;
        @XmlElement(name = "FixedDigit")
        protected FixedDigit fixedDigit;
        @XmlElement(name = "MultipleResponse")
        protected MultipleResponse multipleResponse;
        @XmlElement(name = "WeightedMultipleResponse")
        protected WeightedMultiple weightedMultipleResponse;
        @XmlElement(name = "SurveyItemFormat", required = true)
        protected Format surveyItemFormat;
        @XmlAttribute(name = "Optional")
        protected java.lang.Boolean optional;
        @XmlAttribute(name = "Image")
        protected java.lang.Boolean image;

        /**
         * Gets the value of the text property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getText() {
            return text;
        }

        /**
         * Sets the value of the text property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setText(String value) {
            this.text = value;
        }

        /**
         * Gets the value of the boolean property.
         * 
         * @return
         *     possible object is
         *     {@link net.iatsoftware.iat.generated.Boolean }
         *     
         */
        public net.iatsoftware.iat.generated.Boolean getBoolean() {
            return _boolean;
        }

        /**
         * Sets the value of the boolean property.
         * 
         * @param value
         *     allowed object is
         *     {@link net.iatsoftware.iat.generated.Boolean }
         *     
         */
        public void setBoolean(net.iatsoftware.iat.generated.Boolean value) {
            this._boolean = value;
        }

        /**
         * Gets the value of the date property.
         * 
         * @return
         *     possible object is
         *     {@link DateResponse }
         *     
         */
        public DateResponse getDate() {
            return date;
        }

        /**
         * Sets the value of the date property.
         * 
         * @param value
         *     allowed object is
         *     {@link DateResponse }
         *     
         */
        public void setDate(DateResponse value) {
            this.date = value;
        }

        /**
         * Gets the value of the instruction property.
         * 
         * @return
         *     possible object is
         *     {@link Instruction }
         *     
         */
        public Instruction getInstruction() {
            return instruction;
        }

        /**
         * Sets the value of the instruction property.
         * 
         * @param value
         *     allowed object is
         *     {@link Instruction }
         *     
         */
        public void setInstruction(Instruction value) {
            this.instruction = value;
        }

        /**
         * Gets the value of the likert property.
         * 
         * @return
         *     possible object is
         *     {@link Likert }
         *     
         */
        public Likert getLikert() {
            return likert;
        }

        /**
         * Sets the value of the likert property.
         * 
         * @param value
         *     allowed object is
         *     {@link Likert }
         *     
         */
        public void setLikert(Likert value) {
            this.likert = value;
        }

        /**
         * Gets the value of the multiBoolean property.
         * 
         * @return
         *     possible object is
         *     {@link MultiBoolean }
         *     
         */
        public MultiBoolean getMultiBoolean() {
            return multiBoolean;
        }

        /**
         * Sets the value of the multiBoolean property.
         * 
         * @param value
         *     allowed object is
         *     {@link MultiBoolean }
         *     
         */
        public void setMultiBoolean(MultiBoolean value) {
            this.multiBoolean = value;
        }

        /**
         * Gets the value of the boundedLength property.
         * 
         * @return
         *     possible object is
         *     {@link BoundedLength }
         *     
         */
        public BoundedLength getBoundedLength() {
            return boundedLength;
        }

        /**
         * Sets the value of the boundedLength property.
         * 
         * @param value
         *     allowed object is
         *     {@link BoundedLength }
         *     
         */
        public void setBoundedLength(BoundedLength value) {
            this.boundedLength = value;
        }

        /**
         * Gets the value of the boundedNumber property.
         * 
         * @return
         *     possible object is
         *     {@link BoundedNumber }
         *     
         */
        public BoundedNumber getBoundedNumber() {
            return boundedNumber;
        }

        /**
         * Sets the value of the boundedNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link BoundedNumber }
         *     
         */
        public void setBoundedNumber(BoundedNumber value) {
            this.boundedNumber = value;
        }

        /**
         * Gets the value of the fixedDigit property.
         * 
         * @return
         *     possible object is
         *     {@link FixedDigit }
         *     
         */
        public FixedDigit getFixedDigit() {
            return fixedDigit;
        }

        /**
         * Sets the value of the fixedDigit property.
         * 
         * @param value
         *     allowed object is
         *     {@link FixedDigit }
         *     
         */
        public void setFixedDigit(FixedDigit value) {
            this.fixedDigit = value;
        }

        /**
         * Gets the value of the multipleResponse property.
         * 
         * @return
         *     possible object is
         *     {@link MultipleResponse }
         *     
         */
        public MultipleResponse getMultipleResponse() {
            return multipleResponse;
        }

        /**
         * Sets the value of the multipleResponse property.
         * 
         * @param value
         *     allowed object is
         *     {@link MultipleResponse }
         *     
         */
        public void setMultipleResponse(MultipleResponse value) {
            this.multipleResponse = value;
        }

        /**
         * Gets the value of the weightedMultipleResponse property.
         * 
         * @return
         *     possible object is
         *     {@link WeightedMultiple }
         *     
         */
        public WeightedMultiple getWeightedMultipleResponse() {
            return weightedMultipleResponse;
        }

        /**
         * Sets the value of the weightedMultipleResponse property.
         * 
         * @param value
         *     allowed object is
         *     {@link WeightedMultiple }
         *     
         */
        public void setWeightedMultipleResponse(WeightedMultiple value) {
            this.weightedMultipleResponse = value;
        }

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
         * Gets the value of the optional property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Boolean }
         *     
         */
        public java.lang.Boolean isOptional() {
            return optional;
        }

        /**
         * Sets the value of the optional property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Boolean }
         *     
         */
        public void setOptional(java.lang.Boolean value) {
            this.optional = value;
        }

        /**
         * Gets the value of the image property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Boolean }
         *     
         */
        public java.lang.Boolean isImage() {
            return image;
        }

        /**
         * Sets the value of the image property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Boolean }
         *     
         */
        public void setImage(java.lang.Boolean value) {
            this.image = value;
        }

    }

}