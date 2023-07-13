//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.07.10 at 04:13:23 AM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.resultdata.ResultSetDescriptor;
import net.iatsoftware.iat.resultdata.ResultSetEntry;


/**
 * <p>Java class for GTestResults complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GTestResults"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Descriptor" type="{http://www.iatsoftware.net/schema}GResultSetDescriptor" minOccurs="0"/&gt;
 *         &lt;element name="ResultSet" type="{http://www.iatsoftware.net/schema}GResultSetEntry" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="NumResultSets" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GTestResults", propOrder = {
    "descriptor",
    "resultSet"
})
public class GTestResults {

    @XmlElement(name = "Descriptor", type = ResultSetDescriptor.class)
    protected ResultSetDescriptor descriptor;
    @XmlElement(name = "ResultSet", type = ResultSetEntry.class)
    protected List<GResultSetEntry> resultSet;
    @XmlAttribute(name = "NumResultSets", required = true)
    protected int numResultSets;

    /**
     * Gets the value of the descriptor property.
     * 
     * @return
     *     possible object is
     *     {@link GResultSetDescriptor }
     *     
     */
    public GResultSetDescriptor getDescriptor() {
        return descriptor;
    }

    /**
     * Sets the value of the descriptor property.
     * 
     * @param value
     *     allowed object is
     *     {@link GResultSetDescriptor }
     *     
     */
    public void setDescriptor(GResultSetDescriptor value) {
        this.descriptor = ((ResultSetDescriptor) value);
    }

    /**
     * Gets the value of the resultSet property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the resultSet property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResultSet().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GResultSetEntry }
     * 
     * 
     */
    public List<GResultSetEntry> getResultSet() {
        if (resultSet == null) {
            resultSet = new ArrayList<GResultSetEntry>();
        }
        return this.resultSet;
    }

    /**
     * Gets the value of the numResultSets property.
     * 
     */
    public int getNumResultSets() {
        return numResultSets;
    }

    /**
     * Sets the value of the numResultSets property.
     * 
     */
    public void setNumResultSets(int value) {
        this.numResultSets = value;
    }

}
