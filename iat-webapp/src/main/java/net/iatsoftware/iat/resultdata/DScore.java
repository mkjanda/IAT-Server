/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultdata;

/**
 *
 * @author michael
 */

import net.iatsoftware.iat.controllers.RestException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.crypto.Cipher;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class DScore extends AbstractResultData {
    
    private String score;
    
    public DScore() {}
    
    public DScore(Cipher c) {
        this.score = "Unscored";
    }
    
    private long sum(Long[] vals) {
        long sum = 0;
        for (Long l : vals)
            sum += l;
        return sum;
    }
    
    private double mean(Long[] vals) {
        return (double)sum(vals) / vals.length;
    }
    
    private double sd(Long[] vals) {
        double mean = mean(vals);
        double sd = 0;
        for (Long l : vals)
            sd += (l - mean) * (l - mean);
        return Math.sqrt(sd / (mean - 1));
    }

    @Override
    public void compute(byte[] resultData, int iatPos) throws RestException {
        try {
            ResultSetElementList results = decryptIatResults(resultData, iatPos);
            score(results);
        }
        catch (Exception ex) {
            throw new RestException("Error computing d-score", ex);
        }
    }
    
    protected void score(ResultSetElementList results) {
        
        long nLT300 = results.getIATResultSetElement().stream().filter(elem -> elem.getResponseTime() < 300).count();
        if (nLT300 >= results.getIATResultSetElement().size() / 10) {
            this.score = "Unscored due to exceptionally low latencies";
            return;
        }
        Long[] latencies3 = results.getIATResultSetElement().stream().filter(elem -> elem.getBlockNum() == 3).filter(elem -> elem.getResponseTime() < 10_000L).map(elem -> elem.getResponseTime()).toArray(Long[]::new);
        Long[] latencies4 = results.getIATResultSetElement().stream().filter(elem -> elem.getBlockNum() == 4).filter(elem -> elem.getResponseTime() < 10_000L).map(elem -> elem.getResponseTime()).toArray(Long[]::new);
        Long[] latencies6 = results.getIATResultSetElement().stream().filter(elem -> elem.getBlockNum() == 6).filter(elem -> elem.getResponseTime() < 10_000L).map(elem -> elem.getResponseTime()).toArray(Long[]::new);
        Long[] latencies7 = results.getIATResultSetElement().stream().filter(elem -> elem.getBlockNum() == 7).filter(elem -> elem.getResponseTime() < 10_000L).map(elem -> elem.getResponseTime()).toArray(Long[]::new);
        Long[] latencies_3_6 = new Long[latencies3.length + latencies6.length], latencies_4_7 = new Long[latencies4.length + latencies7.length];
        System.arraycopy(latencies3, 0, latencies_3_6, 0, latencies3.length);
        System.arraycopy(latencies6, 0, latencies_3_6, latencies3.length, latencies6.length);
        System.arraycopy(latencies4, 0, latencies_4_7, 0, latencies4.length);
        System.arraycopy(latencies7, 0, latencies_4_7, latencies4.length, latencies7.length);
        double sd36 = sd(latencies_3_6), sd47 = sd(latencies_4_7);
        double md63 = mean(latencies6) - mean(latencies3), md74 = mean(latencies7) - mean(latencies4);
        NumberFormat df = new DecimalFormat("####.########");
        this.score = df.format(((md63 / sd36) + (md74 / sd47)) / 2);
    }
    
    @XmlElement(name="score")
    public String getScore() {
        return this.score;
    }
}
