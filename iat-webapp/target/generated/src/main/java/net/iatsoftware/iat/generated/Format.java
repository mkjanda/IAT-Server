//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.13 at 08:23:51 AM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Format complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Format"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Font" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FontSize" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ColorR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ColorG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ColorB" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Bold" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Italic" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Format", propOrder = {
    "font",
    "fontSize",
    "colorR",
    "colorG",
    "colorB",
    "bold",
    "italic"
})
public class Format {

    @XmlElement(name = "Font", required = true)
    protected String font;
    @XmlElement(name = "FontSize", required = true)
    protected String fontSize;
    @XmlElement(name = "ColorR", required = true)
    protected String colorR;
    @XmlElement(name = "ColorG", required = true)
    protected String colorG;
    @XmlElement(name = "ColorB", required = true)
    protected String colorB;
    @XmlElement(name = "Bold")
    protected boolean bold;
    @XmlElement(name = "Italic")
    protected boolean italic;

    /**
     * Gets the value of the font property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFont() {
        return font;
    }

    /**
     * Sets the value of the font property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFont(String value) {
        this.font = value;
    }

    /**
     * Gets the value of the fontSize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFontSize() {
        return fontSize;
    }

    /**
     * Sets the value of the fontSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFontSize(String value) {
        this.fontSize = value;
    }

    /**
     * Gets the value of the colorR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColorR() {
        return colorR;
    }

    /**
     * Sets the value of the colorR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColorR(String value) {
        this.colorR = value;
    }

    /**
     * Gets the value of the colorG property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColorG() {
        return colorG;
    }

    /**
     * Sets the value of the colorG property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColorG(String value) {
        this.colorG = value;
    }

    /**
     * Gets the value of the colorB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColorB() {
        return colorB;
    }

    /**
     * Sets the value of the colorB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColorB(String value) {
        this.colorB = value;
    }

    /**
     * Gets the value of the bold property.
     * 
     */
    public boolean isBold() {
        return bold;
    }

    /**
     * Sets the value of the bold property.
     * 
     */
    public void setBold(boolean value) {
        this.bold = value;
    }

    /**
     * Gets the value of the italic property.
     * 
     */
    public boolean isItalic() {
        return italic;
    }

    /**
     * Sets the value of the italic property.
     * 
     */
    public void setItalic(boolean value) {
        this.italic = value;
    }

}
