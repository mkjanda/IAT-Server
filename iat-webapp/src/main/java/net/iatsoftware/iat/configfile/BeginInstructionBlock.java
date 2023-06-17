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

import net.iatsoftware.iat.generated.IATEventType;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="BeginInstructionBlock")
@XmlAccessorType(XmlAccessType.NONE)
public class BeginInstructionBlock extends net.iatsoftware.iat.generated.GBeginInstructionBlock {
    public BeginInstructionBlock() 
    {
        this.eventType = IATEventType.BEGIN_INSTRUCTION_BLOCK;
    }
}
