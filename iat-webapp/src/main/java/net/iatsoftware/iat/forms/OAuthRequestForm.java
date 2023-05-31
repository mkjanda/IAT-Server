package net.iatsoftware.iat.forms;

import net.iatsoftware.iat.validation.OAuthIdMatchesSecret;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@OAuthIdMatchesSecret
public class OAuthRequestForm implements java.io.Serializable {
    private static final long serialVersionUID = 1;
    private String client_id;
    private String client_secret;
    private String grant_type;
    private String code;
    
    public OAuthRequestForm(){}
    public OAuthRequestForm(String client_id, String client_secret, String grant_type, String code) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.grant_type = grant_type;
        this.code = code;
    }

    //@JsonProperty("client_id")
    @XmlElement(name="client_id")
    public String getClientId() {
        return client_id;
    }
    public void setClientId(String val) {
        client_id = val;
    }

    //@JsonProperty("client_secret")
    @XmlElement(name="client_secret")
    public String getClientSecret() {
        return client_secret;
    }
    public void setClientSecret(String val) {
        client_secret = val;
    }

    //@JsonProperty("grant_type")
    @XmlElement(name="grant_type")
    public String getGrantType() {
        return grant_type;
    }
    public void setGrantType(String val) {
        grant_type = val;
    }

    //@JsonProperty("code")
    @XmlElement(name="code")
    public String getCode() {
        return code;
    }
    public void setCode(String val) {
        code = val;
    }
}