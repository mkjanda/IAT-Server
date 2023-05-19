//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.15 at 04:11:12 PM EDT 
//


package net.iatsoftware.iat.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TransactionType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Unset"/&gt;
 *     &lt;enumeration value="RequestResults"/&gt;
 *     &lt;enumeration value="RetrieveItemSlides"/&gt;
 *     &lt;enumeration value="RequestTransmission"/&gt;
 *     &lt;enumeration value="AbortTransaction"/&gt;
 *     &lt;enumeration value="RetrieveResults"/&gt;
 *     &lt;enumeration value="DoIATDeploy"/&gt;
 *     &lt;enumeration value="RequestActivation"/&gt;
 *     &lt;enumeration value="VerifyIATDeployment"/&gt;
 *     &lt;enumeration value="DeleteIAT"/&gt;
 *     &lt;enumeration value="DeleteIATData"/&gt;
 *     &lt;enumeration value="TransactionSuccess"/&gt;
 *     &lt;enumeration value="TransactionFail"/&gt;
 *     &lt;enumeration value="InsufficientDiskSpace"/&gt;
 *     &lt;enumeration value="InsufficientIATS"/&gt;
 *     &lt;enumeration value="IATExists"/&gt;
 *     &lt;enumeration value="RequestPacket"/&gt;
 *     &lt;enumeration value="RequestEncryptionKey"/&gt;
 *     &lt;enumeration value="RequestRetrievalReady"/&gt;
 *     &lt;enumeration value="EstablishEncryption"/&gt;
 *     &lt;enumeration value="RequestAdminPasswordVerification"/&gt;
 *     &lt;enumeration value="RequestDataPasswordVerification"/&gt;
 *     &lt;enumeration value="VerifyPassword"/&gt;
 *     &lt;enumeration value="NoSuchIAT"/&gt;
 *     &lt;enumeration value="ClientFrozen"/&gt;
 *     &lt;enumeration value="ClientDeleted"/&gt;
 *     &lt;enumeration value="RequestResultDescriptor"/&gt;
 *     &lt;enumeration value="RequestIATList"/&gt;
 *     &lt;enumeration value="RequestItemSlideManifest"/&gt;
 *     &lt;enumeration value="ActivationConfirmation"/&gt;
 *     &lt;enumeration value="RequestEMailVerification"/&gt;
 *     &lt;enumeration value="RequestNewVerificationEMail"/&gt;
 *     &lt;enumeration value="RequestRSAKey"/&gt;
 *     &lt;enumeration value="RetrieveSurveys"/&gt;
 *     &lt;enumeration value="RequestRemainingResources"/&gt;
 *     &lt;enumeration value="QueryDeploymentProgress"/&gt;
 *     &lt;enumeration value="IATBeingDeployed"/&gt;
 *     &lt;enumeration value="IATDeployHaltFailed"/&gt;
 *     &lt;enumeration value="NoSuchClient"/&gt;
 *     &lt;enumeration value="EMailAlreadyVerified"/&gt;
 *     &lt;enumeration value="RequestServerReport"/&gt;
 *     &lt;enumeration value="RetrieveIATItemSize"/&gt;
 *     &lt;enumeration value="RequestLegacyIATData"/&gt;
 *     &lt;enumeration value="LegacyData"/&gt;
 *     &lt;enumeration value="MismatchedDataFormat"/&gt;
 *     &lt;enumeration value="RequestConnection"/&gt;
 *     &lt;enumeration value="RequestIATUpload"/&gt;
 *     &lt;enumeration value="PasswordValid"/&gt;
 *     &lt;enumeration value="PasswordInvalid"/&gt;
 *     &lt;enumeration value="RequestItemSlides"/&gt;
 *     &lt;enumeration value="CannotRestoreBackup"/&gt;
 *     &lt;enumeration value="BackupRestored"/&gt;
 *     &lt;enumeration value="RequestIATRedeploy"/&gt;
 *     &lt;enumeration value="RequestReconnection"/&gt;
 *     &lt;enumeration value="RetrieveProductRequests"/&gt;
 *     &lt;enumeration value="Success"/&gt;
 *     &lt;enumeration value="Fail"/&gt;
 *     &lt;enumeration value="QueryRemainingIATS"/&gt;
 *     &lt;enumeration value="RemainingIATS"/&gt;
 *     &lt;enumeration value="RequestDataUpload"/&gt;
 *     &lt;enumeration value="TestBeingDeployed"/&gt;
 *     &lt;enumeration value="HaltTestDeployment"/&gt;
 *     &lt;enumeration value="DeploymentHalted"/&gt;
 *     &lt;enumeration value="ItemSlideDownloadReady"/&gt;
 *     &lt;enumeration value="DeploymentDescriptorMismatch"/&gt;
 *     &lt;enumeration value="CannotCreateBackup"/&gt;
 *     &lt;enumeration value="EncryptionKeysReceived"/&gt;
 *     &lt;enumeration value="DeploymentFileManifestReceived"/&gt;
 *     &lt;enumeration value="TokenDefinitionReceived"/&gt;
 *     &lt;enumeration value="ItemSlideManifestReceived"/&gt;
 *     &lt;enumeration value="AbortDeployment"/&gt;
 *     &lt;enumeration value="QueryPublicityIAT"/&gt;
 *     &lt;enumeration value="PublicityIAT"/&gt;
 *     &lt;enumeration value="UpdateUserInformation"/&gt;
 *     &lt;enumeration value="ServerError"/&gt;
 *     &lt;enumeration value="EmailVerificationMismatch"/&gt;
 *     &lt;enumeration value="TestFilesMissing"/&gt;
 *     &lt;enumeration value="DeploymentAbortFailed"/&gt;
 *     &lt;enumeration value="ResultsReady"/&gt;
 *     &lt;enumeration value="NoActivationsRemain"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TransactionType")
@XmlEnum
public enum TransactionType {

    @XmlEnumValue("Unset")
    UNSET("Unset"),
    @XmlEnumValue("RequestResults")
    REQUEST_RESULTS("RequestResults"),
    @XmlEnumValue("RetrieveItemSlides")
    RETRIEVE_ITEM_SLIDES("RetrieveItemSlides"),
    @XmlEnumValue("RequestTransmission")
    REQUEST_TRANSMISSION("RequestTransmission"),
    @XmlEnumValue("AbortTransaction")
    ABORT_TRANSACTION("AbortTransaction"),
    @XmlEnumValue("RetrieveResults")
    RETRIEVE_RESULTS("RetrieveResults"),
    @XmlEnumValue("DoIATDeploy")
    DO_IAT_DEPLOY("DoIATDeploy"),
    @XmlEnumValue("RequestActivation")
    REQUEST_ACTIVATION("RequestActivation"),
    @XmlEnumValue("VerifyIATDeployment")
    VERIFY_IAT_DEPLOYMENT("VerifyIATDeployment"),
    @XmlEnumValue("DeleteIAT")
    DELETE_IAT("DeleteIAT"),
    @XmlEnumValue("DeleteIATData")
    DELETE_IAT_DATA("DeleteIATData"),
    @XmlEnumValue("TransactionSuccess")
    TRANSACTION_SUCCESS("TransactionSuccess"),
    @XmlEnumValue("TransactionFail")
    TRANSACTION_FAIL("TransactionFail"),
    @XmlEnumValue("InsufficientDiskSpace")
    INSUFFICIENT_DISK_SPACE("InsufficientDiskSpace"),
    @XmlEnumValue("InsufficientIATS")
    INSUFFICIENT_IATS("InsufficientIATS"),
    @XmlEnumValue("IATExists")
    IAT_EXISTS("IATExists"),
    @XmlEnumValue("RequestPacket")
    REQUEST_PACKET("RequestPacket"),
    @XmlEnumValue("RequestEncryptionKey")
    REQUEST_ENCRYPTION_KEY("RequestEncryptionKey"),
    @XmlEnumValue("RequestRetrievalReady")
    REQUEST_RETRIEVAL_READY("RequestRetrievalReady"),
    @XmlEnumValue("EstablishEncryption")
    ESTABLISH_ENCRYPTION("EstablishEncryption"),
    @XmlEnumValue("RequestAdminPasswordVerification")
    REQUEST_ADMIN_PASSWORD_VERIFICATION("RequestAdminPasswordVerification"),
    @XmlEnumValue("RequestDataPasswordVerification")
    REQUEST_DATA_PASSWORD_VERIFICATION("RequestDataPasswordVerification"),
    @XmlEnumValue("VerifyPassword")
    VERIFY_PASSWORD("VerifyPassword"),
    @XmlEnumValue("NoSuchIAT")
    NO_SUCH_IAT("NoSuchIAT"),
    @XmlEnumValue("ClientFrozen")
    CLIENT_FROZEN("ClientFrozen"),
    @XmlEnumValue("ClientDeleted")
    CLIENT_DELETED("ClientDeleted"),
    @XmlEnumValue("RequestResultDescriptor")
    REQUEST_RESULT_DESCRIPTOR("RequestResultDescriptor"),
    @XmlEnumValue("RequestIATList")
    REQUEST_IAT_LIST("RequestIATList"),
    @XmlEnumValue("RequestItemSlideManifest")
    REQUEST_ITEM_SLIDE_MANIFEST("RequestItemSlideManifest"),
    @XmlEnumValue("ActivationConfirmation")
    ACTIVATION_CONFIRMATION("ActivationConfirmation"),
    @XmlEnumValue("RequestEMailVerification")
    REQUEST_E_MAIL_VERIFICATION("RequestEMailVerification"),
    @XmlEnumValue("RequestNewVerificationEMail")
    REQUEST_NEW_VERIFICATION_E_MAIL("RequestNewVerificationEMail"),
    @XmlEnumValue("RequestRSAKey")
    REQUEST_RSA_KEY("RequestRSAKey"),
    @XmlEnumValue("RetrieveSurveys")
    RETRIEVE_SURVEYS("RetrieveSurveys"),
    @XmlEnumValue("RequestRemainingResources")
    REQUEST_REMAINING_RESOURCES("RequestRemainingResources"),
    @XmlEnumValue("QueryDeploymentProgress")
    QUERY_DEPLOYMENT_PROGRESS("QueryDeploymentProgress"),
    @XmlEnumValue("IATBeingDeployed")
    IAT_BEING_DEPLOYED("IATBeingDeployed"),
    @XmlEnumValue("IATDeployHaltFailed")
    IAT_DEPLOY_HALT_FAILED("IATDeployHaltFailed"),
    @XmlEnumValue("NoSuchClient")
    NO_SUCH_CLIENT("NoSuchClient"),
    @XmlEnumValue("EMailAlreadyVerified")
    E_MAIL_ALREADY_VERIFIED("EMailAlreadyVerified"),
    @XmlEnumValue("RequestServerReport")
    REQUEST_SERVER_REPORT("RequestServerReport"),
    @XmlEnumValue("RetrieveIATItemSize")
    RETRIEVE_IAT_ITEM_SIZE("RetrieveIATItemSize"),
    @XmlEnumValue("RequestLegacyIATData")
    REQUEST_LEGACY_IAT_DATA("RequestLegacyIATData"),
    @XmlEnumValue("LegacyData")
    LEGACY_DATA("LegacyData"),
    @XmlEnumValue("MismatchedDataFormat")
    MISMATCHED_DATA_FORMAT("MismatchedDataFormat"),
    @XmlEnumValue("RequestConnection")
    REQUEST_CONNECTION("RequestConnection"),
    @XmlEnumValue("RequestIATUpload")
    REQUEST_IAT_UPLOAD("RequestIATUpload"),
    @XmlEnumValue("PasswordValid")
    PASSWORD_VALID("PasswordValid"),
    @XmlEnumValue("PasswordInvalid")
    PASSWORD_INVALID("PasswordInvalid"),
    @XmlEnumValue("RequestItemSlides")
    REQUEST_ITEM_SLIDES("RequestItemSlides"),
    @XmlEnumValue("CannotRestoreBackup")
    CANNOT_RESTORE_BACKUP("CannotRestoreBackup"),
    @XmlEnumValue("BackupRestored")
    BACKUP_RESTORED("BackupRestored"),
    @XmlEnumValue("RequestIATRedeploy")
    REQUEST_IAT_REDEPLOY("RequestIATRedeploy"),
    @XmlEnumValue("RequestReconnection")
    REQUEST_RECONNECTION("RequestReconnection"),
    @XmlEnumValue("RetrieveProductRequests")
    RETRIEVE_PRODUCT_REQUESTS("RetrieveProductRequests"),
    @XmlEnumValue("Success")
    SUCCESS("Success"),
    @XmlEnumValue("Fail")
    FAIL("Fail"),
    @XmlEnumValue("QueryRemainingIATS")
    QUERY_REMAINING_IATS("QueryRemainingIATS"),
    @XmlEnumValue("RemainingIATS")
    REMAINING_IATS("RemainingIATS"),
    @XmlEnumValue("RequestDataUpload")
    REQUEST_DATA_UPLOAD("RequestDataUpload"),
    @XmlEnumValue("TestBeingDeployed")
    TEST_BEING_DEPLOYED("TestBeingDeployed"),
    @XmlEnumValue("HaltTestDeployment")
    HALT_TEST_DEPLOYMENT("HaltTestDeployment"),
    @XmlEnumValue("DeploymentHalted")
    DEPLOYMENT_HALTED("DeploymentHalted"),
    @XmlEnumValue("ItemSlideDownloadReady")
    ITEM_SLIDE_DOWNLOAD_READY("ItemSlideDownloadReady"),
    @XmlEnumValue("DeploymentDescriptorMismatch")
    DEPLOYMENT_DESCRIPTOR_MISMATCH("DeploymentDescriptorMismatch"),
    @XmlEnumValue("CannotCreateBackup")
    CANNOT_CREATE_BACKUP("CannotCreateBackup"),
    @XmlEnumValue("EncryptionKeysReceived")
    ENCRYPTION_KEYS_RECEIVED("EncryptionKeysReceived"),
    @XmlEnumValue("DeploymentFileManifestReceived")
    DEPLOYMENT_FILE_MANIFEST_RECEIVED("DeploymentFileManifestReceived"),
    @XmlEnumValue("TokenDefinitionReceived")
    TOKEN_DEFINITION_RECEIVED("TokenDefinitionReceived"),
    @XmlEnumValue("ItemSlideManifestReceived")
    ITEM_SLIDE_MANIFEST_RECEIVED("ItemSlideManifestReceived"),
    @XmlEnumValue("AbortDeployment")
    ABORT_DEPLOYMENT("AbortDeployment"),
    @XmlEnumValue("QueryPublicityIAT")
    QUERY_PUBLICITY_IAT("QueryPublicityIAT"),
    @XmlEnumValue("PublicityIAT")
    PUBLICITY_IAT("PublicityIAT"),
    @XmlEnumValue("UpdateUserInformation")
    UPDATE_USER_INFORMATION("UpdateUserInformation"),
    @XmlEnumValue("ServerError")
    SERVER_ERROR("ServerError"),
    @XmlEnumValue("EmailVerificationMismatch")
    EMAIL_VERIFICATION_MISMATCH("EmailVerificationMismatch"),
    @XmlEnumValue("TestFilesMissing")
    TEST_FILES_MISSING("TestFilesMissing"),
    @XmlEnumValue("DeploymentAbortFailed")
    DEPLOYMENT_ABORT_FAILED("DeploymentAbortFailed"),
    @XmlEnumValue("ResultsReady")
    RESULTS_READY("ResultsReady"),
    @XmlEnumValue("NoActivationsRemain")
    NO_ACTIVATIONS_REMAIN("NoActivationsRemain");
    private final String value;

    TransactionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransactionType fromValue(String v) {
        for (TransactionType c: TransactionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
