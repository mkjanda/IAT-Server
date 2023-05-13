/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.deployment;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.config.MyBeanFactory;
import net.iatsoftware.iat.configfile.ConfigFile;
import net.iatsoftware.iat.configfile.Survey;
import net.iatsoftware.iat.configfile.UniqueResponse;
import net.iatsoftware.iat.dataservices.XsltService;
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.JavaScript;
import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.entities.TestSegment;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.entities.UniqueResponseItem;
import net.iatsoftware.iat.events.WebSocketDataSent;
import net.iatsoftware.iat.generated.DeploymentStage;
import net.iatsoftware.iat.generated.ResourceType;
import net.iatsoftware.iat.generated.TokenType;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;
import net.iatsoftware.iat.messaging.UploadRequest;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.services.DeploymentService;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.concurrent.Future;
import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public abstract class DefaultBaseIATDeployer implements BaseIATDeployer {
    public static final int TEST_DEPLOYED = 1;
    public static final int ITEM_SLIDES_DEPLOYED = 2;
    protected static final Logger logger = LogManager.getLogger();
    protected static final Logger criticalLogger = LogManager.getLogger("critical");
    private boolean bItemSlidesDeployed = false, bTestDeployed = false;
    protected Long clientID = 1L, testId = -1L, deploymentSessionId = -1L;
    protected String sessionId = null;
    protected ConfigFile CF;
    protected byte[] IATSource;
    protected byte[][] SurveySources;
    protected Processor XsltProcessor;
    protected GlobalVarNameTable GlobalVars = new GlobalVarNameTable(), AESGlobals;
    protected MessageDigest DeploymentDescriptor;
    protected DeploymentProgress deploymentProgress = null;
    protected boolean complete = false;
    protected Future<?> generationFuture = null;
    protected IAT test;

    @Inject
    protected IATRepositoryManager iatRepositoryManager;
    @Inject
    protected MyBeanFactory beanFactory;
    @Inject
    protected ThreadPoolTaskScheduler scheduler;
    @Inject
    protected XsltService compiledXSLT;
    @Inject
    protected ApplicationEventPublisher eventPublisher;
    @Inject
    protected MyBeanFactory iatServerBeanFactory;
    @Inject
    protected Marshaller marshaller;
    @Inject
    protected Unmarshaller unmarshaller;
    @Inject
    protected DeploymentService deploymentService;
    @Inject
    @Named("ServerConfiguration")
    protected Properties serverConfiguration;

    public DefaultBaseIATDeployer(Long clientId, Long deploymentId, Long testId, String sessId) {
        try {
            clientID = clientId;
            this.deploymentSessionId = deploymentId;
            this.testId = testId;
            sessionId = sessId;
            this.XsltProcessor = new Processor(false);
            this.DeploymentDescriptor = MessageDigest.getInstance("MD5");
        } catch (java.security.NoSuchAlgorithmException ex) {
            criticalLogger.error("Error creating deployment descriptor digest", ex);
        }
    }

    public void setClientId(Long clientId) {
        this.clientID = clientId;
    }

    public void setDeploymentId(Long deploymentId) {
        this.deploymentSessionId = deploymentId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public void setSessionId(String sessId) {
        this.deploymentProgress = new DeploymentProgress(sessId, eventPublisher);
    }

    protected abstract void generateTest();

    public void setRecorded(ResourceType type) {
        if (type == ResourceType.DEPLOYMENT_FILE)
            bTestDeployed = true;
        if (type == ResourceType.ITEM_SLIDE)
            bItemSlidesDeployed = true;
        if (bItemSlidesDeployed && bTestDeployed)
            generateTest();
    }

    @Override
    public void requestUpload(String sessID) throws java.net.URISyntaxException {
        logger.error("Upload request message received");
        UploadRequest upReq = new UploadRequest(this.deploymentSessionId);
        DeploymentSession ds = this.iatRepositoryManager.getDeploymentSession(this.deploymentSessionId);
        ds.setDeploymentUploadKey(upReq.getDataUploadKey());
        ds.setItemSlideUploadKey(upReq.getItemSlideUploadKey());
        ds.setReconnectionKey(upReq.getReconnectionKey());
        iatRepositoryManager.updateDeploymentSession(ds);
        this.eventPublisher.publishEvent(new WebSocketDataSent(sessID, new Envelope(upReq)));
        logger.info("Upload request message processed");
        this.deploymentProgress = new DeploymentProgress(sessID, eventPublisher);
    }

    @Override
    public void storeRSAKeys(PartiallyEncryptedRSAKey adminKey, PartiallyEncryptedRSAKey dataKey) {
        IAT test = iatRepositoryManager.getIAT(this.testId);
        logger.info("RSA key received");
        dataKey.setTest(test);
        iatRepositoryManager.storeEncryptionKey(dataKey);
        adminKey.setTest(test);
        iatRepositoryManager.storeEncryptionKey(adminKey);
        logger.info("RSA Key recorded");
    }

    @Override
    public void storeTokenDefinition(TokenType tokType, String tokenName) {
        iatRepositoryManager.setTokenDefinition(this.testId, tokType, tokenName);
    }

    public Long getTestId() {
        return this.testId;
    }

    public void abort() {
        if (generationFuture != null)
            generationFuture.cancel(true);
        generationFuture = null;
    }

    public void setFailed(String sessId, ServerExceptionMessage serverEx) {
        onFailure(sessId, serverEx);
    }

    public void setSuccess(String sessId) {
        onSuccess(sessId);
    }

    protected abstract void onFailure(String sessId, ServerExceptionMessage ex);

    protected abstract void onSuccess(String sessId);
/* 
    protected void loadTransformSources(IAT test) throws DeploymentTerminationException {
        try {
            this.IATSource = iatRepositoryManager.getTestResource(test, 0L).getResourceBytes();
            var cfString = new String(this.IATSource, StandardCharsets.UTF_16);
            this.CF = (ConfigFile) unmarshaller.unmarshal(new StreamSource(new StringReader(cfString)));
            if (this.CF.getSurvey().size() > 0) {
                SurveySources = new byte[this.CF.getSurvey().size()][];
                for (int ctr = 1; ctr <= SurveySources.length; ctr++) {
                    this.SurveySources[ctr] = iatRepositoryManager
                            .getTestResource(test, (long)ctr).getResourceBytes();
                }
            } else {
                SurveySources = null;
            }
        } catch (java.io.IOException ex) {
            throw new DeploymentTerminationException("Error loading transform sources during test deployment", ex);
        }
    }
*/
    protected void digestTestSegment(StreamSource src, XsltExecutable digestTransX)
            throws DeploymentTerminationException {
        try {
            XsltTransformer transformer = digestTransX.load();
            transformer.setSource(src);
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            Serializer ser = XsltProcessor.newSerializer(bOut);
            transformer.setDestination(ser);
            transformer.transform();
            DeploymentDescriptor.update(bOut.toByteArray());
        } catch (net.sf.saxon.s9api.SaxonApiException ex) {
            throw new DeploymentTerminationException("XSLT error while preparing file descriptor", ex);
        }
    }

    public byte[] transform(byte[] input, XsltExecutable trans)
            throws net.sf.saxon.s9api.SaxonApiException {
        var transformer = trans.load();
        transformer.setSource(new StreamSource(new ByteArrayInputStream(input)));
        var bOut = new ByteArrayOutputStream();
        var ser = XsltProcessor.newSerializer(bOut);
        transformer.setDestination(ser);
        transformer.transform();
        return bOut.toByteArray();
    }

    protected byte[] transformAndMungeCode(byte[] input, XsltExecutable initialTrans)
            throws net.sf.saxon.s9api.SaxonApiException {
        var transformedData = transform(input, initialTrans);
        var mungedData = transform(transformedData, compiledXSLT.getJSCodeSegmentX());
        return transform(mungedData, compiledXSLT.getPostMungeX());
    }

    private void generateIAT() throws DeploymentTerminationException {
        deploymentProgress.setStage(DeploymentStage.GENERATING_IAT);
        deploymentProgress.setActiveElement(test.getTestName());
        try {
            var iatConfiguration = iatRepositoryManager.getTestResource(test, 0L);
            var jsBytes1 = transformAndMungeCode(iatConfiguration.getResourceBytes(), compiledXSLT.getIATHeaderX());
            var jsBytes2 = transformAndMungeCode(iatConfiguration.getResourceBytes(), compiledXSLT.getIATScriptX());
            var jsBytes = new byte[jsBytes1.length + jsBytes2.length + 1];
            System.arraycopy(jsBytes1, 0, jsBytes, 0, jsBytes1.length);
            jsBytes[jsBytes1.length] = "\n".getBytes(java.nio.charset.StandardCharsets.UTF_8)[0];
            System.arraycopy(jsBytes2, 0, jsBytes, jsBytes1.length + 1, jsBytes2.length);
            var js = new JavaScript(test, 0, new String(jsBytes, java.nio.charset.StandardCharsets.UTF_8));
            iatRepositoryManager.addJavaScript(js);
            var bOut = new ByteArrayOutputStream();
            marshaller.marshal(this.CF, new StreamResult(bOut));
            var htmlBytes = transform(bOut.toByteArray(), compiledXSLT.getIATPageX());
            var iatTS = new TestSegment(this.test, test.getTestName(), new String(htmlBytes, StandardCharsets.UTF_8), 
                0, this.CF.getNumBeforeSurveys());
            iatRepositoryManager.addTestSegment(iatTS);
        } catch (net.sf.saxon.s9api.SaxonApiException | java.io.IOException ex) {
            throw new DeploymentTerminationException("XSLT Error while generating files for an IAT", ex);
        }
    }

    private void generateSurvey(Survey survey)
            throws DeploymentTerminationException {
        deploymentProgress.setStage(DeploymentStage.GENERATING_SURVEY);
        deploymentProgress.setActiveElement(String.format("Survey #%d", this.CF.getSurvey().indexOf(survey)));
        try {
            var bOut = new ByteArrayOutputStream();
            marshaller.marshal(survey, new StreamResult(bOut));
            var surveyBytes = bOut.toByteArray();

            var surveyPageBytes = transformAndMungeCode(surveyBytes, compiledXSLT.getSurveyHeaderX());
            var surveyScriptBytes = transformAndMungeCode(surveyBytes, compiledXSLT.getSurveyScriptX());
            var surveyJSBytes = new byte[surveyPageBytes.length + 1 + surveyScriptBytes.length];
            System.arraycopy(surveyPageBytes, 0, surveyJSBytes, 0, surveyPageBytes.length);
            surveyJSBytes[surveyPageBytes.length] = 10;
            System.arraycopy(surveyScriptBytes, 0, surveyJSBytes, surveyPageBytes.length + 1, surveyScriptBytes.length);
            iatRepositoryManager.addJavaScript(new JavaScript(this.test, this.CF.getSurvey().indexOf(survey) + 1, 
                new String(surveyJSBytes, StandardCharsets.UTF_8)));

            var surveyHTMLBytes = transformAndMungeCode(surveyBytes, compiledXSLT.getSurveyPageX());
            var ts = new TestSegment(this.test, survey.getSurveyName(), new String(surveyHTMLBytes, StandardCharsets.UTF_8),
                survey.getAlternationGroup(), survey.getInitialPosition());
            ts.setIat(false);
            iatRepositoryManager.addTestSegment(ts);
        } catch (net.sf.saxon.s9api.SaxonApiException | java.io.IOException ex) {
            throw new DeploymentTerminationException("XSLT Error while generating files for an IAT", ex);
        }
    }

    private void processUniqueResponses(IAT test) {
        UniqueResponseItem uri = new UniqueResponseItem();
        uri.setAdditive(CF.getUniqueResponse().isAdditive());
        uri.setItemNum(CF.getUniqueResponse().getItemNum());
        uri.setSurveyName(CF.getUniqueResponse().getSurveyName());
        uri.setTest(test);
        iatRepositoryManager.addUniqueResponseItem(uri);
        if (!CF.getUniqueResponse().isAdditive())
            iatRepositoryManager.addUniqueResponses(uri, CF.getUniqueResponse().getValue());
        this.deploymentProgress.incProgress();
    }

    protected void doDeploy(IAT test) throws DeploymentTerminationException {
        try {
            this.deploymentProgress = new DeploymentProgress(sessionId, eventPublisher);
        this.deploymentProgress.setStage(DeploymentStage.INITIALIZING_DEPLOYMENT);
        var iatResource = iatRepositoryManager.getTestResource(this.test, 0L);
        var source = new StreamSource(new ByteArrayInputStream(iatResource.getResourceBytes()));
        this.CF = (ConfigFile)this.unmarshaller.unmarshal(source);
        int numStages = (1 + this.CF.getSurvey().size());
        processUniqueResponses(test);
        this.deploymentProgress.setProgressRange(0, numStages);
            test.setRedirectOnComplete(CF.getRedirectOnComplete());
            test.setAlternated(true);
            test.setNumElements(numStages);
            generateIAT();
            this.deploymentProgress.incProgress();
            for (var survey : this.CF.getSurvey()) {
                generateSurvey(survey);
                this.deploymentProgress.incProgress();
            }
            deploymentProgress.setStage(DeploymentStage.FINALIZING_DEPLOYMENT);
            test.setDeploymentDescriptor(DeploymentDescriptor.digest());
            iatRepositoryManager.updateIAT(test);
        } catch (java.io.IOException ex) {
            criticalLogger.error(ex);
        } catch (NullPointerException | org.springframework.orm.jpa.JpaSystemException ex) {
            throw new DeploymentTerminationException("Null pointer", ex);
        }
    }
}
