/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.forms;

/**
 *
 * @author michael
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetrieveResultForm implements java.io.Serializable {
    private static final long serialVersionUID = 1;
    private String testName, testPassword, productKey, tokenValue, resultsFormat = "ResultsSummary", accessToken;
    
    public RetrieveResultForm(){}
    
    public RetrieveResultForm(String accessToken, String testName, String testPassword, String productKey, String tokenValue) {
        this.accessToken = accessToken;
        this.testName = testName;
        this.testPassword = testPassword;
        this.productKey = productKey;
        this.tokenValue = tokenValue;
    }
    
    public RetrieveResultForm(String accessToken, String testName, String testPassword, String productKey, String tokenValue, String resultsFormat) {
        this.accessToken = accessToken;
        this.testName = testName;
        this.testPassword = testPassword;
        this.productKey = productKey;
        this.tokenValue = tokenValue;
        this.resultsFormat = resultsFormat;
    }

    @JsonProperty("access_token")
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String val) {
        accessToken = val;
    }

    @JsonProperty("test_name")
    public String getTestName() {
        return testName;
    }
    public void setTestName(String val) {
        this.testName = val;
    }
    
    @JsonProperty("test_password")
    public String getTestPassword() {
        return this.testPassword;
    }
    public void setTestPassword(String val) {
        this.testPassword = val;
    }
    
    @JsonProperty("product_key")
    public String getProductKey() {
        return this.productKey;
    }
    public void setProductKey(String val) {
        this.productKey = val;
    }
    
    @JsonProperty("result_set_token")
    public String getTokenValue() {
        return this.tokenValue;
    }
    public void setTokenValue(String val) {
        this.tokenValue = val;
    }

    @JsonProperty("results_format")
    public String getResultsFormat() {
        return this.resultsFormat;
    }
    public void setResultsFormat(String val) {
        this.resultsFormat = val;
    }
}
