//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.13 at 12:28:08 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.messaging.Message;


/**
 * <p>Java class for ResultSetDescriptorPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResultSetDescriptorPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TestAuthor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ConfigFile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BeforeSurvey" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="AfterSurvey" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="NumResults" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RSAKey" type="{http://www.iatsoftware.net/schema}partially-encrypted-rsa-key-impl"/&gt;
 *         &lt;element name="TokenType" type="{http://www.iatsoftware.net/schema}TokenType"/&gt;
 *         &lt;element name="TokenName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="DataVersion" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultSetDescriptorPojo", propOrder = {
    "testAuthor",
    "configFile",
    "beforeSurvey",
    "afterSurvey",
    "numResults",
    "rsaKey",
    "tokenType",
    "tokenName"
})
public class ResultSetDescriptorPojo
    extends Message
{

    @XmlElement(name = "TestAuthor", required = true)
    protected String testAuthor;
    @XmlElement(name = "ConfigFile", required = true)
    protected String configFile;
    @XmlElement(name = "BeforeSurvey")
    protected List<String> beforeSurvey;
    @XmlElement(name = "AfterSurvey")
    protected List<String> afterSurvey;
    @XmlElement(name = "NumResults")
    protected int numResults;
    @XmlElement(name = "RSAKey", required = true)
    protected PartiallyEncryptedRSAKey rsaKey;
    @XmlElement(name = "TokenType", required = true)
    @XmlSchemaType(name = "string")
    protected TokenType tokenType;
    @XmlElement(name = "TokenName")
    protected String tokenName;
    @XmlAttribute(name = "DataVersion", required = true)
    protected int dataVersion;

    /**
     * Gets the value of the testAuthor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestAuthor() {
        return testAuthor;
    }

    /**
     * Sets the value of the testAuthor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestAuthor(String value) {
        this.testAuthor = value;
    }

    /**
     * Gets the value of the configFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigFile() {
        return configFile;
    }

    /**
     * Sets the value of the configFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigFile(String value) {
        this.configFile = value;
    }

    /**
     * Gets the value of the beforeSurvey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the beforeSurvey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBeforeSurvey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getBeforeSurvey() {
        if (beforeSurvey == null) {
            beforeSurvey = new ArrayList<String>();
        }
        return this.beforeSurvey;
    }

    /**
     * Gets the value of the afterSurvey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the afterSurvey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAfterSurvey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAfterSurvey() {
        if (afterSurvey == null) {
            afterSurvey = new ArrayList<String>();
        }
        return this.afterSurvey;
    }

    /**
     * Gets the value of the numResults property.
     * 
     */
    public int getNumResults() {
        return numResults;
    }

    /**
     * Sets the value of the numResults property.
     * 
     */
    public void setNumResults(int value) {
        this.numResults = value;
    }

    /**
     * Gets the value of the rsaKey property.
     * 
     * @return
     *     possible object is
     *     {@link PartiallyEncryptedRSAKey }
     *     
     */
    public PartiallyEncryptedRSAKey getRSAKey() {
        return rsaKey;
    }

    /**
     * Sets the value of the rsaKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartiallyEncryptedRSAKey }
     *     
     */
    public void setRSAKey(PartiallyEncryptedRSAKey value) {
        this.rsaKey = value;
    }

    /**
     * Gets the value of the tokenType property.
     * 
     * @return
     *     possible object is
     *     {@link TokenType }
     *     
     */
    public TokenType getTokenType() {
        return tokenType;
    }

    /**
     * Sets the value of the tokenType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TokenType }
     *     
     */
    public void setTokenType(TokenType value) {
        this.tokenType = value;
    }

    /**
     * Gets the value of the tokenName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenName() {
        return tokenName;
    }

    /**
     * Sets the value of the tokenName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenName(String value) {
        this.tokenName = value;
    }

    /**
     * Gets the value of the dataVersion property.
     * 
     */
    public int getDataVersion() {
        return dataVersion;
    }

    /**
     * Sets the value of the dataVersion property.
     * 
     */
    public void setDataVersion(int value) {
        this.dataVersion = value;
    }

}
