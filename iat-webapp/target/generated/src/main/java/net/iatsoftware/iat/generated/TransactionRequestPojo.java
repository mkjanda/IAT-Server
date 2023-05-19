//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.15 at 04:11:12 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.DecimalElement;
import net.iatsoftware.iat.messaging.IntElement;
import net.iatsoftware.iat.messaging.LongElement;
import net.iatsoftware.iat.messaging.Message;
import net.iatsoftware.iat.messaging.StringElement;


/**
 * <p>Java class for TransactionRequestPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionRequestPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Transaction" type="{http://www.iatsoftware.net/schema}TransactionType"/&gt;
 *         &lt;element name="IATName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProductKey" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ClientID" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="ActivationKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IntValue" type="{http://www.iatsoftware.net/schema}int-element-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="LongValue" type="{http://www.iatsoftware.net/schema}long-element-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="DecimalValue" type="{http://www.iatsoftware.net/schema}decimal-element-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="StringValue" type="{http://www.iatsoftware.net/schema}string-element-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="LastTransaction" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionRequestPojo", propOrder = {
    "transaction",
    "iatName",
    "productKey",
    "clientID",
    "activationKey",
    "intValue",
    "longValue",
    "decimalValue",
    "stringValue",
    "lastTransaction"
})
public abstract class TransactionRequestPojo
    extends Message
{

    @XmlElement(name = "Transaction", required = true)
    @XmlSchemaType(name = "string")
    protected TransactionType transaction;
    @XmlElement(name = "IATName", required = true)
    protected String iatName;
    @XmlElement(name = "ProductKey", required = true)
    protected String productKey;
    @XmlElement(name = "ClientID")
    protected long clientID;
    @XmlElement(name = "ActivationKey")
    protected String activationKey;
    @XmlElement(name = "IntValue")
    protected List<IntElement> intValue;
    @XmlElement(name = "LongValue")
    protected List<LongElement> longValue;
    @XmlElement(name = "DecimalValue")
    protected List<DecimalElement> decimalValue;
    @XmlElement(name = "StringValue")
    protected List<StringElement> stringValue;
    @XmlElement(name = "LastTransaction")
    protected boolean lastTransaction;

    /**
     * Gets the value of the transaction property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionType }
     *     
     */
    public TransactionType getTransaction() {
        return transaction;
    }

    /**
     * Sets the value of the transaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionType }
     *     
     */
    public void setTransaction(TransactionType value) {
        this.transaction = value;
    }

    /**
     * Gets the value of the iatName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIATName() {
        return iatName;
    }

    /**
     * Sets the value of the iatName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIATName(String value) {
        this.iatName = value;
    }

    /**
     * Gets the value of the productKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductKey() {
        return productKey;
    }

    /**
     * Sets the value of the productKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductKey(String value) {
        this.productKey = value;
    }

    /**
     * Gets the value of the clientID property.
     * 
     */
    public long getClientID() {
        return clientID;
    }

    /**
     * Sets the value of the clientID property.
     * 
     */
    public void setClientID(long value) {
        this.clientID = value;
    }

    /**
     * Gets the value of the activationKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivationKey() {
        return activationKey;
    }

    /**
     * Sets the value of the activationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivationKey(String value) {
        this.activationKey = value;
    }

    /**
     * Gets the value of the intValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the intValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIntValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IntElement }
     * 
     * 
     */
    public List<IntElement> getIntValue() {
        if (intValue == null) {
            intValue = new ArrayList<IntElement>();
        }
        return this.intValue;
    }

    /**
     * Gets the value of the longValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the longValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLongValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LongElement }
     * 
     * 
     */
    public List<LongElement> getLongValue() {
        if (longValue == null) {
            longValue = new ArrayList<LongElement>();
        }
        return this.longValue;
    }

    /**
     * Gets the value of the decimalValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the decimalValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDecimalValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DecimalElement }
     * 
     * 
     */
    public List<DecimalElement> getDecimalValue() {
        if (decimalValue == null) {
            decimalValue = new ArrayList<DecimalElement>();
        }
        return this.decimalValue;
    }

    /**
     * Gets the value of the stringValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stringValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStringValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringElement }
     * 
     * 
     */
    public List<StringElement> getStringValue() {
        if (stringValue == null) {
            stringValue = new ArrayList<StringElement>();
        }
        return this.stringValue;
    }

    /**
     * Gets the value of the lastTransaction property.
     * 
     */
    public boolean isLastTransaction() {
        return lastTransaction;
    }

    /**
     * Sets the value of the lastTransaction property.
     * 
     */
    public void setLastTransaction(boolean value) {
        this.lastTransaction = value;
    }

}
