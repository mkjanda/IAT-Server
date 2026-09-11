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


@XmlRootElement(name="IATResultFragment")
@XmlAccessorType(XmlAccessType.NONE)
public class IATResultFragment extends net.iatsoftware.iat.generated.GIATResultFragment {
    public IATResultFragment(){}
    public IATResultFragment(int item, int block, long latency, int presentationNum, boolean error) {
        this.itemNum = item;
        this.blockNum = block;
        this.responseTime = latency;
        this.presentationNum = presentationNum;
        this.error = error;
    }
}
