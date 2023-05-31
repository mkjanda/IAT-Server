//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.24 at 07:02:58 PM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.HexBinaryAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for IATLayoutPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IATLayoutPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InteriorWidth" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="InteriorHeight" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="BorderWidth" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ResponseWidth" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ResponseHeight" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="BorderColorR" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *         &lt;element name="BorderColorG" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *         &lt;element name="BorderColorB" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *         &lt;element name="BackColorR" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *         &lt;element name="BackColorG" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *         &lt;element name="BackColorB" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *         &lt;element name="OutlineColorR" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *         &lt;element name="OutlineColorG" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *         &lt;element name="OutlineColorB" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *         &lt;element name="PageColorR" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *         &lt;element name="PageColorG" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *         &lt;element name="PageColorB" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IATLayoutPojo", propOrder = {
    "interiorWidth",
    "interiorHeight",
    "borderWidth",
    "responseWidth",
    "responseHeight",
    "borderColorR",
    "borderColorG",
    "borderColorB",
    "backColorR",
    "backColorG",
    "backColorB",
    "outlineColorR",
    "outlineColorG",
    "outlineColorB",
    "pageColorR",
    "pageColorG",
    "pageColorB"
})
public class IATLayoutPojo {

    @XmlElement(name = "InteriorWidth")
    protected int interiorWidth;
    @XmlElement(name = "InteriorHeight")
    protected int interiorHeight;
    @XmlElement(name = "BorderWidth")
    protected int borderWidth;
    @XmlElement(name = "ResponseWidth")
    protected int responseWidth;
    @XmlElement(name = "ResponseHeight")
    protected int responseHeight;
    @XmlElement(name = "BorderColorR", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] borderColorR;
    @XmlElement(name = "BorderColorG", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] borderColorG;
    @XmlElement(name = "BorderColorB", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] borderColorB;
    @XmlElement(name = "BackColorR", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] backColorR;
    @XmlElement(name = "BackColorG", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] backColorG;
    @XmlElement(name = "BackColorB", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] backColorB;
    @XmlElement(name = "OutlineColorR", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] outlineColorR;
    @XmlElement(name = "OutlineColorG", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] outlineColorG;
    @XmlElement(name = "OutlineColorB", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] outlineColorB;
    @XmlElement(name = "PageColorR", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] pageColorR;
    @XmlElement(name = "PageColorG", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] pageColorG;
    @XmlElement(name = "PageColorB", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] pageColorB;

    /**
     * Gets the value of the interiorWidth property.
     * 
     */
    public int getInteriorWidth() {
        return interiorWidth;
    }

    /**
     * Sets the value of the interiorWidth property.
     * 
     */
    public void setInteriorWidth(int value) {
        this.interiorWidth = value;
    }

    /**
     * Gets the value of the interiorHeight property.
     * 
     */
    public int getInteriorHeight() {
        return interiorHeight;
    }

    /**
     * Sets the value of the interiorHeight property.
     * 
     */
    public void setInteriorHeight(int value) {
        this.interiorHeight = value;
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
     * Gets the value of the responseWidth property.
     * 
     */
    public int getResponseWidth() {
        return responseWidth;
    }

    /**
     * Sets the value of the responseWidth property.
     * 
     */
    public void setResponseWidth(int value) {
        this.responseWidth = value;
    }

    /**
     * Gets the value of the responseHeight property.
     * 
     */
    public int getResponseHeight() {
        return responseHeight;
    }

    /**
     * Sets the value of the responseHeight property.
     * 
     */
    public void setResponseHeight(int value) {
        this.responseHeight = value;
    }

    /**
     * Gets the value of the borderColorR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getBorderColorR() {
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
    public void setBorderColorR(byte[] value) {
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
    public byte[] getBorderColorG() {
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
    public void setBorderColorG(byte[] value) {
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
    public byte[] getBorderColorB() {
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
    public void setBorderColorB(byte[] value) {
        this.borderColorB = value;
    }

    /**
     * Gets the value of the backColorR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getBackColorR() {
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
    public void setBackColorR(byte[] value) {
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
    public byte[] getBackColorG() {
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
    public void setBackColorG(byte[] value) {
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
    public byte[] getBackColorB() {
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
    public void setBackColorB(byte[] value) {
        this.backColorB = value;
    }

    /**
     * Gets the value of the outlineColorR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getOutlineColorR() {
        return outlineColorR;
    }

    /**
     * Sets the value of the outlineColorR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutlineColorR(byte[] value) {
        this.outlineColorR = value;
    }

    /**
     * Gets the value of the outlineColorG property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getOutlineColorG() {
        return outlineColorG;
    }

    /**
     * Sets the value of the outlineColorG property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutlineColorG(byte[] value) {
        this.outlineColorG = value;
    }

    /**
     * Gets the value of the outlineColorB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getOutlineColorB() {
        return outlineColorB;
    }

    /**
     * Sets the value of the outlineColorB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutlineColorB(byte[] value) {
        this.outlineColorB = value;
    }

    /**
     * Gets the value of the pageColorR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getPageColorR() {
        return pageColorR;
    }

    /**
     * Sets the value of the pageColorR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPageColorR(byte[] value) {
        this.pageColorR = value;
    }

    /**
     * Gets the value of the pageColorG property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getPageColorG() {
        return pageColorG;
    }

    /**
     * Sets the value of the pageColorG property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPageColorG(byte[] value) {
        this.pageColorG = value;
    }

    /**
     * Gets the value of the pageColorB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getPageColorB() {
        return pageColorB;
    }

    /**
     * Sets the value of the pageColorB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPageColorB(byte[] value) {
        this.pageColorB = value;
    }

}
