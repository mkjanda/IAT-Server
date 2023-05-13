//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.13 at 08:23:51 AM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.configfile.DisplayItem;
import net.iatsoftware.iat.configfile.IATEventList;
import net.iatsoftware.iat.configfile.IATLayout;
import net.iatsoftware.iat.configfile.Survey;
import net.iatsoftware.iat.configfile.UniqueResponse;
import net.iatsoftware.iat.entities.DynamicSpecifier;
import net.iatsoftware.iat.messaging.Message;


/**
 * <p>Java class for ConfigFile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfigFile"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IATName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ServerDomain" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ServerPath" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ServerPort" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ClientID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ScriptId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="NumIATItems" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsSevenBlock" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="RedirectOnComplete" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LeftResponseASCIIKeyCodeUpper" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RightResponseASCIIKeyCodeUpper" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="LeftResponseASCIIKeyCodeLower" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RightResponseASCIIKeyCodeLower" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RandomizationType"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="None"/&gt;
 *               &lt;enumeration value="RandomOrder"/&gt;
 *               &lt;enumeration value="SetNumberOfPresentations"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ErrorMarkID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="LeftKeyOutlineID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RightKeyOutlineID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="PrefixSelfAlternatingSurveys" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="DynamicSpecifier" type="{http://www.iatsoftware.net/schema}dynamic-specifier-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Survey" type="{http://www.iatsoftware.net/schema}survey-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="UniqueResponse" type="{http://www.iatsoftware.net/schema}unique-response-impl"/&gt;
 *         &lt;element name="Layout" type="{http://www.iatsoftware.net/schema}layout-impl"/&gt;
 *         &lt;element name="IATEventList" type="{http://www.iatsoftware.net/schema}event-list-impl"/&gt;
 *         &lt;element name="DisplayItemList"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="IATDisplayItem" type="{http://www.iatsoftware.net/schema}display-item-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="NumBeforeSurveys" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="NumAfterSurveys" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="HasUniqueResponse" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigFile", propOrder = {
    "iatName",
    "serverDomain",
    "serverPath",
    "serverPort",
    "clientID",
    "scriptId",
    "numIATItems",
    "isSevenBlock",
    "redirectOnComplete",
    "leftResponseASCIIKeyCodeUpper",
    "rightResponseASCIIKeyCodeUpper",
    "leftResponseASCIIKeyCodeLower",
    "rightResponseASCIIKeyCodeLower",
    "randomizationType",
    "errorMarkID",
    "leftKeyOutlineID",
    "rightKeyOutlineID",
    "prefixSelfAlternatingSurveys",
    "dynamicSpecifier",
    "survey",
    "uniqueResponse",
    "layout",
    "iatEventList",
    "displayItemList"
})
public class ConfigFile
    extends Message
{

    @XmlElement(name = "IATName", required = true)
    protected String iatName;
    @XmlElement(name = "ServerDomain", required = true)
    protected String serverDomain;
    @XmlElement(name = "ServerPath", required = true)
    protected String serverPath;
    @XmlElement(name = "ServerPort", required = true)
    protected String serverPort;
    @XmlElement(name = "ClientID")
    protected int clientID;
    @XmlElement(name = "ScriptId")
    protected int scriptId;
    @XmlElement(name = "NumIATItems")
    protected int numIATItems;
    @XmlElement(name = "IsSevenBlock")
    protected boolean isSevenBlock;
    @XmlElement(name = "RedirectOnComplete", required = true)
    protected String redirectOnComplete;
    @XmlElement(name = "LeftResponseASCIIKeyCodeUpper")
    protected int leftResponseASCIIKeyCodeUpper;
    @XmlElement(name = "RightResponseASCIIKeyCodeUpper")
    protected int rightResponseASCIIKeyCodeUpper;
    @XmlElement(name = "LeftResponseASCIIKeyCodeLower")
    protected int leftResponseASCIIKeyCodeLower;
    @XmlElement(name = "RightResponseASCIIKeyCodeLower")
    protected int rightResponseASCIIKeyCodeLower;
    @XmlElement(name = "RandomizationType", required = true)
    protected String randomizationType;
    @XmlElement(name = "ErrorMarkID")
    protected int errorMarkID;
    @XmlElement(name = "LeftKeyOutlineID")
    protected int leftKeyOutlineID;
    @XmlElement(name = "RightKeyOutlineID")
    protected int rightKeyOutlineID;
    @XmlElement(name = "PrefixSelfAlternatingSurveys")
    protected boolean prefixSelfAlternatingSurveys;
    @XmlElement(name = "DynamicSpecifier")
    protected List<DynamicSpecifier> dynamicSpecifier;
    @XmlElement(name = "Survey")
    protected List<Survey> survey;
    @XmlElement(name = "UniqueResponse", required = true)
    protected UniqueResponse uniqueResponse;
    @XmlElement(name = "Layout", required = true)
    protected IATLayout layout;
    @XmlElement(name = "IATEventList", required = true)
    protected IATEventList iatEventList;
    @XmlElement(name = "DisplayItemList", required = true)
    protected ConfigFile.DisplayItemList displayItemList;
    @XmlAttribute(name = "NumBeforeSurveys", required = true)
    protected int numBeforeSurveys;
    @XmlAttribute(name = "NumAfterSurveys", required = true)
    protected int numAfterSurveys;
    @XmlAttribute(name = "HasUniqueResponse", required = true)
    protected boolean hasUniqueResponse;

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
     * Gets the value of the serverDomain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerDomain() {
        return serverDomain;
    }

    /**
     * Sets the value of the serverDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerDomain(String value) {
        this.serverDomain = value;
    }

    /**
     * Gets the value of the serverPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerPath() {
        return serverPath;
    }

    /**
     * Sets the value of the serverPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerPath(String value) {
        this.serverPath = value;
    }

    /**
     * Gets the value of the serverPort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerPort() {
        return serverPort;
    }

    /**
     * Sets the value of the serverPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerPort(String value) {
        this.serverPort = value;
    }

    /**
     * Gets the value of the clientID property.
     * 
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * Sets the value of the clientID property.
     * 
     */
    public void setClientID(int value) {
        this.clientID = value;
    }

    /**
     * Gets the value of the scriptId property.
     * 
     */
    public int getScriptId() {
        return scriptId;
    }

    /**
     * Sets the value of the scriptId property.
     * 
     */
    public void setScriptId(int value) {
        this.scriptId = value;
    }

    /**
     * Gets the value of the numIATItems property.
     * 
     */
    public int getNumIATItems() {
        return numIATItems;
    }

    /**
     * Sets the value of the numIATItems property.
     * 
     */
    public void setNumIATItems(int value) {
        this.numIATItems = value;
    }

    /**
     * Gets the value of the isSevenBlock property.
     * 
     */
    public boolean isIsSevenBlock() {
        return isSevenBlock;
    }

    /**
     * Sets the value of the isSevenBlock property.
     * 
     */
    public void setIsSevenBlock(boolean value) {
        this.isSevenBlock = value;
    }

    /**
     * Gets the value of the redirectOnComplete property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRedirectOnComplete() {
        return redirectOnComplete;
    }

    /**
     * Sets the value of the redirectOnComplete property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRedirectOnComplete(String value) {
        this.redirectOnComplete = value;
    }

    /**
     * Gets the value of the leftResponseASCIIKeyCodeUpper property.
     * 
     */
    public int getLeftResponseASCIIKeyCodeUpper() {
        return leftResponseASCIIKeyCodeUpper;
    }

    /**
     * Sets the value of the leftResponseASCIIKeyCodeUpper property.
     * 
     */
    public void setLeftResponseASCIIKeyCodeUpper(int value) {
        this.leftResponseASCIIKeyCodeUpper = value;
    }

    /**
     * Gets the value of the rightResponseASCIIKeyCodeUpper property.
     * 
     */
    public int getRightResponseASCIIKeyCodeUpper() {
        return rightResponseASCIIKeyCodeUpper;
    }

    /**
     * Sets the value of the rightResponseASCIIKeyCodeUpper property.
     * 
     */
    public void setRightResponseASCIIKeyCodeUpper(int value) {
        this.rightResponseASCIIKeyCodeUpper = value;
    }

    /**
     * Gets the value of the leftResponseASCIIKeyCodeLower property.
     * 
     */
    public int getLeftResponseASCIIKeyCodeLower() {
        return leftResponseASCIIKeyCodeLower;
    }

    /**
     * Sets the value of the leftResponseASCIIKeyCodeLower property.
     * 
     */
    public void setLeftResponseASCIIKeyCodeLower(int value) {
        this.leftResponseASCIIKeyCodeLower = value;
    }

    /**
     * Gets the value of the rightResponseASCIIKeyCodeLower property.
     * 
     */
    public int getRightResponseASCIIKeyCodeLower() {
        return rightResponseASCIIKeyCodeLower;
    }

    /**
     * Sets the value of the rightResponseASCIIKeyCodeLower property.
     * 
     */
    public void setRightResponseASCIIKeyCodeLower(int value) {
        this.rightResponseASCIIKeyCodeLower = value;
    }

    /**
     * Gets the value of the randomizationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRandomizationType() {
        return randomizationType;
    }

    /**
     * Sets the value of the randomizationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRandomizationType(String value) {
        this.randomizationType = value;
    }

    /**
     * Gets the value of the errorMarkID property.
     * 
     */
    public int getErrorMarkID() {
        return errorMarkID;
    }

    /**
     * Sets the value of the errorMarkID property.
     * 
     */
    public void setErrorMarkID(int value) {
        this.errorMarkID = value;
    }

    /**
     * Gets the value of the leftKeyOutlineID property.
     * 
     */
    public int getLeftKeyOutlineID() {
        return leftKeyOutlineID;
    }

    /**
     * Sets the value of the leftKeyOutlineID property.
     * 
     */
    public void setLeftKeyOutlineID(int value) {
        this.leftKeyOutlineID = value;
    }

    /**
     * Gets the value of the rightKeyOutlineID property.
     * 
     */
    public int getRightKeyOutlineID() {
        return rightKeyOutlineID;
    }

    /**
     * Sets the value of the rightKeyOutlineID property.
     * 
     */
    public void setRightKeyOutlineID(int value) {
        this.rightKeyOutlineID = value;
    }

    /**
     * Gets the value of the prefixSelfAlternatingSurveys property.
     * 
     */
    public boolean isPrefixSelfAlternatingSurveys() {
        return prefixSelfAlternatingSurveys;
    }

    /**
     * Sets the value of the prefixSelfAlternatingSurveys property.
     * 
     */
    public void setPrefixSelfAlternatingSurveys(boolean value) {
        this.prefixSelfAlternatingSurveys = value;
    }

    /**
     * Gets the value of the dynamicSpecifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dynamicSpecifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDynamicSpecifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DynamicSpecifier }
     * 
     * 
     */
    public List<DynamicSpecifier> getDynamicSpecifier() {
        if (dynamicSpecifier == null) {
            dynamicSpecifier = new ArrayList<DynamicSpecifier>();
        }
        return this.dynamicSpecifier;
    }

    /**
     * Gets the value of the survey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the survey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSurvey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Survey }
     * 
     * 
     */
    public List<Survey> getSurvey() {
        if (survey == null) {
            survey = new ArrayList<Survey>();
        }
        return this.survey;
    }

    /**
     * Gets the value of the uniqueResponse property.
     * 
     * @return
     *     possible object is
     *     {@link UniqueResponse }
     *     
     */
    public UniqueResponse getUniqueResponse() {
        return uniqueResponse;
    }

    /**
     * Sets the value of the uniqueResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link UniqueResponse }
     *     
     */
    public void setUniqueResponse(UniqueResponse value) {
        this.uniqueResponse = value;
    }

    /**
     * Gets the value of the layout property.
     * 
     * @return
     *     possible object is
     *     {@link IATLayout }
     *     
     */
    public IATLayout getLayout() {
        return layout;
    }

    /**
     * Sets the value of the layout property.
     * 
     * @param value
     *     allowed object is
     *     {@link IATLayout }
     *     
     */
    public void setLayout(IATLayout value) {
        this.layout = value;
    }

    /**
     * Gets the value of the iatEventList property.
     * 
     * @return
     *     possible object is
     *     {@link IATEventList }
     *     
     */
    public IATEventList getIATEventList() {
        return iatEventList;
    }

    /**
     * Sets the value of the iatEventList property.
     * 
     * @param value
     *     allowed object is
     *     {@link IATEventList }
     *     
     */
    public void setIATEventList(IATEventList value) {
        this.iatEventList = value;
    }

    /**
     * Gets the value of the displayItemList property.
     * 
     * @return
     *     possible object is
     *     {@link ConfigFile.DisplayItemList }
     *     
     */
    public ConfigFile.DisplayItemList getDisplayItemList() {
        return displayItemList;
    }

    /**
     * Sets the value of the displayItemList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfigFile.DisplayItemList }
     *     
     */
    public void setDisplayItemList(ConfigFile.DisplayItemList value) {
        this.displayItemList = value;
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

    /**
     * Gets the value of the hasUniqueResponse property.
     * 
     */
    public boolean isHasUniqueResponse() {
        return hasUniqueResponse;
    }

    /**
     * Sets the value of the hasUniqueResponse property.
     * 
     */
    public void setHasUniqueResponse(boolean value) {
        this.hasUniqueResponse = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="IATDisplayItem" type="{http://www.iatsoftware.net/schema}display-item-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "iatDisplayItem"
    })
    public static class DisplayItemList {

        @XmlElement(name = "IATDisplayItem")
        protected List<DisplayItem> iatDisplayItem;

        /**
         * Gets the value of the iatDisplayItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the iatDisplayItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIATDisplayItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DisplayItem }
         * 
         * 
         */
        public List<DisplayItem> getIATDisplayItem() {
            if (iatDisplayItem == null) {
                iatDisplayItem = new ArrayList<DisplayItem>();
            }
            return this.iatDisplayItem;
        }

    }

}
