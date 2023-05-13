//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.13 at 12:28:08 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.AcceptConfirmation;
import net.iatsoftware.iat.messaging.ProductRequests;
import net.iatsoftware.iat.messaging.Transaction;
import net.iatsoftware.iat.messaging.UsageReport;


/**
 * <p>Java class for ClientManagementEnvelopePojo element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="ClientManagementEnvelopePojo"&gt;
 *   &lt;complexType&gt;
 *     &lt;complexContent&gt;
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *         &lt;choice&gt;
 *           &lt;sequence&gt;
 *             &lt;element name="AcceptConfirmation" type="{http://www.iatsoftware.net/schema}accept-confirmation-impl"/&gt;
 *             &lt;element name="ProductRequests" type="{http://www.iatsoftware.net/schema}product-requests-impl"/&gt;
 *             &lt;element name="Transaction" type="{http://www.iatsoftware.net/schema}transaction-impl"/&gt;
 *             &lt;element name="UsageReport" type="{http://www.iatsoftware.net/schema}usage-report-impl"/&gt;
 *           &lt;/sequence&gt;
 *         &lt;/choice&gt;
 *       &lt;/restriction&gt;
 *     &lt;/complexContent&gt;
 *   &lt;/complexType&gt;
 * &lt;/element&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "acceptConfirmation",
    "productRequests",
    "transaction",
    "usageReport"
})
@XmlRootElement(name = "ClientManagementEnvelopePojo")
public class ClientManagementEnvelopePojo {

    @XmlElement(name = "AcceptConfirmation")
    protected AcceptConfirmation acceptConfirmation;
    @XmlElement(name = "ProductRequests")
    protected ProductRequests productRequests;
    @XmlElement(name = "Transaction")
    protected Transaction transaction;
    @XmlElement(name = "UsageReport")
    protected UsageReport usageReport;

    /**
     * Gets the value of the acceptConfirmation property.
     * 
     * @return
     *     possible object is
     *     {@link AcceptConfirmation }
     *     
     */
    public AcceptConfirmation getAcceptConfirmation() {
        return acceptConfirmation;
    }

    /**
     * Sets the value of the acceptConfirmation property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcceptConfirmation }
     *     
     */
    public void setAcceptConfirmation(AcceptConfirmation value) {
        this.acceptConfirmation = value;
    }

    /**
     * Gets the value of the productRequests property.
     * 
     * @return
     *     possible object is
     *     {@link ProductRequests }
     *     
     */
    public ProductRequests getProductRequests() {
        return productRequests;
    }

    /**
     * Sets the value of the productRequests property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductRequests }
     *     
     */
    public void setProductRequests(ProductRequests value) {
        this.productRequests = value;
    }

    /**
     * Gets the value of the transaction property.
     * 
     * @return
     *     possible object is
     *     {@link Transaction }
     *     
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Sets the value of the transaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transaction }
     *     
     */
    public void setTransaction(Transaction value) {
        this.transaction = value;
    }

    /**
     * Gets the value of the usageReport property.
     * 
     * @return
     *     possible object is
     *     {@link UsageReport }
     *     
     */
    public UsageReport getUsageReport() {
        return usageReport;
    }

    /**
     * Sets the value of the usageReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link UsageReport }
     *     
     */
    public void setUsageReport(UsageReport value) {
        this.usageReport = value;
    }

}
