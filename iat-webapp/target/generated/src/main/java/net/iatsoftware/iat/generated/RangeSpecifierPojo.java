//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.12 at 11:08:36 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.entities.DynamicSpecifier;


/**
 * <p>Java class for RangeSpecifierPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RangeSpecifierPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}dynamic-specifier-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Cutoff" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ReverseScored" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="CutoffExcludes" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RangeSpecifierPojo", propOrder = {
    "cutoff",
    "reverseScored",
    "cutoffExcludes"
})
public abstract class RangeSpecifierPojo
    extends DynamicSpecifier
{

    @XmlElement(name = "Cutoff")
    protected int cutoff;
    @XmlElement(name = "ReverseScored")
    protected boolean reverseScored;
    @XmlElement(name = "CutoffExcludes")
    protected boolean cutoffExcludes;

    /**
     * Gets the value of the cutoff property.
     * 
     */
    public int getCutoff() {
        return cutoff;
    }

    /**
     * Sets the value of the cutoff property.
     * 
     */
    public void setCutoff(int value) {
        this.cutoff = value;
    }

    /**
     * Gets the value of the reverseScored property.
     * 
     */
    public boolean isReverseScored() {
        return reverseScored;
    }

    /**
     * Sets the value of the reverseScored property.
     * 
     */
    public void setReverseScored(boolean value) {
        this.reverseScored = value;
    }

    /**
     * Gets the value of the cutoffExcludes property.
     * 
     */
    public boolean isCutoffExcludes() {
        return cutoffExcludes;
    }

    /**
     * Sets the value of the cutoffExcludes property.
     * 
     */
    public void setCutoffExcludes(boolean value) {
        this.cutoffExcludes = value;
    }

}
