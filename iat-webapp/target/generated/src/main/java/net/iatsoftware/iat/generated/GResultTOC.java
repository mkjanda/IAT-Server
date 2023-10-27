//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.09.25 at 12:11:19 AM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.resultdata.ResultTOCEntry;


/**
 * <p>Java class for GResultTOC complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GResultTOC"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ResultTOCEntry" type="{http://www.iatsoftware.net/schema}result-toc-entry-impl" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="NumEntries" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GResultTOC", propOrder = {
    "resultTOCEntry"
})
public abstract class GResultTOC {

    @XmlElement(name = "ResultTOCEntry", required = true)
    protected List<ResultTOCEntry> resultTOCEntry;
    @XmlAttribute(name = "NumEntries", required = true)
    protected int numEntries;

    /**
     * Gets the value of the resultTOCEntry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the resultTOCEntry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResultTOCEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResultTOCEntry }
     * 
     * 
     */
    public List<ResultTOCEntry> getResultTOCEntry() {
        if (resultTOCEntry == null) {
            resultTOCEntry = new ArrayList<ResultTOCEntry>();
        }
        return this.resultTOCEntry;
    }

    /**
     * Gets the value of the numEntries property.
     * 
     */
    public int getNumEntries() {
        return numEntries;
    }

    /**
     * Sets the value of the numEntries property.
     * 
     */
    public void setNumEntries(int value) {
        this.numEntries = value;
    }

}
