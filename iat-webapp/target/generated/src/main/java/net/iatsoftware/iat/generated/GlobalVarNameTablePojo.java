//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.12 at 11:08:36 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GlobalVarNameTablePojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GlobalVarNameTablePojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="VarTableEntry" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="OrigName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="NewName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GlobalVarNameTablePojo", propOrder = {
    "varTableEntry"
})
public abstract class GlobalVarNameTablePojo {

    @XmlElement(name = "VarTableEntry", required = true)
    protected List<GlobalVarNameTablePojo.VarTableEntry> varTableEntry;

    /**
     * Gets the value of the varTableEntry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the varTableEntry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVarTableEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GlobalVarNameTablePojo.VarTableEntry }
     * 
     * 
     */
    public List<GlobalVarNameTablePojo.VarTableEntry> getVarTableEntry() {
        if (varTableEntry == null) {
            varTableEntry = new ArrayList<GlobalVarNameTablePojo.VarTableEntry>();
        }
        return this.varTableEntry;
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
     *         &lt;element name="OrigName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="NewName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "origName",
        "newName"
    })
    public static class VarTableEntry {

        @XmlElement(name = "OrigName", required = true)
        protected String origName;
        @XmlElement(name = "NewName", required = true)
        protected String newName;

        /**
         * Gets the value of the origName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOrigName() {
            return origName;
        }

        /**
         * Sets the value of the origName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOrigName(String value) {
            this.origName = value;
        }

        /**
         * Gets the value of the newName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNewName() {
            return newName;
        }

        /**
         * Sets the value of the newName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNewName(String value) {
            this.newName = value;
        }

    }

}
