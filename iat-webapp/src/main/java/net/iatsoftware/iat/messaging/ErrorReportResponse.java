package net.iatsoftware.iat.messaging;

import net.iatsoftware.iat.generated.ErrorReportResponseCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ErrorReportResponse")
@XmlAccessorType(XmlAccessType.NONE)
public class ErrorReportResponse extends net.iatsoftware.iat.generated.ErrorReportResponse {
    public ErrorReportResponse(){}
    public ErrorReportResponse(ErrorReportResponseCode code) {
        this.response = code;
    }

}