//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.09.25 at 12:11:19 AM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GPurchaseOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GPurchaseOrder"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NumTests" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="NumAdministrations" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DiskSpace" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Total" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GPurchaseOrder", propOrder = {
    "numTests",
    "numAdministrations",
    "diskSpace",
    "total"
})
public abstract class GPurchaseOrder {

    @XmlElement(name = "NumTests")
    protected int numTests;
    @XmlElement(name = "NumAdministrations")
    protected int numAdministrations;
    @XmlElement(name = "DiskSpace")
    protected int diskSpace;
    @XmlElement(name = "Total")
    protected int total;

    /**
     * Gets the value of the numTests property.
     * 
     */
    public int getNumTests() {
        return numTests;
    }

    /**
     * Sets the value of the numTests property.
     * 
     */
    public void setNumTests(int value) {
        this.numTests = value;
    }

    /**
     * Gets the value of the numAdministrations property.
     * 
     */
    public int getNumAdministrations() {
        return numAdministrations;
    }

    /**
     * Sets the value of the numAdministrations property.
     * 
     */
    public void setNumAdministrations(int value) {
        this.numAdministrations = value;
    }

    /**
     * Gets the value of the diskSpace property.
     * 
     */
    public int getDiskSpace() {
        return diskSpace;
    }

    /**
     * Sets the value of the diskSpace property.
     * 
     */
    public void setDiskSpace(int value) {
        this.diskSpace = value;
    }

    /**
     * Gets the value of the total property.
     * 
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     */
    public void setTotal(int value) {
        this.total = value;
    }

}
