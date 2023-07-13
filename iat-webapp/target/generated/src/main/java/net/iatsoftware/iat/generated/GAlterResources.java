//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.07.10 at 04:13:23 AM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.Message;


/**
 * <p>Java class for GAlterResources complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GAlterResources"&gt;
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
@XmlType(name = "GAlterResources", propOrder = {
    "productKey",
    "activations",
    "administrations",
    "diskAlottmentMB",
    "numIATs"
})
public abstract class GAlterResources
    extends Message
{

    @XmlElement(name = "ProductKey", required = true)
    protected String productKey;
    @XmlElement(name = "Activations")
    protected GAlterResources.Activations activations;
    @XmlElement(name = "Administrations")
    protected GAlterResources.Administrations administrations;
    @XmlElement(name = "DiskAlottmentMB")
    protected GAlterResources.DiskAlottmentMB diskAlottmentMB;
    @XmlElement(name = "NumIATs")
    protected GAlterResources.NumIATs numIATs;

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
     *     {@link GAlterResources.Activations }
     *     
     */
    public GAlterResources.Activations getActivations() {
        return activations;
    }

    /**
     * Sets the value of the activations property.
     * 
     * @param value
     *     allowed object is
     *     {@link GAlterResources.Activations }
     *     
     */
    public void setActivations(GAlterResources.Activations value) {
        this.activations = value;
    }

    /**
     * Gets the value of the administrations property.
     * 
     * @return
     *     possible object is
     *     {@link GAlterResources.Administrations }
     *     
     */
    public GAlterResources.Administrations getAdministrations() {
        return administrations;
    }

    /**
     * Sets the value of the administrations property.
     * 
     * @param value
     *     allowed object is
     *     {@link GAlterResources.Administrations }
     *     
     */
    public void setAdministrations(GAlterResources.Administrations value) {
        this.administrations = value;
    }

    /**
     * Gets the value of the diskAlottmentMB property.
     * 
     * @return
     *     possible object is
     *     {@link GAlterResources.DiskAlottmentMB }
     *     
     */
    public GAlterResources.DiskAlottmentMB getDiskAlottmentMB() {
        return diskAlottmentMB;
    }

    /**
     * Sets the value of the diskAlottmentMB property.
     * 
     * @param value
     *     allowed object is
     *     {@link GAlterResources.DiskAlottmentMB }
     *     
     */
    public void setDiskAlottmentMB(GAlterResources.DiskAlottmentMB value) {
        this.diskAlottmentMB = value;
    }

    /**
     * Gets the value of the numIATs property.
     * 
     * @return
     *     possible object is
     *     {@link GAlterResources.NumIATs }
     *     
     */
    public GAlterResources.NumIATs getNumIATs() {
        return numIATs;
    }

    /**
     * Sets the value of the numIATs property.
     * 
     * @param value
     *     allowed object is
     *     {@link GAlterResources.NumIATs }
     *     
     */
    public void setNumIATs(GAlterResources.NumIATs value) {
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
