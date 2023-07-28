//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.07.28 at 02:14:21 PM EDT 
//


package net.iatsoftware.iat.generated;

import jakarta.xml.bind.annotation.XmlRegistry;
import net.iatsoftware.iat.configfile.BeginIATBlock;
import net.iatsoftware.iat.configfile.BeginInstructionBlock;
import net.iatsoftware.iat.configfile.ConfigFile;
import net.iatsoftware.iat.configfile.DisplayItem;
import net.iatsoftware.iat.configfile.Globals;
import net.iatsoftware.iat.configfile.IATLayout;
import net.iatsoftware.iat.configfile.IATSurveySummary;
import net.iatsoftware.iat.configfile.KeyedInstructionScreen;
import net.iatsoftware.iat.configfile.MockItemInstructionScreen;
import net.iatsoftware.iat.configfile.Survey;
import net.iatsoftware.iat.configfile.UniqueResponse;
import net.iatsoftware.iat.deployment.CodeFile;
import net.iatsoftware.iat.messaging.ActivationRequest;
import net.iatsoftware.iat.messaging.ActivationResponse;
import net.iatsoftware.iat.messaging.AjaxRequest;
import net.iatsoftware.iat.messaging.ClientActivityEvent;
import net.iatsoftware.iat.messaging.ClientManagementEnvelope;
import net.iatsoftware.iat.messaging.DecimalElement;
import net.iatsoftware.iat.messaging.EncryptedDESCipher;
import net.iatsoftware.iat.messaging.ErrorReportResponse;
import net.iatsoftware.iat.messaging.ExceptionMessage;
import net.iatsoftware.iat.messaging.Handshake;
import net.iatsoftware.iat.messaging.InnerException;
import net.iatsoftware.iat.messaging.IntElement;
import net.iatsoftware.iat.messaging.LongElement;
import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.messaging.ResultRequest;
import net.iatsoftware.iat.messaging.StringElement;
import net.iatsoftware.iat.messaging.TokenDefinition;
import net.iatsoftware.iat.messaging.UpdateNotification;
import net.iatsoftware.iat.messaging.UsageReport;
import net.iatsoftware.iat.resultdata.IATResultSetElementV2;
import net.iatsoftware.iat.resultdata.ResultSetDescriptor;
import net.iatsoftware.iat.resultdata.ResultSetElementList;
import net.iatsoftware.iat.resultdata.ResultSetEntry;
import net.iatsoftware.iat.resultdata.ResultTOCEntry;
import net.iatsoftware.iat.resultdata.SurveyResponseSet;
import net.iatsoftware.iat.resultdata.TestResults;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.iatsoftware.iat.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.iatsoftware.iat.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GClientActivityEvent }
     * 
     */
    public GClientActivityEvent createGClientActivityEvent() {
        return new ClientActivityEvent();
    }

    /**
     * Create an instance of {@link GConfigFile }
     * 
     */
    public GConfigFile createGConfigFile() {
        return new ConfigFile();
    }

    /**
     * Create an instance of {@link GIATSurvey }
     * 
     */
    public GIATSurvey createGIATSurvey() {
        return new Survey();
    }

    /**
     * Create an instance of {@link GWeightedMultiple }
     * 
     */
    public GWeightedMultiple createGWeightedMultiple() {
        return new GWeightedMultiple();
    }

    /**
     * Create an instance of {@link GWeightedMultiple.WeightedChoices }
     * 
     */
    public GWeightedMultiple.WeightedChoices createGWeightedMultipleWeightedChoices() {
        return new GWeightedMultiple.WeightedChoices();
    }

    /**
     * Create an instance of {@link GMultipleResponse }
     * 
     */
    public GMultipleResponse createGMultipleResponse() {
        return new GMultipleResponse();
    }

    /**
     * Create an instance of {@link GMultipleResponse.Choices }
     * 
     */
    public GMultipleResponse.Choices createGMultipleResponseChoices() {
        return new GMultipleResponse.Choices();
    }

    /**
     * Create an instance of {@link GMultiBoolean }
     * 
     */
    public GMultiBoolean createGMultiBoolean() {
        return new GMultiBoolean();
    }

    /**
     * Create an instance of {@link GMultiBoolean.Labels }
     * 
     */
    public GMultiBoolean.Labels createGMultiBooleanLabels() {
        return new GMultiBoolean.Labels();
    }

    /**
     * Create an instance of {@link GLikert }
     * 
     */
    public GLikert createGLikert() {
        return new GLikert();
    }

    /**
     * Create an instance of {@link GLikert.ChoiceDescriptions }
     * 
     */
    public GLikert.ChoiceDescriptions createGLikertChoiceDescriptions() {
        return new GLikert.ChoiceDescriptions();
    }

    /**
     * Create an instance of {@link GSurveyDate }
     * 
     */
    public GSurveyDate createGSurveyDate() {
        return new GSurveyDate();
    }

    /**
     * Create an instance of {@link GSurveyWeightedMultiple }
     * 
     */
    public GSurveyWeightedMultiple createGSurveyWeightedMultiple() {
        return new GSurveyWeightedMultiple();
    }

    /**
     * Create an instance of {@link GSurveyWeightedMultiple.WeightedChoices }
     * 
     */
    public GSurveyWeightedMultiple.WeightedChoices createGSurveyWeightedMultipleWeightedChoices() {
        return new GSurveyWeightedMultiple.WeightedChoices();
    }

    /**
     * Create an instance of {@link GSurveyLikertResponse }
     * 
     */
    public GSurveyLikertResponse createGSurveyLikertResponse() {
        return new GSurveyLikertResponse();
    }

    /**
     * Create an instance of {@link GSurveyMultipleResponse }
     * 
     */
    public GSurveyMultipleResponse createGSurveyMultipleResponse() {
        return new GSurveyMultipleResponse();
    }

    /**
     * Create an instance of {@link GSurveyMultiBoolean }
     * 
     */
    public GSurveyMultiBoolean createGSurveyMultiBoolean() {
        return new GSurveyMultiBoolean();
    }

    /**
     * Create an instance of {@link GIATSurveySummary }
     * 
     */
    public GIATSurveySummary createGIATSurveySummary() {
        return new IATSurveySummary();
    }

    /**
     * Create an instance of {@link GUpdateNotification }
     * 
     */
    public GUpdateNotification createGUpdateNotification() {
        return new UpdateNotification();
    }

    /**
     * Create an instance of {@link GClientManagementEnvelope }
     * 
     */
    public GClientManagementEnvelope createGClientManagementEnvelope() {
        return new ClientManagementEnvelope();
    }

    /**
     * Create an instance of {@link GUsageReport }
     * 
     */
    public GUsageReport createGUsageReport() {
        return new UsageReport();
    }

    /**
     * Create an instance of {@link GCodeFile }
     * 
     */
    public GCodeFile createGCodeFile() {
        return new CodeFile();
    }

    /**
     * Create an instance of {@link GIATResultSetElementList }
     * 
     */
    public GIATResultSetElementList createGIATResultSetElementList() {
        return new ResultSetElementList();
    }

    /**
     * Create an instance of {@link GIATResultSetElementV2 }
     * 
     */
    public GIATResultSetElementV2 createGIATResultSetElementV2() {
        return new IATResultSetElementV2();
    }

    /**
     * Create an instance of {@link GResultRequest }
     * 
     */
    public GResultRequest createGResultRequest() {
        return new ResultRequest();
    }

    /**
     * Create an instance of {@link GResultTOCEntry }
     * 
     */
    public GResultTOCEntry createGResultTOCEntry() {
        return new ResultTOCEntry();
    }

    /**
     * Create an instance of {@link GResultSetDescriptor }
     * 
     */
    public GResultSetDescriptor createGResultSetDescriptor() {
        return new ResultSetDescriptor();
    }

    /**
     * Create an instance of {@link GErrorReportResponse }
     * 
     */
    public GErrorReportResponse createGErrorReportResponse() {
        return new ErrorReportResponse();
    }

    /**
     * Create an instance of {@link GSurveyResponseSet }
     * 
     */
    public GSurveyResponseSet createGSurveyResponseSet() {
        return new SurveyResponseSet();
    }

    /**
     * Create an instance of {@link GTestResults }
     * 
     */
    public GTestResults createGTestResults() {
        return new TestResults();
    }

    /**
     * Create an instance of {@link GResultSetEntry }
     * 
     */
    public GResultSetEntry createGResultSetEntry() {
        return new ResultSetEntry();
    }

    /**
     * Create an instance of {@link GIATLayout }
     * 
     */
    public GIATLayout createGIATLayout() {
        return new IATLayout();
    }

    /**
     * Create an instance of {@link GSurveyBoolean }
     * 
     */
    public GSurveyBoolean createGSurveyBoolean() {
        return new GSurveyBoolean();
    }

    /**
     * Create an instance of {@link GSurveyRegularExpression }
     * 
     */
    public GSurveyRegularExpression createGSurveyRegularExpression() {
        return new GSurveyRegularExpression();
    }

    /**
     * Create an instance of {@link GSurveyFixedDigit }
     * 
     */
    public GSurveyFixedDigit createGSurveyFixedDigit() {
        return new GSurveyFixedDigit();
    }

    /**
     * Create an instance of {@link GSurveyBoundedNumber }
     * 
     */
    public GSurveyBoundedNumber createGSurveyBoundedNumber() {
        return new GSurveyBoundedNumber();
    }

    /**
     * Create an instance of {@link GSurveyBoundedLength }
     * 
     */
    public GSurveyBoundedLength createGSurveyBoundedLength() {
        return new GSurveyBoundedLength();
    }

    /**
     * Create an instance of {@link GSurveyResponse }
     * 
     */
    public GSurveyResponse createGSurveyResponse() {
        return new GSurveyResponse();
    }

    /**
     * Create an instance of {@link GSurveyFormat }
     * 
     */
    public GSurveyFormat createGSurveyFormat() {
        return new GSurveyFormat();
    }

    /**
     * Create an instance of {@link GDisplayItem }
     * 
     */
    public GDisplayItem createGDisplayItem() {
        return new DisplayItem();
    }

    /**
     * Create an instance of {@link GIATItem }
     * 
     */
    public GIATItem createGIATItem() {
        return new GIATItem();
    }

    /**
     * Create an instance of {@link GKeyedInstructionScreen }
     * 
     */
    public GKeyedInstructionScreen createGKeyedInstructionScreen() {
        return new KeyedInstructionScreen();
    }

    /**
     * Create an instance of {@link GMockItemInstructionScreen }
     * 
     */
    public GMockItemInstructionScreen createGMockItemInstructionScreen() {
        return new MockItemInstructionScreen();
    }

    /**
     * Create an instance of {@link GFormat }
     * 
     */
    public GFormat createGFormat() {
        return new GFormat();
    }

    /**
     * Create an instance of {@link GYearMonthDay }
     * 
     */
    public GYearMonthDay createGYearMonthDay() {
        return new GYearMonthDay();
    }

    /**
     * Create an instance of {@link GDateResponse }
     * 
     */
    public GDateResponse createGDateResponse() {
        return new GDateResponse();
    }

    /**
     * Create an instance of {@link GBoolean }
     * 
     */
    public GBoolean createGBoolean() {
        return new GBoolean();
    }

    /**
     * Create an instance of {@link GInstruction }
     * 
     */
    public GInstruction createGInstruction() {
        return new GInstruction();
    }

    /**
     * Create an instance of {@link GBoundedLength }
     * 
     */
    public GBoundedLength createGBoundedLength() {
        return new GBoundedLength();
    }

    /**
     * Create an instance of {@link GBoundedNumber }
     * 
     */
    public GBoundedNumber createGBoundedNumber() {
        return new GBoundedNumber();
    }

    /**
     * Create an instance of {@link GFixedDigit }
     * 
     */
    public GFixedDigit createGFixedDigit() {
        return new GFixedDigit();
    }

    /**
     * Create an instance of {@link GRegularExpression }
     * 
     */
    public GRegularExpression createGRegularExpression() {
        return new GRegularExpression();
    }

    /**
     * Create an instance of {@link GBeginIATBlock }
     * 
     */
    public GBeginIATBlock createGBeginIATBlock() {
        return new BeginIATBlock();
    }

    /**
     * Create an instance of {@link GBeginInstructionBlock }
     * 
     */
    public GBeginInstructionBlock createGBeginInstructionBlock() {
        return new BeginInstructionBlock();
    }

    /**
     * Create an instance of {@link GEndIATBlock }
     * 
     */
    public GEndIATBlock createGEndIATBlock() {
        return new GEndIATBlock();
    }

    /**
     * Create an instance of {@link GTextInstructionScreen }
     * 
     */
    public GTextInstructionScreen createGTextInstructionScreen() {
        return new GTextInstructionScreen();
    }

    /**
     * Create an instance of {@link GGlobals }
     * 
     */
    public GGlobals createGGlobals() {
        return new Globals();
    }

    /**
     * Create an instance of {@link GGlobalVar }
     * 
     */
    public GGlobalVar createGGlobalVar() {
        return new GGlobalVar();
    }

    /**
     * Create an instance of {@link GUniqueResponse }
     * 
     */
    public GUniqueResponse createGUniqueResponse() {
        return new UniqueResponse();
    }

    /**
     * Create an instance of {@link GActivationResponse }
     * 
     */
    public GActivationResponse createGActivationResponse() {
        return new ActivationResponse();
    }

    /**
     * Create an instance of {@link GActivationRequest }
     * 
     */
    public GActivationRequest createGActivationRequest() {
        return new ActivationRequest();
    }

    /**
     * Create an instance of {@link GAjaxRequest }
     * 
     */
    public GAjaxRequest createGAjaxRequest() {
        return new AjaxRequest();
    }

    /**
     * Create an instance of {@link GManifest }
     * 
     */
    public GManifest createGManifest() {
        return new Manifest();
    }

    /**
     * Create an instance of {@link GEncryptedDESCipher }
     * 
     */
    public GEncryptedDESCipher createGEncryptedDESCipher() {
        return new EncryptedDESCipher();
    }

    /**
     * Create an instance of {@link GHandshake }
     * 
     */
    public GHandshake createGHandshake() {
        return new Handshake();
    }

    /**
     * Create an instance of {@link GTokenDefinition }
     * 
     */
    public GTokenDefinition createGTokenDefinition() {
        return new TokenDefinition();
    }

    /**
     * Create an instance of {@link GIntElement }
     * 
     */
    public GIntElement createGIntElement() {
        return new IntElement();
    }

    /**
     * Create an instance of {@link GLongElement }
     * 
     */
    public GLongElement createGLongElement() {
        return new LongElement();
    }

    /**
     * Create an instance of {@link GDecimalElement }
     * 
     */
    public GDecimalElement createGDecimalElement() {
        return new DecimalElement();
    }

    /**
     * Create an instance of {@link GStringElement }
     * 
     */
    public GStringElement createGStringElement() {
        return new StringElement();
    }

    /**
     * Create an instance of {@link GExceptionMessage }
     * 
     */
    public GExceptionMessage createGExceptionMessage() {
        return new ExceptionMessage();
    }

    /**
     * Create an instance of {@link GInnerException }
     * 
     */
    public GInnerException createGInnerException() {
        return new InnerException();
    }

    /**
     * Create an instance of {@link GClientActivityEvent.Parameter }
     * 
     */
    public GClientActivityEvent.Parameter createGClientActivityEventParameter() {
        return new GClientActivityEvent.Parameter();
    }

    /**
     * Create an instance of {@link net.iatsoftware.iat.generated.GClientException.HistoryEntry }
     * 
     */
    public net.iatsoftware.iat.generated.GClientException.HistoryEntry createGClientExceptionHistoryEntry() {
        return new net.iatsoftware.iat.generated.GClientException.HistoryEntry();
    }

    /**
     * Create an instance of {@link net.iatsoftware.iat.generated.GClientException.EventLog }
     * 
     */
    public net.iatsoftware.iat.generated.GClientException.EventLog createGClientExceptionEventLog() {
        return new net.iatsoftware.iat.generated.GClientException.EventLog();
    }

    /**
     * Create an instance of {@link net.iatsoftware.iat.generated.GAjaxResponse.ProcessedCode }
     * 
     */
    public net.iatsoftware.iat.generated.GAjaxResponse.ProcessedCode createGAjaxResponseProcessedCode() {
        return new net.iatsoftware.iat.generated.GAjaxResponse.ProcessedCode();
    }

    /**
     * Create an instance of {@link GConfigFile.EventList }
     * 
     */
    public GConfigFile.EventList createGConfigFileEventList() {
        return new GConfigFile.EventList();
    }

    /**
     * Create an instance of {@link GConfigFile.DisplayItemList }
     * 
     */
    public GConfigFile.DisplayItemList createGConfigFileDisplayItemList() {
        return new GConfigFile.DisplayItemList();
    }

    /**
     * Create an instance of {@link GIATSurvey.SurveyItem }
     * 
     */
    public GIATSurvey.SurveyItem createGIATSurveySurveyItem() {
        return new GIATSurvey.SurveyItem();
    }

    /**
     * Create an instance of {@link GIATSurvey.Caption }
     * 
     */
    public GIATSurvey.Caption createGIATSurveyCaption() {
        return new GIATSurvey.Caption();
    }

    /**
     * Create an instance of {@link GIATSurvey.SurveyImage }
     * 
     */
    public GIATSurvey.SurveyImage createGIATSurveySurveyImage() {
        return new GIATSurvey.SurveyImage();
    }

    /**
     * Create an instance of {@link GWeightedMultiple.WeightedChoices.WeightedChoice }
     * 
     */
    public GWeightedMultiple.WeightedChoices.WeightedChoice createGWeightedMultipleWeightedChoicesWeightedChoice() {
        return new GWeightedMultiple.WeightedChoices.WeightedChoice();
    }

    /**
     * Create an instance of {@link GMultipleResponse.Choices.Choice }
     * 
     */
    public GMultipleResponse.Choices.Choice createGMultipleResponseChoicesChoice() {
        return new GMultipleResponse.Choices.Choice();
    }

    /**
     * Create an instance of {@link GMultiBoolean.Labels.Label }
     * 
     */
    public GMultiBoolean.Labels.Label createGMultiBooleanLabelsLabel() {
        return new GMultiBoolean.Labels.Label();
    }

    /**
     * Create an instance of {@link GLikert.ChoiceDescriptions.Choice }
     * 
     */
    public GLikert.ChoiceDescriptions.Choice createGLikertChoiceDescriptionsChoice() {
        return new GLikert.ChoiceDescriptions.Choice();
    }

    /**
     * Create an instance of {@link net.iatsoftware.iat.generated.GSelectionSpecifier.KeySpecifiers }
     * 
     */
    public net.iatsoftware.iat.generated.GSelectionSpecifier.KeySpecifiers createGSelectionSpecifierKeySpecifiers() {
        return new net.iatsoftware.iat.generated.GSelectionSpecifier.KeySpecifiers();
    }

    /**
     * Create an instance of {@link GSurveyDate.StartDate }
     * 
     */
    public GSurveyDate.StartDate createGSurveyDateStartDate() {
        return new GSurveyDate.StartDate();
    }

    /**
     * Create an instance of {@link GSurveyDate.EndDate }
     * 
     */
    public GSurveyDate.EndDate createGSurveyDateEndDate() {
        return new GSurveyDate.EndDate();
    }

    /**
     * Create an instance of {@link GSurveyWeightedMultiple.WeightedChoices.WeightedChoice }
     * 
     */
    public GSurveyWeightedMultiple.WeightedChoices.WeightedChoice createGSurveyWeightedMultipleWeightedChoicesWeightedChoice() {
        return new GSurveyWeightedMultiple.WeightedChoices.WeightedChoice();
    }

    /**
     * Create an instance of {@link GSurveyLikertResponse.ChoiceDescriptions }
     * 
     */
    public GSurveyLikertResponse.ChoiceDescriptions createGSurveyLikertResponseChoiceDescriptions() {
        return new GSurveyLikertResponse.ChoiceDescriptions();
    }

    /**
     * Create an instance of {@link GSurveyMultipleResponse.Choices }
     * 
     */
    public GSurveyMultipleResponse.Choices createGSurveyMultipleResponseChoices() {
        return new GSurveyMultipleResponse.Choices();
    }

    /**
     * Create an instance of {@link GSurveyMultiBoolean.Labels }
     * 
     */
    public GSurveyMultiBoolean.Labels createGSurveyMultiBooleanLabels() {
        return new GSurveyMultiBoolean.Labels();
    }

    /**
     * Create an instance of {@link GIATSurveySummary.ResponseTypes }
     * 
     */
    public GIATSurveySummary.ResponseTypes createGIATSurveySummaryResponseTypes() {
        return new GIATSurveySummary.ResponseTypes();
    }

    /**
     * Create an instance of {@link net.iatsoftware.iat.generated.GGlobalVarNameTable.VarTableEntry }
     * 
     */
    public net.iatsoftware.iat.generated.GGlobalVarNameTable.VarTableEntry createGGlobalVarNameTableVarTableEntry() {
        return new net.iatsoftware.iat.generated.GGlobalVarNameTable.VarTableEntry();
    }

    /**
     * Create an instance of {@link net.iatsoftware.iat.generated.GPurchaseConfirmation.Resource }
     * 
     */
    public net.iatsoftware.iat.generated.GPurchaseConfirmation.Resource createGPurchaseConfirmationResource() {
        return new net.iatsoftware.iat.generated.GPurchaseConfirmation.Resource();
    }

    /**
     * Create an instance of {@link net.iatsoftware.iat.generated.GPurchaseConfirmation.Card }
     * 
     */
    public net.iatsoftware.iat.generated.GPurchaseConfirmation.Card createGPurchaseConfirmationCard() {
        return new net.iatsoftware.iat.generated.GPurchaseConfirmation.Card();
    }

    /**
     * Create an instance of {@link GUpdateNotification.Notification }
     * 
     */
    public GUpdateNotification.Notification createGUpdateNotificationNotification() {
        return new GUpdateNotification.Notification();
    }

    /**
     * Create an instance of {@link net.iatsoftware.iat.generated.GAlterResources.Activations }
     * 
     */
    public net.iatsoftware.iat.generated.GAlterResources.Activations createGAlterResourcesActivations() {
        return new net.iatsoftware.iat.generated.GAlterResources.Activations();
    }

    /**
     * Create an instance of {@link net.iatsoftware.iat.generated.GAlterResources.Administrations }
     * 
     */
    public net.iatsoftware.iat.generated.GAlterResources.Administrations createGAlterResourcesAdministrations() {
        return new net.iatsoftware.iat.generated.GAlterResources.Administrations();
    }

    /**
     * Create an instance of {@link net.iatsoftware.iat.generated.GAlterResources.DiskAlottmentMB }
     * 
     */
    public net.iatsoftware.iat.generated.GAlterResources.DiskAlottmentMB createGAlterResourcesDiskAlottmentMB() {
        return new net.iatsoftware.iat.generated.GAlterResources.DiskAlottmentMB();
    }

    /**
     * Create an instance of {@link net.iatsoftware.iat.generated.GAlterResources.NumIATs }
     * 
     */
    public net.iatsoftware.iat.generated.GAlterResources.NumIATs createGAlterResourcesNumIATs() {
        return new net.iatsoftware.iat.generated.GAlterResources.NumIATs();
    }

}
