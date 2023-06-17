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
/*
import java.text.DateFormat;
import java.util.Date;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name="ClientSessionUsage")
@XmlAccessorType(XmlAccessType.NONE)
public class ClientEntityUsage extends net.iatsoftware.iat.generated.GGClientEntityUsage {
    private static final DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM);
    
    public ClientEntityUsage(){}
    
    @Override
    public boolean doBeforeMarshal(Marshaller m) {
        for (EntityCreation ec : super.entityCreation)
            ec.setTimeCreatedString(df.format(new Date(ec.getTimeCreatedTicks())));
        super.firstCreation = df.format(new Date(super.entityCreation.get(0).getTimeCreatedTicks()));
        super.lastCreation = df.format(new Date(super.entityCreation.get(super.entityCreation.size() - 1).getTimeCreatedTicks()));
        return true;
    }
    
    @Override
    public void doAfterUnmarshal(Unmarshaller um, Object parent) {}
}
*/