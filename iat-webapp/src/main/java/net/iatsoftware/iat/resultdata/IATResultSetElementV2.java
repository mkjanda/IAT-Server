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

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="IATResultSetElement")
@XmlAccessorType(XmlAccessType.NONE)
public class IATResultSetElementV2 extends net.iatsoftware.iat.generated.IATResultSetElementV2Pojo {
    public IATResultSetElementV2(){}
    public IATResultSetElementV2(int item, int block, long latency, int presentationNum, boolean error) {
        this.error = error;
        this.itemNum = item;
        this.responseTime = latency;
        this.presentationNum = presentationNum;
        this.blockNum = block;
    }
}
