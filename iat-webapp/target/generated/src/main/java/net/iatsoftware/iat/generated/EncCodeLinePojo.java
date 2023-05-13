//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.13 at 08:23:51 AM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.Message;
import net.iatsoftware.iat.messaging.Segment;


/**
 * <p>Java class for EncCodeLinePojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EncCodeLinePojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Segment" type="{http://www.iatsoftware.net/schema}segment-impl" maxOccurs="4" minOccurs="4"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="ANDX" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="BNDX" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="CL" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="Type" use="required" type="{http://www.iatsoftware.net/schema}CodeType" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EncCodeLinePojo", propOrder = {
    "segment"
})
public abstract class EncCodeLinePojo
    extends Message
{

    @XmlElement(name = "Segment", required = true)
    protected List<Segment> segment;
    @XmlAttribute(name = "ANDX", required = true)
    protected int andx;
    @XmlAttribute(name = "BNDX", required = true)
    protected int bndx;
    @XmlAttribute(name = "CL", required = true)
    protected int cl;
    @XmlAttribute(name = "Type", required = true)
    protected CodeType type;

    /**
     * Gets the value of the segment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the segment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSegment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Segment }
     * 
     * 
     */
    public List<Segment> getSegment() {
        if (segment == null) {
            segment = new ArrayList<Segment>();
        }
        return this.segment;
    }

    /**
     * Gets the value of the andx property.
     * 
     */
    public int getANDX() {
        return andx;
    }

    /**
     * Sets the value of the andx property.
     * 
     */
    public void setANDX(int value) {
        this.andx = value;
    }

    /**
     * Gets the value of the bndx property.
     * 
     */
    public int getBNDX() {
        return bndx;
    }

    /**
     * Sets the value of the bndx property.
     * 
     */
    public void setBNDX(int value) {
        this.bndx = value;
    }

    /**
     * Gets the value of the cl property.
     * 
     */
    public int getCL() {
        return cl;
    }

    /**
     * Sets the value of the cl property.
     * 
     */
    public void setCL(int value) {
        this.cl = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link CodeType }
     *     
     */
    public CodeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeType }
     *     
     */
    public void setType(CodeType value) {
        this.type = value;
    }

}
