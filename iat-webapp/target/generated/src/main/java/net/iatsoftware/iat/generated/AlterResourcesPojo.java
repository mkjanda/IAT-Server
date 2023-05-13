//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.12 at 11:08:36 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.Message;


/**
 * <p>Java class for AlterResourcesPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AlterResourcesPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}message-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProductKey" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Activations" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;choice&gt;
 *                     &lt;element name="Activations" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                     &lt;element name="ActivationsInc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                   &lt;/choice&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Administrations" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;choice&gt;
 *                     &lt;element name="Administrations" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                     &lt;element name="AdministrationsInc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                   &lt;/choice&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="DiskAlottmentMB" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;choice&gt;
 *                     &lt;element name="DiskAlottment" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                     &lt;element name="DiskAlottmentInc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                   &lt;/choice&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="NumIATs" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;choice&gt;
 *                     &lt;element name="NumIATs" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                     &lt;element name="NumIATsInc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                   &lt;/choice&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlterResourcesPojo", propOrder = {
    "productKey",
    "activations",
    "administrations",
    "diskAlottmentMB",
    "numIATs"
})
public abstract class AlterResourcesPojo
    extends Message
{

    @XmlElement(name = "ProductKey", required = true)
    protected String productKey;
    @XmlElement(name = "Activations")
    protected AlterResourcesPojo.Activations activations;
    @XmlElement(name = "Administrations")
    protected AlterResourcesPojo.Administrations administrations;
    @XmlElement(name = "DiskAlottmentMB")
    protected AlterResourcesPojo.DiskAlottmentMB diskAlottmentMB;
    @XmlElement(name = "NumIATs")
    protected AlterResourcesPojo.NumIATs numIATs;

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
     * Gets the value of the activations property.
     * 
     * @return
     *     possible object is
     *     {@link AlterResourcesPojo.Activations }
     *     
     */
    public AlterResourcesPojo.Activations getActivations() {
        return activations;
    }

    /**
     * Sets the value of the activations property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlterResourcesPojo.Activations }
     *     
     */
    public void setActivations(AlterResourcesPojo.Activations value) {
        this.activations = value;
    }

    /**
     * Gets the value of the administrations property.
     * 
     * @return
     *     possible object is
     *     {@link AlterResourcesPojo.Administrations }
     *     
     */
    public AlterResourcesPojo.Administrations getAdministrations() {
        return administrations;
    }

    /**
     * Sets the value of the administrations property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlterResourcesPojo.Administrations }
     *     
     */
    public void setAdministrations(AlterResourcesPojo.Administrations value) {
        this.administrations = value;
    }

    /**
     * Gets the value of the diskAlottmentMB property.
     * 
     * @return
     *     possible object is
     *     {@link AlterResourcesPojo.DiskAlottmentMB }
     *     
     */
    public AlterResourcesPojo.DiskAlottmentMB getDiskAlottmentMB() {
        return diskAlottmentMB;
    }

    /**
     * Sets the value of the diskAlottmentMB property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlterResourcesPojo.DiskAlottmentMB }
     *     
     */
    public void setDiskAlottmentMB(AlterResourcesPojo.DiskAlottmentMB value) {
        this.diskAlottmentMB = value;
    }

    /**
     * Gets the value of the numIATs property.
     * 
     * @return
     *     possible object is
     *     {@link AlterResourcesPojo.NumIATs }
     *     
     */
    public AlterResourcesPojo.NumIATs getNumIATs() {
        return numIATs;
    }

    /**
     * Sets the value of the numIATs property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlterResourcesPojo.NumIATs }
     *     
     */
    public void setNumIATs(AlterResourcesPojo.NumIATs value) {
        this.numIATs = value;
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
     *         &lt;choice&gt;
     *           &lt;element name="Activations" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *           &lt;element name="ActivationsInc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;/choice&gt;
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
        "activations",
        "activationsInc"
    })
    public static class Activations {

        @XmlElement(name = "Activations")
        protected Integer activations;
        @XmlElement(name = "ActivationsInc")
        protected Integer activationsInc;

        /**
         * Gets the value of the activations property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getActivations() {
            return activations;
        }

        /**
         * Sets the value of the activations property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setActivations(Integer value) {
            this.activations = value;
        }

        /**
         * Gets the value of the activationsInc property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getActivationsInc() {
            return activationsInc;
        }

        /**
         * Sets the value of the activationsInc property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setActivationsInc(Integer value) {
            this.activationsInc = value;
        }

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
     *         &lt;choice&gt;
     *           &lt;element name="Administrations" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *           &lt;element name="AdministrationsInc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;/choice&gt;
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
        "administrations",
        "administrationsInc"
    })
    public static class Administrations {

        @XmlElement(name = "Administrations")
        protected Integer administrations;
        @XmlElement(name = "AdministrationsInc")
        protected Integer administrationsInc;

        /**
         * Gets the value of the administrations property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getAdministrations() {
            return administrations;
        }

        /**
         * Sets the value of the administrations property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setAdministrations(Integer value) {
            this.administrations = value;
        }

        /**
         * Gets the value of the administrationsInc property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getAdministrationsInc() {
            return administrationsInc;
        }

        /**
         * Sets the value of the administrationsInc property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setAdministrationsInc(Integer value) {
            this.administrationsInc = value;
        }

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
     *         &lt;choice&gt;
     *           &lt;element name="DiskAlottment" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *           &lt;element name="DiskAlottmentInc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;/choice&gt;
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
        "diskAlottment",
        "diskAlottmentInc"
    })
    public static class DiskAlottmentMB {

        @XmlElement(name = "DiskAlottment")
        protected Integer diskAlottment;
        @XmlElement(name = "DiskAlottmentInc")
        protected Integer diskAlottmentInc;

        /**
         * Gets the value of the diskAlottment property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getDiskAlottment() {
            return diskAlottment;
        }

        /**
         * Sets the value of the diskAlottment property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setDiskAlottment(Integer value) {
            this.diskAlottment = value;
        }

        /**
         * Gets the value of the diskAlottmentInc property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getDiskAlottmentInc() {
            return diskAlottmentInc;
        }

        /**
         * Sets the value of the diskAlottmentInc property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setDiskAlottmentInc(Integer value) {
            this.diskAlottmentInc = value;
        }

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
     *         &lt;choice&gt;
     *           &lt;element name="NumIATs" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *           &lt;element name="NumIATsInc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;/choice&gt;
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
        "numIATs",
        "numIATsInc"
    })
    public static class NumIATs {

        @XmlElement(name = "NumIATs")
        protected Integer numIATs;
        @XmlElement(name = "NumIATsInc")
        protected Integer numIATsInc;

        /**
         * Gets the value of the numIATs property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getNumIATs() {
            return numIATs;
        }

        /**
         * Sets the value of the numIATs property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setNumIATs(Integer value) {
            this.numIATs = value;
        }

        /**
         * Gets the value of the numIATsInc property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getNumIATsInc() {
            return numIATsInc;
        }

        /**
         * Sets the value of the numIATsInc property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setNumIATsInc(Integer value) {
            this.numIATsInc = value;
        }

    }

}
