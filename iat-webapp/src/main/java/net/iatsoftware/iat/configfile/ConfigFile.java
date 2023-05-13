/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.configfile;

/**
 *
 * @author Michael Janda
 */

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;


@XmlRootElement(name="ConfigFile")
@XmlAccessorType(XmlAccessType.NONE)
public class ConfigFile extends net.iatsoftware.iat.generated.ConfigFile {
    private int numPresentations = -1;
    
    public int getNumPresentations()
    {
/*
        this.eventList.getBeginIATBlockAndEndIATBlockAndBeginInstructionBlock()
        if (numPresentations != -1)
            return numPresentations;
        numPresentations = 0;
        
        for (int ctr = 0; ctr < this.eventList.getEvents().size(); ctr++)
            if (this.iatEventList.getIATEvents().get(ctr).getEventType().equals(IATEventType.BEGIN_IAT_BLOCK))
                numPresentations += ((BeginIATBlock)this.iatEventList.getIATEvents().get(ctr)).getNumPresentations();
  */              
        return numPresentations;
    }

}
