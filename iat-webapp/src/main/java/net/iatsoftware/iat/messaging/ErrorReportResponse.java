package net.iatsoftware.iat.messaging;

import net.iatsoftware.iat.generated.ErrorReportResponseCode;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ErrorReportResponse")
@XmlAccessorType(XmlAccessType.NONE)
public class ErrorReportResponse extends net.iatsoftware.iat.generated.GErrorReportResponse {
    public ErrorReportResponse(){}
    public ErrorReportResponse(ErrorReportResponseCode code) {
        this.response = code;
    }

}