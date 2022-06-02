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

import net.iatsoftware.iat.config.IatConfigurationProperties;
import net.iatsoftware.iat.config.MyBeanFactory;
import net.iatsoftware.iat.configfile.ConfigFile;
import net.iatsoftware.iat.configfile.IATSurvey;
import net.iatsoftware.iat.configfile.UniqueResponse;
import net.iatsoftware.iat.dataservices.XsltService;
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.entities.TestSegment;
import net.iatsoftware.iat.entities.UniqueResponseItem;
import net.iatsoftware.iat.events.TestResourcesRecordedEvent;
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
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.inject.Inject;
import javax.xml.transform.stream.StreamSource;

@Component
@Scope(value = "prototype")
public abstract class DefaultBaseIATDeployer implements BaseIATDeployer {

    public static final int TEST_DEPLOYED = 1;
    public static final int ITEM_SLIDES_DEPLOYED = 2;
    protected static final Logger logger = LogManager.getLogger();
    protected static final Logger criticalLogger = LogManager.getLogger("critical");
    private boolean bItemSlidesDeployed = false, bTestDeployed = false;
    protected Long clientID;
    protected ConfigFile CF;
    protected StreamSource IATSource;
    protected StreamSource[] SurveySources;
    protected Processor XsltProcessor;
    protected GlobalVarNameTable GlobalVars = new GlobalVarNameTable(), AESGlobals;
    protected MessageDigest DeploymentDescriptor;
    protected Long testId;
    protected DeploymentProgress deploymentProgress = null;
    protected boolean complete = false;
    protected Long deploymentSessionId = -1L;

    @Inject
    protected Unmarshaller unmarshaller;
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
    protected DeploymentService deploymentService;
    @Inject
    protected IatConfigurationProperties serverConfiguration;

    public DefaultBaseIATDeployer(Long clientID, Long deploymentID, Long testID, String sessionId) {
        try {
            this.XsltProcessor = new Processor(false);
            this.clientID = clientID;
            this.testId = testID;
            this.deploymentSessionId = deploymentID;
            this.DeploymentDescriptor = MessageDigest.getInstance("MD5");
            this.deploymentProgress = new DeploymentProgress(sessionId, eventPublisher);
        } catch (java.security.NoSuchAlgorithmException ex) {
            criticalLogger.error("Error creating deployment descriptor digest", ex);
        }
    }

	@EventListener
	public void testResourcesRecorded(TestResourcesRecordedEvent evt) {
		if (evt.getDeploymentID() != this.deploymentSessionId)
			return;
		if (evt.getType() == ResourceType.DEPLOYMENT_FILE)
			bTestDeployed = true;
		if (evt.getType() == ResourceType.ITEM_SLIDE)
			bItemSlidesDeployed = true;
		if (bTestDeployed && bItemSlidesDeployed)
			generateTest(evt.getSessionId());
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

    public void setFailed(String sessId, ServerExceptionMessage serverEx) {
        onFailure(sessId, serverEx);
    }

    public void setSuccess(String sessId) {
        onSuccess(sessId);
    }

    protected abstract void onFailure(String sessId, ServerExceptionMessage ex);

    protected abstract void onSuccess(String sessId);

    protected void loadTransformSources(IAT test) throws DeploymentTerminationException {
        try {
            var testCF = new String(iatRepositoryManager.getTestResource(test, test.getTestName()).getResource(),
                    StandardCharsets.UTF_16);
            this.IATSource = new StreamSource(new StringReader(testCF));
            this.CF = (ConfigFile) unmarshaller.unmarshal(this.IATSource);
            if (this.CF.getNumBeforeSurveys() + this.CF.getNumAfterSurveys() > 0) {
                SurveySources = new StreamSource[this.CF.getNumBeforeSurveys() + this.CF.getNumAfterSurveys()];
                for (int ctr = 0; ctr < SurveySources.length; ctr++) {
                    var surveyConfig = new String(
                            iatRepositoryManager.getTestResource(test, this.CF.getIATSurvey().get(ctr).getSurveyName())
                                    .getResource(),
                            StandardCharsets.UTF_16);
                    SurveySources[ctr] = new StreamSource(new StringReader(surveyConfig));
                }
            } else {
                SurveySources = null;
            }
        } catch (java.io.IOException ex) {
            throw new DeploymentTerminationException("Error loading transform sources during test deployment", ex);
        }
    }

    private String transSource(StreamSource src, XsltExecutable initTransX, boolean mergeGlobals)
            throws DeploymentTerminationException {
        try {
            XsltTransformer transformer = initTransX.load();
            transformer.setSource(src);
            StringWriter sWriter = new StringWriter();
            Serializer ser = XsltProcessor.newSerializer(sWriter);
            transformer.setDestination(ser);
            transformer.transform();

            StringReader sReader = new StringReader(sWriter.toString());
            StreamSource sSource = new StreamSource(sReader);
            sWriter = new StringWriter();

            transformer = compiledXSLT.getJSCodeSegmentX().load();
            transformer.setSource(sSource);
            ser = XsltProcessor.newSerializer(sWriter);
            transformer.setDestination(ser);
            transformer.transform();
            String mungedCode = sWriter.toString();

            sReader = new StringReader(mungedCode);
            sSource = new StreamSource(sReader);
            transformer = compiledXSLT.getGlobalVarX().load();
            transformer.setSource(sSource);
            sWriter = new StringWriter();
            ser = XsltProcessor.newSerializer(sWriter);
            transformer.setDestination(ser);
            transformer.transform();
            if (mergeGlobals) {
                sSource = new StreamSource(new StringReader(sWriter.toString()));
                GlobalVarNameTable varTable = (GlobalVarNameTable) unmarshaller.unmarshal(sSource);
                GlobalVars.addGlobalVars(varTable);
            }

            sSource = new StreamSource(new StringReader(mungedCode));
            transformer = compiledXSLT.getPostMungeX().load();
            transformer.setSource(sSource);
            sWriter = new StringWriter();
            ser = XsltProcessor.newSerializer(sWriter);
            transformer.setDestination(ser);
            transformer.transform();
            return sWriter.toString();
        } catch (net.sf.saxon.s9api.SaxonApiException ex) {
            throw new DeploymentTerminationException("XSLT Error during test deployment", ex);
        } catch (java.io.IOException ex) {
            throw new DeploymentTerminationException("Unmarshalling Error during test deployment", ex);
        }
    }

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

    private void generateIAT(IAT test) throws DeploymentTerminationException {
        deploymentProgress.setStage(DeploymentStage.GENERATING_IAT, 0, 5);
        try {
            XsltTransformer transformer = compiledXSLT.getIATPageX().load();
            transformer.setSource(IATSource);
            StringWriter sWriter = new StringWriter();
            Serializer pageSerializer = XsltProcessor.newSerializer(sWriter);
            transformer.setDestination(pageSerializer);
            transformer.transform();
            String iatPage = "<!DOCTYPE html>\n" + sWriter.toString();
            String testName = test.getTestName();
            String compactedElemName = testName.replaceAll("[^A-Za-z0-9_\\-]", "");
            TestSegment ts = new TestSegment(test, compactedElemName, iatPage, -1, CF.getNumBeforeSurveys(), 0, true);
            deploymentProgress.incProgress();

            String iatCode = transSource(IATSource, compiledXSLT.getIATScriptX(), true);
            ts.setScript(iatCode);
            deploymentProgress.incProgress();

            String iatHeader = transSource(IATSource, compiledXSLT.getIATHeaderX(), true);
            ts.setHeaderScript(iatHeader);
            deploymentProgress.incProgress();
            iatRepositoryManager.addTestSegment(ts);
            digestTestSegment(IATSource, compiledXSLT.getIATDescriptorX());
            deploymentProgress.incProgress();
        } catch (net.sf.saxon.s9api.SaxonApiException ex) {
            throw new DeploymentTerminationException("XSLT Error while generating files for an IAT", ex);
        }
    }

    private void generateSurvey(IAT test, StreamSource surveySource, IATSurvey survey, int initialPos)
            throws DeploymentTerminationException {
        try {
            String surveyName = survey.getSurveyName();
            deploymentProgress.setStage(DeploymentStage.GENERATING_SURVEY, surveyName, 0, 5);
            XsltTransformer transformer = compiledXSLT.getSurveyPageX().load();
            transformer.setSource(surveySource);
            StringWriter sWriter = new StringWriter();
            Serializer pageSerializer = XsltProcessor.newSerializer(sWriter);
            transformer.setDestination(pageSerializer);
            transformer.transform();
            String surveyHTML = "<!DOCTYPE html>\n" + sWriter.toString();
            String compactedElemName = surveyName.replaceAll("[^A-Za-z0-9\\-_]", "");
            TestSegment testSegment = new TestSegment(test, compactedElemName, surveyHTML, survey.getAlternationSet(),
                    initialPos, 0, false);
            deploymentProgress.incProgress();

            GlobalVars.clear();
            // GlobalVars.addGlobalVars(AESGlobals);
            String surveyCode = transSource(surveySource, compiledXSLT.getSurveyScriptX(), true);
            testSegment.setScript(surveyCode);
            // writeBytesToUri(serverConfiguration.getIatFileUri(clientID, testName,
            // compactedElemName + ".js"),
            // surveyCode.getBytes(StandardCharsets.UTF_8));
            deploymentProgress.incProgress();
            String surveyHeader = transSource(surveySource, compiledXSLT.getSurveyHeaderX(), true);
            // GlobalVars.replaceGlobals(surveyCode);
            // writeBytesToUri(serverConfiguration.getIatFileUri(clientID, testName,
            // compactedElemName + "-header.js"),
            // surveyHeader.getBytes(StandardCharsets.UTF_8));
            testSegment.setHeaderScript(surveyHeader);
            deploymentProgress.incProgress();
            digestTestSegment(surveySource, compiledXSLT.getSurveyDescriptorX());
            deploymentProgress.incProgress();
            iatRepositoryManager.addTestSegment(testSegment);
        } catch (net.sf.saxon.s9api.SaxonApiException ex) {
            throw new DeploymentTerminationException("Error generating survey java script during test deployment", ex);
        }
    }

    private void processUniqueResponses(IAT test) throws DeploymentTerminationException {
        UniqueResponse unique = null;
        deploymentProgress.setStage(DeploymentStage.PROCESSING_UNIQUE_SURVEY_RESPONSES);
        var uniqueRespString = new String(iatRepositoryManager.getTestResource(test, "UniqueResponse").getResource(),
                StandardCharsets.UTF_16);
        var sSource = new StreamSource(new StringReader(uniqueRespString));
        try {
            unique = (UniqueResponse) unmarshaller.unmarshal(sSource);
        } catch (java.io.IOException ex) {
            throw new DeploymentTerminationException("Error unmarshalling unique response file during test deployment",
                    ex);
        }
        UniqueResponseItem uri = new UniqueResponseItem();
        uri.setAdditive(unique.isAdditive());
        uri.setItemNum(unique.getItemNum());
        uri.setSurveyName(unique.getSurveyName());
        uri.setTest(test);
        iatRepositoryManager.addUniqueResponseItem(uri);
        if (!unique.isAdditive())
            iatRepositoryManager.addUniqueResponses(uri, unique.getValue());
    }

    protected void doDeploy(IAT test) throws DeploymentTerminationException {
        this.deploymentProgress.setStage(DeploymentStage.INITIALIZING_DEPLOYMENT);
        // codeFileEncryptor = iatServerBeanFactory.codeFileEncryptor();
        try {
            test.setRedirectOnComplete(CF.getRedirectOnComplete());
            test.setAlternated(true);
            this.deploymentProgress.setStage(DeploymentStage.GENERATING_AES);
            generateIAT(test);
            if (SurveySources != null) {
                for (int ctr = 0; ctr < SurveySources.length; ctr++) {
                    IATSurvey s = CF.getIATSurvey().get(ctr);
                    generateSurvey(test, SurveySources[ctr], s, (ctr < CF.getNumBeforeSurveys()) ? ctr : ctr + 1);
                }
                test.setNumElements(SurveySources.length + 1);
            } else {
                test.setNumElements(1);
            }
            if (CF.isHasUniqueResponse()) {
                processUniqueResponses(test);
            }
            deploymentProgress.setStage(DeploymentStage.FINALIZING_DEPLOYMENT);
            test.setDeploymentDescriptor(DeploymentDescriptor.digest());
            iatRepositoryManager.updateIAT(test);
        } catch (NullPointerException | org.springframework.orm.jpa.JpaSystemException ex) {
            throw new DeploymentTerminationException("Null pointer", ex);
        }
    }
}
