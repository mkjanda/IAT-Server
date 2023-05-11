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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import java.util.ArrayList;

@XmlRootElement(name="Segment")
@XmlAccessorType(XmlAccessType.NONE)
public class Segment extends net.iatsoftware.iat.generated.SegmentPojo {
    public Segment() {
    }
    
    public Segment(String code) {
        String str = code;
        this.segmentPart = new ArrayList<>();
        while (str.length() > 1000) {
            this.segmentPart.add(str.substring(0, 1000));
            str = str.substring(1000);
        }
        this.segmentPart.add(str);
    }
}
