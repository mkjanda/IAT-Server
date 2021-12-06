/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultdata;

/**
 *
 * @author michael
 */
import net.iatsoftware.iat.controllers.RestException;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class Results extends ResultsSummary {
    private List<ItemResponseDetails> details = new ArrayList<>();
    
    public Results(){}
    
    @Override
    public void compute(byte[] resultData, int iatPos) throws RestException {
        super.compute(resultData, iatPos);
        ResultSetElementList rElems = this.decryptIatResults(resultData, iatPos);
        this.details = new ArrayList<>();
        for (IATResultSetElementV2 rs : rElems.getIATResultSetElement()) {
            this.details.add(new ItemResponseDetails(rs.getResponseTime(), rs.getBlockNum(), rs.getItemNum(), rs.isError()));
        }
    }
    
    @XmlElement(name="details")
    public List<ItemResponseDetails> getDetails() {
        return this.details;
    }
}
