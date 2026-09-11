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


import java.util.Map;
import java.util.stream.IntStream;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "IATResult")
@XmlAccessorType(XmlAccessType.NONE)
public class IATResult extends net.iatsoftware.iat.generated.GIATResult {
    public IATResult() {
    }

    public void parseResults(Map<String, String> responseData) {
        IntStream.rangeClosed(1, responseData.size()).forEach(i -> {
            var ndx = Integer.toString(i);
            if (!responseData.containsKey("Item" + ndx) ||
                    !responseData.containsKey("Latency" + ndx) ||
                    !responseData.containsKey("Block" + ndx) ||
                    !responseData.containsKey("Error" + ndx)) {
                return;
            }
            int itemNum = Integer.parseInt(responseData.get("Item" + ndx));
            long latency = Long.parseLong(responseData.get("Latency" + ndx));
            int block = Integer.parseInt(responseData.get("Block" + ndx));
            boolean error = Boolean.parseBoolean(responseData.get("Error" + ndx));
            this.getFragment().add(new IATResultFragment(itemNum, block, latency, i, error));
        });
        this.setNumElements(this.getFragment().size());
    }
}
