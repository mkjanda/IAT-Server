/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.entities.ProductRequestEntity;

import java.util.ArrayList;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;


@XmlType(name="ProductRequests")
@XmlAccessorType(XmlAccessType.NONE)
public class ProductRequests extends net.iatsoftware.iat.generated.ProductRequestsPojo {
   
    public ProductRequests(Iterable<ProductRequestEntity> requests) {
        this.productRequest = new ArrayList<>();
        requests.spliterator().forEachRemaining((r) -> { this.productRequest.add(new ProductRequest(r)); });
    }
    
}
