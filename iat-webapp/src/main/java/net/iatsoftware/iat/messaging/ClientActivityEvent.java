/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

/**
 *
 * @author michael
 */

 import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ClientActivityEvent")
@XmlAccessorType(XmlAccessType.NONE)
public class ClientActivityEvent extends net.iatsoftware.iat.generated.ClientActivityEventPojo {


    @Override
    public boolean doBeforeMarshal(Marshaller m) {
        this.logMessage = super.getTime() + " " + super.getEventType() + " " + super.getTargetType() + "[" + super.id + "]";
        if (super.getParameter().size() > 0) {
            this.logMessage += " (";
            for (int ctr = 0; ctr < super.getParameter().size(); ctr++) {
                this.logMessage += super.getParameter().get(ctr).getName() + ": " + super.getParameter().get(ctr).getValue();
                if (ctr + 1 < super.getParameter().size())
                    this.logMessage += ", ";
            }
            this.logMessage += ")";
        }
        return true;
    }
}
