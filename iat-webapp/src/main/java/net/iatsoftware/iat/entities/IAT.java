/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.entities;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.generated.TokenType;
import net.iatsoftware.iat.messaging.Manifest;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Lob;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Basic;
import javax.persistence.Index;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Calendar;
import javax.xml.bind.JAXBContext;
import javax.xml.transform.stream.StreamSource;

@Entity
@Table(name = "tests", indexes = {
    @Index(name = "tests", columnList = "client_id, test_name")
})
public class IAT implements java.io.Serializable {
    private static final long serialVersionUID = 1;
    private long id = -1;
    private int numAdministrations = 0, testSizeKB = 0;
    private String testName, URL, oauthClientSecret = null, oauthClientRedirect = null, oauthClientId = null;
    private TestType testType = TestType.SetNumberOfPresentations;
    private Calendar uploadTimestamp, lastDataRetrieval, resultRetrievalTokenAge;
    private boolean alternate = false, alternated = false, oauthSubpathRedirects = false, redeployed = false;
    private byte[] deploymentDescriptor, resultRetrievalToken;
    private String aesCode, redirectOnComplete;
    private DeploymentSession deploymentSession = null;
    private Client client;
    private int resultFormat, numElements = -1;
    private User user;
    private UniqueResponseItem uniqueResponseItem;
    private String itemSlideDownloadKey = null, version;
    private TokenType tokenType = TokenType.NONE;
    private String tokenName = "";
    private JAXBContext marshaller = null;
    private String manifestXml;    
    public IAT() throws javax.xml.bind.JAXBException {
        marshaller = JAXBContext.newInstance(Manifest.class);        
    }
    
    public IAT(Client c, User u, String testName, String version, int resultFormat, Calendar uploadStart) 
            throws javax.xml.bind.JAXBException {
        this.resultFormat = resultFormat;
        this.version = version;
        this.client = c;
        this.user = u;
        this.testName = testName;
        uploadTimestamp = uploadStart;
        lastDataRetrieval = null;
        alternate = false;
        URL = "http://www.iatsoftware.net/IAT?IATName=" + testName + "&ClientID=" + c.getClientId();
        marshaller = JAXBContext.newInstance(Manifest.class);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TestID")
    public long getId() {
        return this.id;
    }

    public void setId(long val) {
        this.id = val;
    }

    @Basic
    @Column(name = "test_name")
    public String getTestName() {
        return this.testName;
    }

    public void setTestName(String val) {
        this.testName = val;
    }

    
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="client_id", referencedColumnName="ClientID")
    public Client getClient() {
        return this.client;
    }
    public void setClient(Client val) {
        this.client = val;
    }

    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="user_id", referencedColumnName="UserID")
    public User getUser() {
        return this.user;
    }
    public void setUser(User val) {
        this.user = val;
    }
    
    @OneToOne(fetch=FetchType.EAGER, optional=true, mappedBy="test")
    public DeploymentSession getDeploymentSession() {
        return this.deploymentSession;
    }
    public void setDeploymentSession(DeploymentSession ds) {
        this.deploymentSession = ds;
    }

    @Basic
    @Column(name = "num_administrations")
    public int getNumAdministrations() {
        return this.numAdministrations;
    }

    public void setNumAdministrations(int val) {
        this.numAdministrations = val;
    }

    @Basic
    @Column(name = "test_size_kb")
    public int getTestSizeKB() {
        return this.testSizeKB;
    }

    public void setTestSizeKB(int val) {
        this.testSizeKB = val;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "test_type")
    public TestType getTestType() {
        return this.testType;
    }

    public void setTestType(TestType val) {
        this.testType = val;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upload_timestamp")
    public Calendar getUploadTimestamp() {
        return this.uploadTimestamp;
    }

    public void setUploadTimestamp(Calendar val) {
        this.uploadTimestamp = val;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_data_retrieval")
    public Calendar getLastDataRetrieval() {
        return this.lastDataRetrieval;
    }

    public void setLastDataRetrieval(Calendar val) {
        this.lastDataRetrieval = val;
    }

    @Basic
    @Column(name = "alternate")
    public boolean isAlternate() {
        return this.alternate;
    }


    public void setAlternate(boolean val) {
        this.alternate = val;
    }

    @Basic
    @Column(name="version")
    public String getVersion() {
        return this.version;
    }
    public void setVersion(String val) {
        this.version = val;
    }
    
    @Basic
    @Column(name = "result_format")
    public int getResultFormat() {
        return this.resultFormat;
    }

    public void setResultFormat(int val) {
        this.resultFormat = val;
    }

    @Lob
    @Column(name = "aes_code")
    public String getAESCode() {
        return this.aesCode;
    }

    public void setAESCode(String val) {
        this.aesCode = val;
    }

    @Lob
    @Column(name = "deployment_descriptor")
    public byte[] getDeploymentDescriptor() {
        return this.deploymentDescriptor;
    }

    public void setDeploymentDescriptor(byte[] val) {
        this.deploymentDescriptor = val;
    }

    @Basic
    @Column(name = "alternated")
    public boolean getAlternated() {
        return this.alternated;
    }

    public void setAlternated(boolean val) {
        this.alternated = val;
    }
    
    @Basic
    @Column(name = "redirect_on_complete")
    public String getRedirectOnComplete() {
        return this.redirectOnComplete;
    }
    public void setRedirectOnComplete(String val) {
        this.redirectOnComplete = val;
    }
    
    @Basic
    @Column(name = "num_elems")
    public int getNumElements() {
        return this.numElements;
    }
    public void setNumElements(int val) {
        this.numElements = val;
    }
    
    @OneToOne(optional=true, mappedBy="test", fetch=FetchType.EAGER)
    public UniqueResponseItem getUniqueResponseItem() {
        return this.uniqueResponseItem;
    }
    public void setUniqueResponseItem(UniqueResponseItem val) {
        this.uniqueResponseItem = val;
    }
    
    @Basic
    @Column(name="item_slide_download_key")
    public String getItemSlideDownloadKey() {
        return this.itemSlideDownloadKey;
    }
    public void setItemSlideDownloadKey(String val) {
        this.itemSlideDownloadKey = val;
    }
    
    @Basic
    @Column(name="url")
    public String getURL() {
        return URL;
    }
    public void setURL(String val) {
        this.URL = val;
    }
    
    @Basic
    @Column(name="oauth_client_redirect")
    public String getOauthClientRedirect() {
        return this.oauthClientRedirect;
    }
    public void setOauthClientRedirect(String val) {
        this.oauthClientRedirect = val;
    }
    
    @Basic
    @Column(name="oauth_subpath_redirects")
    public boolean isOauthSubpathRedirects() {
        return this.oauthSubpathRedirects;
    }
    public void setOauthSubpathRedirects(boolean val) {
        this.oauthSubpathRedirects = val;
    }

    @Basic
    @Column(name="oauth_client_id")
    public String getOauthClientId() {
        return this.oauthClientId;
    }
    public void setOauthClientId(String val) {
        this.oauthClientId = val;
    }
    
    @Basic
    @Column(name="oauth_client_secret")
    public String getOauthClientSecret()
    {
        return this.oauthClientSecret;
    }
    public void setOauthClientSecret(String val) {
        this.oauthClientSecret = val;
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name="token_type")
    public TokenType getTokenType() {
        return this.tokenType;
    }
    public void setTokenType(TokenType val) {
        this.tokenType = val;
    }
    
    @Basic
    @Column(name="token_name")
    public String getTokenName() {
        return this.tokenName;
    }
    public void setTokenName(String val) {
        this.tokenName = val;
    }
    
    @Lob
    @Column(name="result_retrieval_token")
    public byte[] getResultRetrievalToken() {
        return this.resultRetrievalToken;
    }
    public void setResultRetrievalToken(byte[] val) {
        this.resultRetrievalToken = val;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="result_retrieval_token_age")
    public Calendar getResultRetrievalTokenAge() {
        return resultRetrievalTokenAge;
    }
    public void setResultRetrievalTokenAge(Calendar val) {
        resultRetrievalTokenAge = val;
    }

    @Lob
    @Column(name="manifest")
    public String getManifestXml() {
        return manifestXml;
    }
    public void setManifestXml(String val) {
        manifestXml = val;
    }

    @Transient
    public Manifest getManifest() throws javax.xml.bind.JAXBException {
        var um = marshaller.createUnmarshaller();
        return (Manifest)um.unmarshal(new StreamSource(new StringReader(getManifestXml())));
    }
    @Transient
    public void setManifest(Manifest val) throws javax.xml.bind.JAXBException {
        var m = marshaller.createMarshaller();
        var w = new StringWriter();
        m.marshal(val, w);
        setManifestXml(w.toString());
    }

    @Basic
    @Column(name="redeployed")
    public boolean isRedeployed() {
        return this.redeployed;
    }
    public void setRedeployed(boolean val) {
        redeployed = val;
    }
}
