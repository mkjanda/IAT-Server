//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.09.25 at 12:11:19 AM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GIATReport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GIATReport"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IATName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="URL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NumAdministrations" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="NumResultSets" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="TestSizeKB" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="LastDataRetrieval" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AuthorTitle" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AuthorFName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AuthorLName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AuthorEMail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TestVersion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Deploying" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GIATReport", propOrder = {
    "iatName",
    "url",
    "numAdministrations",
    "numResultSets",
    "testSizeKB",
    "lastDataRetrieval",
    "authorTitle",
    "authorFName",
    "authorLName",
    "authorEMail",
    "testVersion"
})
public abstract class GIATReport {

    @XmlElement(name = "IATName", required = true)
    protected String iatName;
    @XmlElement(name = "URL", required = true)
    protected String url;
    @XmlElement(name = "NumAdministrations")
    protected int numAdministrations;
    @XmlElement(name = "NumResultSets")
    protected int numResultSets;
    @XmlElement(name = "TestSizeKB")
    protected int testSizeKB;
    @XmlElement(name = "LastDataRetrieval", required = true)
    protected String lastDataRetrieval;
    @XmlElement(name = "AuthorTitle", required = true)
    protected String authorTitle;
    @XmlElement(name = "AuthorFName", required = true)
    protected String authorFName;
    @XmlElement(name = "AuthorLName", required = true)
    protected String authorLName;
    @XmlElement(name = "AuthorEMail", required = true)
    protected String authorEMail;
    @XmlElement(name = "TestVersion", required = true)
    protected String testVersion;
    @XmlAttribute(name = "Deploying", required = true)
    protected boolean deploying;

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
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURL() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURL(String value) {
        this.url = value;
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

    /**
     * Gets the value of the testSizeKB property.
     * 
     */
    public int getTestSizeKB() {
        return testSizeKB;
    }

    /**
     * Sets the value of the testSizeKB property.
     * 
     */
    public void setTestSizeKB(int value) {
        this.testSizeKB = value;
    }

    /**
     * Gets the value of the lastDataRetrieval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastDataRetrieval() {
        return lastDataRetrieval;
    }

    /**
     * Sets the value of the lastDataRetrieval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastDataRetrieval(String value) {
        this.lastDataRetrieval = value;
    }

    /**
     * Gets the value of the authorTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorTitle() {
        return authorTitle;
    }

    /**
     * Sets the value of the authorTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorTitle(String value) {
        this.authorTitle = value;
    }

    /**
     * Gets the value of the authorFName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorFName() {
        return authorFName;
    }

    /**
     * Sets the value of the authorFName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorFName(String value) {
        this.authorFName = value;
    }

    /**
     * Gets the value of the authorLName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorLName() {
        return authorLName;
    }

    /**
     * Sets the value of the authorLName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorLName(String value) {
        this.authorLName = value;
    }

    /**
     * Gets the value of the authorEMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorEMail() {
        return authorEMail;
    }

    /**
     * Sets the value of the authorEMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorEMail(String value) {
        this.authorEMail = value;
    }

    /**
     * Gets the value of the testVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestVersion() {
        return testVersion;
    }

    /**
     * Sets the value of the testVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestVersion(String value) {
        this.testVersion = value;
    }

    /**
     * Gets the value of the deploying property.
     * 
     */
    public boolean isDeploying() {
        return deploying;
    }

    /**
     * Sets the value of the deploying property.
     * 
     */
    public void setDeploying(boolean value) {
        this.deploying = value;
    }

}
