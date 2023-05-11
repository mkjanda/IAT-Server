//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.09 at 12:27:51 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.entities.ResourcePrice;


/**
 * <p>Java class for PricingPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PricingPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ResourcePrice" type="{http://www.iatsoftware.net/schema}resource-price-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PricingPojo", propOrder = {
    "resourcePrice"
})
public abstract class PricingPojo {

    @XmlElement(name = "ResourcePrice")
    protected List<ResourcePrice> resourcePrice;

    /**
     * Gets the value of the resourcePrice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resourcePrice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResourcePrice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResourcePrice }
     * 
     * 
     */
    public List<ResourcePrice> getResourcePrice() {
        if (resourcePrice == null) {
            resourcePrice = new ArrayList<ResourcePrice>();
        }
        return this.resourcePrice;
    }

}
