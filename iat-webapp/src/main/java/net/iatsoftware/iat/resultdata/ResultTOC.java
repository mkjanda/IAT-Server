/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultdata;

/**
 *
 * @author Michael Janda
 */


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ResultTOC")
@XmlAccessorType(XmlAccessType.NONE)
public class ResultTOC extends net.iatsoftware.iat.generated.ResultTOCPojo {
    public ResultTOC() {}
    
    @Override
    public int getNumEntries() {
        if ((this.numEntries == 0) && (this.getResultTOCEntry() != null))
            this.numEntries = this.getResultTOCEntry().size();
        return this.numEntries;
    }
    
    public void addEntry(ResultTOCEntry entry) {
        this.getResultTOCEntry().add(entry);
    }
}
