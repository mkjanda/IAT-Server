//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.07.28 at 02:14:21 PM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.ExceptionMessage;


/**
 * <p>Java class for GServerExceptionMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GServerExceptionMessage"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}exception-message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ServerMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GServerExceptionMessage", propOrder = {
    "serverMessage"
})
public abstract class GServerExceptionMessage
    extends ExceptionMessage
{

    @XmlElement(name = "ServerMessage", required = true)
    protected String serverMessage;

    /**
     * Gets the value of the serverMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerMessage() {
        return serverMessage;
    }

    /**
     * Sets the value of the serverMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerMessage(String value) {
        this.serverMessage = value;
    }

}
