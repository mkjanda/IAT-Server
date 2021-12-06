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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name="Line")
@XmlAccessorType(XmlAccessType.NONE)
public class CodeLine extends net.iatsoftware.iat.generated.EncCodeLinePojo {
    
    public CodeLine(){}
    
    public CodeLine(EncCodeLine encLine) {
        super();
        this.andx = encLine.getAndx();
        this.bndx = encLine.getBndx();
        this.cl = encLine.getCL();
        this.getSegment().add(new Segment(encLine.getCode1()));
        this.getSegment().add(new Segment(encLine.getCode2()));
        this.getSegment().add(new Segment(encLine.getCode3()));
        this.getSegment().add(new Segment(encLine.getCode4()));
        this.type = encLine.getType();
    }
}
