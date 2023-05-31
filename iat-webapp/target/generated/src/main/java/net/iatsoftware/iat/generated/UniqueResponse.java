//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.24 at 07:02:58 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.Message;


/**
 * <p>Java class for UniqueResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UniqueResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SurveyName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ItemNum" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Additive" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UniqueResponse", propOrder = {
    "surveyName",
    "itemNum",
    "value"
})
public class UniqueResponse
    extends Message
{

    @XmlElement(name = "SurveyName", required = true)
    protected String surveyName;
    @XmlElement(name = "ItemNum")
    protected int itemNum;
    @XmlElement(name = "Value")
    protected List<String> value;
    @XmlAttribute(name = "Additive", required = true)
    protected boolean additive;

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
     * Gets the value of the itemNum property.
     * 
     */
    public int getItemNum() {
        return itemNum;
    }

    /**
     * Sets the value of the itemNum property.
     * 
     */
    public void setItemNum(int value) {
        this.itemNum = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the value property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getValue() {
        if (value == null) {
            value = new ArrayList<String>();
        }
        return this.value;
    }

    /**
     * Gets the value of the additive property.
     * 
     */
    public boolean isAdditive() {
        return additive;
    }

    /**
     * Sets the value of the additive property.
     * 
     */
    public void setAdditive(boolean value) {
        this.additive = value;
    }

}
