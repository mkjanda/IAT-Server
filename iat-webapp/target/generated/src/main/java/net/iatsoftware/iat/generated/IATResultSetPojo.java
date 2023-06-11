//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.06 at 10:05:44 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.resultdata.ResultSetElementList;
import net.iatsoftware.iat.resultdata.SurveyResponseSet;


/**
 * <p>Java class for IATResultSetPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IATResultSetPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BeforeSurveyResponseSet" type="{http://www.iatsoftware.net/schema}survey-response-set-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="IATResultSetElementList" type="{http://www.iatsoftware.net/schema}result-set-element-list-impl" maxOccurs="unbounded"/&gt;
 *         &lt;element name="AfterSurveyResponseSet" type="{http://www.iatsoftware.net/schema}survey-response-set-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="NumBeforeSurveys" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="NumResultSetElements" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="NumAfterSurveys" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IATResultSetPojo", propOrder = {
    "beforeSurveyResponseSet",
    "iatResultSetElementList",
    "afterSurveyResponseSet"
})
public abstract class IATResultSetPojo {

    @XmlElement(name = "BeforeSurveyResponseSet")
    protected List<SurveyResponseSet> beforeSurveyResponseSet;
    @XmlElement(name = "IATResultSetElementList", required = true)
    protected List<ResultSetElementList> iatResultSetElementList;
    @XmlElement(name = "AfterSurveyResponseSet")
    protected List<SurveyResponseSet> afterSurveyResponseSet;
    @XmlAttribute(name = "NumBeforeSurveys", required = true)
    protected int numBeforeSurveys;
    @XmlAttribute(name = "NumResultSetElements", required = true)
    protected int numResultSetElements;
    @XmlAttribute(name = "NumAfterSurveys", required = true)
    protected int numAfterSurveys;

    /**
     * Gets the value of the beforeSurveyResponseSet property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the beforeSurveyResponseSet property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBeforeSurveyResponseSet().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SurveyResponseSet }
     * 
     * 
     */
    public List<SurveyResponseSet> getBeforeSurveyResponseSet() {
        if (beforeSurveyResponseSet == null) {
            beforeSurveyResponseSet = new ArrayList<SurveyResponseSet>();
        }
        return this.beforeSurveyResponseSet;
    }

    /**
     * Gets the value of the iatResultSetElementList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the iatResultSetElementList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIATResultSetElementList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResultSetElementList }
     * 
     * 
     */
    public List<ResultSetElementList> getIATResultSetElementList() {
        if (iatResultSetElementList == null) {
            iatResultSetElementList = new ArrayList<ResultSetElementList>();
        }
        return this.iatResultSetElementList;
    }

    /**
     * Gets the value of the afterSurveyResponseSet property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the afterSurveyResponseSet property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAfterSurveyResponseSet().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SurveyResponseSet }
     * 
     * 
     */
    public List<SurveyResponseSet> getAfterSurveyResponseSet() {
        if (afterSurveyResponseSet == null) {
            afterSurveyResponseSet = new ArrayList<SurveyResponseSet>();
        }
        return this.afterSurveyResponseSet;
    }

    /**
     * Gets the value of the numBeforeSurveys property.
     * 
     */
    public int getNumBeforeSurveys() {
        return numBeforeSurveys;
    }

    /**
     * Sets the value of the numBeforeSurveys property.
     * 
     */
    public void setNumBeforeSurveys(int value) {
        this.numBeforeSurveys = value;
    }

    /**
     * Gets the value of the numResultSetElements property.
     * 
     */
    public int getNumResultSetElements() {
        return numResultSetElements;
    }

    /**
     * Sets the value of the numResultSetElements property.
     * 
     */
    public void setNumResultSetElements(int value) {
        this.numResultSetElements = value;
    }

    /**
     * Gets the value of the numAfterSurveys property.
     * 
     */
    public int getNumAfterSurveys() {
        return numAfterSurveys;
    }

    /**
     * Sets the value of the numAfterSurveys property.
     * 
     */
    public void setNumAfterSurveys(int value) {
        this.numAfterSurveys = value;
    }

}
