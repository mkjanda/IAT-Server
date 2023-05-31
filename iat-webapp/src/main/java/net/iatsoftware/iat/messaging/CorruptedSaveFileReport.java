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


import java.time.format.FormatStyle;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

        
@XmlRootElement(name="CorruptedSaveFileReport")       
@XmlAccessorType(XmlAccessType.NONE)
public class CorruptedSaveFileReport extends net.iatsoftware.iat.generated.CorruptedSaveFileReportPojo {
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.LONG);
    
    @Override
    public void doAfterUnmarshal(Unmarshaller um, Object parent) {
    	setReportTime(dateFormat.format(Calendar.getInstance().toInstant()));
    }
}
