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
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.InnerException;
import net.iatsoftware.iat.messaging.Message;


/**
 * <p>Java class for GExceptionMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GExceptionMessage"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ExceptionMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="StackTraceElement" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="InnerException" type="{http://www.iatsoftware.net/schema}inner-exception-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GExceptionMessage", propOrder = {
    "exceptionMessage",
    "stackTraceElement",
    "innerException"
})
public class GExceptionMessage
    extends Message
{

    @XmlElement(name = "ExceptionMessage", required = true)
    protected String exceptionMessage;
    @XmlElement(name = "StackTraceElement")
    protected List<String> stackTraceElement;
    @XmlElement(name = "InnerException")
    protected List<InnerException> innerException;

    /**
     * Gets the value of the exceptionMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    /**
     * Sets the value of the exceptionMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExceptionMessage(String value) {
        this.exceptionMessage = value;
    }

    /**
     * Gets the value of the stackTraceElement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the stackTraceElement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStackTraceElement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getStackTraceElement() {
        if (stackTraceElement == null) {
            stackTraceElement = new ArrayList<String>();
        }
        return this.stackTraceElement;
    }

    /**
     * Gets the value of the innerException property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the innerException property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInnerException().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InnerException }
     * 
     * 
     */
    public List<InnerException> getInnerException() {
        if (innerException == null) {
            innerException = new ArrayList<InnerException>();
        }
        return this.innerException;
    }

}
