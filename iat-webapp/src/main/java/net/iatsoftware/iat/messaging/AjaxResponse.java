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

import net.iatsoftware.iat.entities.EncCodeLine;
import net.iatsoftware.iat.generated.AjaxResponseType;

import java.util.List;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name="AjaxResponse")
@XmlAccessorType(XmlAccessType.NONE)
public class AjaxResponse extends net.iatsoftware.iat.generated.AjaxResponsePojo {
    private String textResponse;
    public AjaxResponse(){}
    
    public AjaxResponse(String textResponse) {
        this.processedCode = null;
        this.textResponse = textResponse;
        this.responseType = AjaxResponseType.TEXT;
    }
    
    public AjaxResponse(List<EncCodeLine> encCodeLines) {
        this.response = null;
        this.processedCode = new ProcessedCode();
        for (EncCodeLine encCode : encCodeLines) 
            this.processedCode.getLine().add(new CodeLine(encCode));
        this.responseType = AjaxResponseType.XML;
        this.textResponse = null;
    }
    
    @Override
    public boolean doBeforeMarshal(Marshaller m) {
        super.doBeforeMarshal(m);
        if (this.textResponse != null)
            this.response = "<![CDATA[" + this.textResponse + "]]>";
        return true;
    }
}
