/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.services;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.config.MyBeanFactory;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.events.AbortDeploymentEvent;
import net.iatsoftware.iat.events.DeploymentCleanupEvent;
import net.iatsoftware.iat.events.CommunicationEvent;
import net.iatsoftware.iat.events.WebSocketFinalDataSent;
import net.iatsoftware.iat.events.WebSocketDataSent;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.Message;
import net.iatsoftware.iat.messaging.RSAKeyPair;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.messaging.Handshake;
import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.User;
import net.iatsoftware.iat.events.DataRequestEvent;
import net.iatsoftware.iat.events.DataRequestEventType;
import net.iatsoftware.iat.events.DeploymentTransactionEvent;
import net.iatsoftware.iat.events.UploadRequestEvent;
import net.iatsoftware.iat.generated.KeyType;
import net.iatsoftware.iat.messaging.ActivationRequest;
import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.resultretrieval.DataRetrievalSession;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.oxm.Marshaller;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.PublicKey;
import java.security.KeyFactory;
import java.security.spec.RSAPublicKeySpec;
import java.util.Random;
import java.util.Base64;
import java.util.Calendar;
import java.util.function.Predicate;
import java.util.List;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.inject.Named;
import javax.inject.Inject;

@Service
@Async("TaskScheduler")
@PropertySource("classpath:email/email-config.properties")
public class DefaultTransactionService implements TransactionService {
    private static final Logger logger = LogManager.getLogger();
    private static final Logger transLogger = LogManager.getLogger("transactions");
    private static final Logger critical = LogManager.getLogger("critical");
    @Inject
    Marshaller marshaller;
    @Inject
    IATRepositoryManager iatRepositoryManager;
    @Inject
    DeploymentService deploymentService;
    @Inject
    @Named("ServerConfiguration")
    Properties serverConfiguration;
    @Inject
    ApplicationEventPublisher publisher;
    @Inject
    MyBeanFactory beanFactory;
    @Inject
    WebSocketService webSocketService;
    @Inject
    DataRetrievalSession dataRetrievalSession;
    @Inject
    MailService mailService;

    @Value("${mail.images.logo-classpath-location}")
    private String logoClasspathLocation;
    @Value("${mail.images.header-classpath-location}")
    private String headerClasspathLocation;

    @EventListener
    public void onApplicationEvent(CommunicationEvent e) {
        try {
            Message msg = e.getMessage();
            Client client = (Client) webSocketService.getSessionProperty(e.getSessionId(), "Client");
            if (client == null) {
                if (msg instanceof TransactionRequest) {
                    client = iatRepositoryManager.getClient(((TransactionRequest) msg).getProductKey());
                    if (client == null) {
                        sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.NO_SUCH_CLIENT), true);
                        return;
                    }
                    webSocketService.setSessionProperty(e.getSessionId(), "Client", client);
                }
            }
            String logMsgBase = "Client (" + Long.toString(client.getClientId()) + ") " + client.getProductKey() + ": ";

            User user = (User) webSocketService.getSessionProperty(e.getSessionId(), "User");
            if (msg instanceof TransactionRequest) {
                TransactionRequest trans = (TransactionRequest) msg;
                if (trans.getTransaction() == TransactionType.REQUEST_CONNECTION) {
                    requestConnection(e);
                    transLogger.info(logMsgBase + "Handshake sent");
                    return;
                }
            }
            if (msg instanceof Handshake) {
                Handshake outhand = (Handshake) webSocketService.getSessionProperty(e.getSessionId(), "Handshake");
                if (outhand.checkInHand((Handshake) msg)) {
                    if (client.isDeleted()) {
                        transLogger.info(logMsgBase + "Client deleted");
                        webSocketService.setSessionProperty(e.getSessionId(), "HandsShaken", Boolean.FALSE);
                        sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.CLIENT_DELETED), true);
                    } else if (client.isFrozen()) {
                        transLogger.info(logMsgBase + "Client frozen");
                        webSocketService.setSessionProperty(e.getSessionId(), "HandsShaken", Boolean.FALSE);
                        sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.CLIENT_FROZEN), true);
                    } else {
                        transLogger.info(logMsgBase + "Hands shaken");
                        webSocketService.setSessionProperty(e.getSessionId(), "HandsShaken", Boolean.TRUE);
                        TransactionRequest requestTransmissionTrans = new TransactionRequest(
                                TransactionType.REQUEST_TRANSMISSION);
                        requestTransmissionTrans.setClientID(client.getClientId());
                        if (user != null)
                            requestTransmissionTrans.setActivationKey(user.getActivationKey());
                        sendMessage(e.getSessionId(), requestTransmissionTrans, false);
                    }
                } else {
                    transLogger.info(logMsgBase + "Handshake failed");
                    this.publisher.publishEvent(new WebSocketFinalDataSent(e.getSessionId(),
                            new Envelope(new TransactionRequest(TransactionType.TRANSACTION_FAIL))));
                }
                return;
            } else if (webSocketService.getSessionProperty(e.getSessionId(), "HandsShaken") != Boolean.TRUE) {
                transLogger.info(logMsgBase + "Hands not shaken");
                closeSocket(e.getSessionId());
                return;
            }
            if (msg instanceof TransactionRequest) {
                processTransactionRequest(e);
            } else if (msg instanceof ActivationRequest) {
                activateProduct(e);
            }
        } catch (Exception ex) {
            critical.error("Error occurred processing the transaction", ex);
            var ds = iatRepositoryManager.getDeploymentSession(e);
            if (ds != null) {
                this.publisher.publishEvent(new DeploymentCleanupEvent(ds.getTest().getId(), ex));
            }
            this.publisher.publishEvent(new WebSocketDataSent(e.getSessionId(),
                    new Envelope(new ServerExceptionMessage("Error processing transaction request", ex))));
            this.publisher.publishEvent(new WebSocketFinalDataSent(e.getSessionId(),
                    new Envelope(new TransactionRequest(TransactionType.TRANSACTION_FAIL))));
        }
    }

    private void processTransactionRequest(CommunicationEvent e) {
        TransactionRequest inTrans = (TransactionRequest) e.getMessage();
        String webSessionId = e.getSessionId();
        Random Rand = new Random();
        try {
            Long deploymentID = 0L;
            RSAKeyPair keyPair;
            PartiallyEncryptedRSAKey key;
            Client client = (Client) webSocketService.getSessionProperty(e.getSessionId(), "Client");
            String logMsgBase = "Client (" + Long.toString(client.getClientId()) + ") " + client.getProductKey() + ": ";
            User user;
            TransactionRequest outTrans;
            byte[] randVal;
            String encStrVal;
            var test = (IAT) webSocketService.getSessionProperty(e.getSessionId(), "IAT");
            String iatName = (String) webSocketService.getSessionProperty(e.getSessionId(), "IATName");
            if ((inTrans.getTransaction() != TransactionType.REQUEST_RECONNECTION) && ((client == null)
                    || webSocketService.getSessionProperty(e.getSessionId(), "HandsShaken") == null)) {
                transLogger.info(logMsgBase + "Hands shaken");
                sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.ABORT_TRANSACTION), true);
                return;
            } else if (inTrans.getTransaction() == TransactionType.REQUEST_RECONNECTION) {
                return;
            }
            if (((Boolean) webSocketService.getSessionProperty(e.getSessionId(), "HandsShaken")) != Boolean.TRUE) {
                transLogger.info(logMsgBase + "Handshake failed");
                sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.FAIL), true);
                return;
            }
            switch (inTrans.getTransaction()) {
                case ABORT_TRANSACTION:
                    transLogger.info(logMsgBase + "Abort Transaction");
                    closeSocket(e.getSessionId());
                    break;

                case ABORT_DEPLOYMENT:
                    transLogger.info(logMsgBase + "Abort deployment");
                    this.publisher.publishEvent(
                            new AbortDeploymentEvent(e.getSessionId(), inTrans.getLongValue("DeploymentId")));
                    break;

                case REQUEST_E_MAIL_VERIFICATION:
                    try {
                        user = iatRepositoryManager.getUserByClientAndEmail(client, inTrans.getStringValue("email"));
                        if (user == null) {
                            sendMessage(e.getSessionId(),
                                    new TransactionRequest(TransactionType.EMAIL_VERIFICATION_MISMATCH), true);
                            transLogger.info(logMsgBase + "Request email verification failure");
                        } else if (user.isEMailVerified()) {
                            outTrans = new TransactionRequest(TransactionType.TRANSACTION_SUCCESS);
                            outTrans.setActivationKey(user.getActivationKey());
                            sendMessage(e.getSessionId(), outTrans, true);
                            user.setEMailVerified(true);
                            iatRepositoryManager.updateUser(user);
                            transLogger.info(logMsgBase + "Request email verification success");
                        } else {
                            sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.TRANSACTION_FAIL),
                                    true);
                            transLogger.info(logMsgBase + "Request email verification failure");
                        }
                    } catch (javax.persistence.NoResultException ex) {
                        sendMessage(e.getSessionId(),
                                new TransactionRequest(TransactionType.EMAIL_VERIFICATION_MISMATCH), true);
                        transLogger.info(logMsgBase + "Request email verification failure");
                    }
                    break;

                case REQUEST_NEW_VERIFICATION_E_MAIL:
                    transLogger.info(logMsgBase + "Request resend email verification");
                    try {
                        user = iatRepositoryManager.getUserByClientAndEmail(client, inTrans.getStringValue("email"));
                        if (user.isEMailVerified()) {
                            outTrans = new TransactionRequest(TransactionType.E_MAIL_ALREADY_VERIFIED);
                            outTrans.setActivationKey(user.getActivationKey());
                            sendMessage(e.getSessionId(), outTrans, true);
                            transLogger.info(logMsgBase + "Email already verified");
                            return;
                        }
                    } catch (javax.persistence.NoResultException ex) {
                        Predicate<User> emailNotVerified = u2 -> !u2.isEMailVerified();
                        var clientsUsers = client.getUsers().stream().filter(emailNotVerified);
                        if (!clientsUsers.findFirst().isPresent()) {
                            sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.TRANSACTION_FAIL),
                                    true);
                            return;
                        }
                        user = clientsUsers.findFirst().get();
                        user.setEMail(inTrans.getStringValue("email"));
                        iatRepositoryManager.updateUser(user);
                    }
                    try {
                        EmailParameters emailParams = new EmailParameters(user.getEMail(),
                                "IAT Software eMail Verification", "email/email-verification.html");
                        emailParams.addParameter("user", user);
                        emailParams.addInlineImage("logo", logoClasspathLocation, "image/png");
                        emailParams.addInlineImage("header", headerClasspathLocation, "image/png");
                        mailService.sendEmail(emailParams);
                        outTrans = new TransactionRequest(TransactionType.TRANSACTION_SUCCESS);
                        sendMessage(e.getSessionId(), outTrans, true);
                        transLogger.info(logMsgBase + "Request resend email verification success");
                    } catch (javax.mail.MessagingException ex) {
                        logger.error("Error sending email verification message to " + user.getEMail(), ex);
                        sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.TRANSACTION_FAIL), true);
                    }
                    break;

                case IAT_EXISTS:
                    transLogger.info(logMsgBase + "Check IAT Exists");
                    if (iatRepositoryManager.iatExists(client, inTrans.getIATName())) {
                        test = iatRepositoryManager.getIATByNameAndClientID(inTrans.getIATName(), client.getClientId());
                        webSocketService.setSessionProperty(e.getSessionId(), "IAT", test);
                        var ds = iatRepositoryManager.getDeploymentSession(test);
                        if (ds != null) {
                            outTrans = new TransactionRequest(TransactionType.TEST_BEING_DEPLOYED);
                            outTrans.addLongValue("DeploymentId", ds.getTest().getId());
                            sendMessage(e.getSessionId(), outTrans, false);
                        } else {
                            transLogger.info(String.format("%s IAT exists (%s)", logMsgBase, inTrans.getIATName()));
                            sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.IAT_EXISTS), false);
                        }
                    } else {
                        transLogger.info(logMsgBase + "no such IAT (" + inTrans.getIATName() + ")");
                        sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.NO_SUCH_IAT), false);
                    }
                    break;

                case QUERY_REMAINING_IATS:
                    if (iatRepositoryManager.noRemainingIATs(client)) {
                        sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.INSUFFICIENT_IATS), true);
                        transLogger.info(logMsgBase + "insufficient IATs");
                        return;
                    }
                    /*
                     * if (client.isTrialUser() && !iatRepositoryManager.hasPublicityIAT(client)) {
                     * sendMessage(e.getSessionId(), new
                     * TransactionRequest(TransactionType.QUERY_PUBLICITY_IAT), false); return; }
                     */
                    outTrans = new TransactionRequest(TransactionType.REMAINING_IATS);
                    int nIatsRemaining = iatRepositoryManager.getNumRemainingIATs(client);
                    outTrans.addIntValue("NumIATsRemaining", nIatsRemaining);
                    sendMessage(e.getSessionId(), outTrans, false);
                    transLogger.info(logMsgBase + Integer.toString(nIatsRemaining) + " IATs remaining");
                    break;
                /*
                 * case PUBLICITY_IAT: webSocketService.setSessionProperty(webSessionId,
                 * "PublicityIAT", Boolean.TRUE); outTrans = new
                 * TransactionRequest(TransactionType.REMAINING_IATS);
                 * outTrans.addIntValue("NumIATsRemaining", 1); sendMessage(e.getSessionId(),
                 * outTrans, false); break;
                 */
                case HALT_TEST_DEPLOYMENT:
                    this.publisher.publishEvent(
                            new AbortDeploymentEvent(e.getSessionId(), inTrans.getLongValue("DeploymentId")));
                    sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.DEPLOYMENT_HALTED), false);
                    transLogger.info(logMsgBase + "Halt deployment");
                    break;

                case REQUEST_IAT_UPLOAD:
                    iatName = inTrans.getIATName();
                    Calendar deploymentStartTime = deploymentService.beginNewDeployment(client,
                            (User) webSocketService.getSessionProperty(e.getSessionId(), "User"), iatName,
                            e.getSessionId());
                    if (deploymentStartTime.getTimeInMillis() == 0)
                        return;
                    outTrans = new TransactionRequest(TransactionType.REQUEST_IAT_UPLOAD, client.getClientId());
                    outTrans.addLongValue("DeploymentStartTime", deploymentStartTime.getTimeInMillis());
                    outTrans.addLongValue("DeploymentId",
                            (Long) webSocketService.getSessionProperty(e.getSessionId(), "DeploymentID"));
                    sendMessage(e.getSessionId(), outTrans, false);
                    transLogger.info(logMsgBase + "IAT upload requested (" + iatName + ")");
                    break;

                case REQUEST_DATA_UPLOAD:
                    deploymentID = inTrans.getLongValue("DeploymentId");
                    DeploymentTransactionEvent dEvent = new UploadRequestEvent(e.getSessionId(), deploymentID);
                    this.publisher.publishEvent(dEvent);
                    transLogger.info(logMsgBase + "Data upload requested");
                    break;

                case REQUEST_IAT_REDEPLOY:
                    if (webSocketService.getSessionProperty(e.getSessionId(), "DataPasswordVerified") != Boolean.TRUE) {
                        sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.TRANSACTION_FAIL), true);
                        transLogger.info(logMsgBase + "IAT Redeploy password mismatch (" + iatName + ")");
                        return;
                    }
                    IAT oldTest = iatRepositoryManager.getIATByNameAndClientID(iatName, client.getClientId());
                    webSocketService.setSessionProperty(e.getSessionId(), "OldTest", oldTest);
                    try {
                        Calendar deploymentStart = deploymentService.beginNewRedeployment(client,
                                (User) webSocketService.getSessionProperty(e.getSessionId(), "User"), iatName, oldTest,
                                e.getSessionId());
                        if (deploymentStart.getTimeInMillis() == 0)
                            return;
                        transLogger.info(logMsgBase + "IAT Redeploy initiated (" + iatName + ")");
                        outTrans = new TransactionRequest(TransactionType.REQUEST_IAT_UPLOAD, client.getClientId());
                        outTrans.addLongValue("DeploymentStartTime", deploymentStart.getTimeInMillis());
                        outTrans.addLongValue("DeploymentId",
                                (Long) webSocketService.getSessionProperty(e.getSessionId(), "DeploymentID"));
                        sendMessage(e.getSessionId(), outTrans, false);
                    } catch (java.nio.file.NoSuchFileException ex) {
                        transLogger
                                .info(logMsgBase + " IAT Redeploy cannot create backup because of missing test files.");
                        logger.error("Failed to complete IAT Redeploy backup", ex);
                        outTrans = new TransactionRequest(TransactionType.TEST_FILES_MISSING);
                        sendMessage(e.getSessionId(), outTrans, true);
                    } catch (java.io.IOException | java.net.URISyntaxException ex) {
                        transLogger.info(logMsgBase + "IAT Redeploy cannot create backup (" + iatName + ")");
                        logger.error("Failed to complete IAT Redeploy backup", ex);
                        outTrans = new TransactionRequest(TransactionType.CANNOT_CREATE_BACKUP);
                        sendMessage(e.getSessionId(), outTrans, true);
                    }
                    break;

                case REQUEST_SERVER_REPORT:
                    transLogger.info(logMsgBase + "Request server report");
                    this.publisher.publishEvent(
                            new DataRequestEvent(e.getSessionId(), client.getClientId(), "",
                                    DataRequestEventType.retrieveServerReport));
                    break;

                case REQUEST_ENCRYPTION_KEY:
                    transLogger.info(logMsgBase + "Request encryption key (" + inTrans.getIATName() + ")");
                    webSocketService.setSessionProperty(e.getSessionId(), "IATName", inTrans.getIATName());
                    keyPair = iatRepositoryManager.getRSAKeyPair(
                            (Client) webSocketService.getSessionProperty(e.getSessionId(), "Client"),
                            inTrans.getIATName());
                    webSocketService.setSessionProperty(e.getSessionId(), "RSAKeyPair", keyPair);
                    sendMessage(e.getSessionId(), keyPair, false);
                    break;

                case REQUEST_ADMIN_PASSWORD_VERIFICATION:
                    transLogger.info(logMsgBase + "Request admin password verification");
                    keyPair = (RSAKeyPair) webSocketService.getSessionProperty(e.getSessionId(), "RSAKeyPair");
                    key = keyPair.getAdminKey();
                    randVal = new byte[64];
                    Rand.nextBytes(randVal);
                    webSocketService.setSessionProperty(e.getSessionId(), "UnencryptedValue",
                            Base64.getEncoder().encodeToString(randVal));
                    webSocketService.setSessionProperty(e.getSessionId(), "CurrentPasswordVerification", KeyType.ADMIN);
                    encStrVal = encryptValue(key, randVal);
                    outTrans = new TransactionRequest(TransactionType.VERIFY_PASSWORD);
                    outTrans.addStringValue("EncryptedTestString", encStrVal);
                    sendMessage(e.getSessionId(), outTrans, false);
                    break;

                case REQUEST_DATA_PASSWORD_VERIFICATION:
                    transLogger.info(logMsgBase + "Request data password verification");
                    keyPair = (RSAKeyPair) webSocketService.getSessionProperty(e.getSessionId(), "RSAKeyPair");
                    key = keyPair.getDataKey();
                    randVal = new byte[64];
                    Rand.nextBytes(randVal);
                    webSocketService.setSessionProperty(e.getSessionId(), "UnencryptedValue",
                            Base64.getEncoder().encodeToString(randVal));
                    webSocketService.setSessionProperty(e.getSessionId(), "CurrentPasswordVerification", KeyType.DATA);
                    encStrVal = encryptValue(key, randVal);
                    outTrans = new TransactionRequest(TransactionType.VERIFY_PASSWORD);
                    outTrans.addStringValue("EncryptedTestString", encStrVal);
                    sendMessage(e.getSessionId(), outTrans, false);
                    break;

                case VERIFY_PASSWORD:
                    String val = (String) webSocketService.getSessionProperty(e.getSessionId(), "UnencryptedValue");
                    if (inTrans.getStringValue("DecryptedTestString").equals(val)) {
                        if ((KeyType) webSocketService.getSessionProperty(e.getSessionId(),
                                "CurrentPasswordVerification") == KeyType.ADMIN) {
                            transLogger.info(logMsgBase + "Admin password verified");
                            webSocketService.setSessionProperty(e.getSessionId(), "AdminPasswordVerified",
                                    Boolean.TRUE);
                        } else if ((KeyType) webSocketService.getSessionProperty(e.getSessionId(),
                                "CurrentPasswordVerification") == KeyType.DATA) {
                            transLogger.info(logMsgBase + "Data password verified");
                            webSocketService.setSessionProperty(e.getSessionId(), "DataPasswordVerified", Boolean.TRUE);
                        } else {
                            throw new RuntimeException("Cannot verify password of unspecified type.");
                        }
                        webSocketService.setSessionProperty(e.getSessionId(), "CurrentPasswordVerification", "NONE");
                        sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.PASSWORD_VALID), false);
                    } else {
                        transLogger.info(logMsgBase + "Password invalid");
                        sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.PASSWORD_INVALID), true);
                    }
                    break;

                case DELETE_IAT:
                    if (webSocketService.getSessionProperty(e.getSessionId(), "AdminPasswordVerified") == null) {
                        sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.TRANSACTION_FAIL), true);
                        return;
                    }
                    transLogger.info(logMsgBase + "Deleting IAT (" + test.getTestName());
                    iatRepositoryManager.deleteIAT(test.getId());
                    sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.TRANSACTION_SUCCESS), true);
                    break;

                case DELETE_IAT_DATA:
                    if (webSocketService.getSessionProperty(e.getSessionId(), "AdminPasswordVerified") == null) {
                        sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.TRANSACTION_FAIL), true);
                        return;
                    }
                    transLogger.info(logMsgBase + "Deleting IAT data ("
                            + (String) webSocketService.getSessionProperty(e.getSessionId(), "IATName") + ")");
                    iatRepositoryManager.deleteIATResults(client.getClientId(),
                            (String) webSocketService.getSessionProperty(e.getSessionId(), "IATName"));
                    sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.TRANSACTION_SUCCESS), true);
                    break;

                case REQUEST_RESULTS:
                    if ((webSocketService.getSessionProperty(e.getSessionId(), "AdminPasswordVerified") == null)
                            && (webSocketService.getSessionProperty(e.getSessionId(),
                                    "DataPasswordVerified") == null)) {
                        sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.TRANSACTION_FAIL), true);
                        return;
                    }
                    transLogger.info(logMsgBase + "Requesting results (" + iatName + ")");
                    publisher.publishEvent(new DataRequestEvent(e.getSessionId(), client.getClientId(), iatName,
                            DataRequestEventType.retrieveResults));
                    break;

                case REQUEST_ITEM_SLIDE_MANIFEST:
                    if ((webSocketService.getSessionProperty(e.getSessionId(), "AdminPasswordVerified") == null)
                            && (webSocketService.getSessionProperty(e.getSessionId(),
                                    "DataPasswordVerified") == null)) {
                        sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.TRANSACTION_FAIL), true);
                        return;
                    }
                    transLogger.info(logMsgBase + "Requesting item slide manifest (" + iatName + ")");
                    Manifest itemSlideManifest = test.getManifest();
                    sendMessage(e.getSessionId(), itemSlideManifest, false);
                    break;

                case REQUEST_ITEM_SLIDES:
                    if ((webSocketService.getSessionProperty(e.getSessionId(), "AdminPasswordVerified") == null)
                            && (webSocketService.getSessionProperty(e.getSessionId(),
                                    "DataPasswordVerified") == null)) {
                        sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.TRANSACTION_FAIL), true);
                        return;
                    }
                    transLogger.info(logMsgBase + "Requesting item slides (" + iatName + ")");
                    publisher.publishEvent(new DataRequestEvent(e.getSessionId(), client.getClientId(), iatName,
                            DataRequestEventType.retrieveItemSlides));
                    break;

                case UPDATE_USER_INFORMATION:
                    user = iatRepositoryManager.getUserByClientAndActivationKey(
                            iatRepositoryManager.getClient(inTrans.getProductKey()), inTrans.getActivationKey());
                    user.setFName(inTrans.getStringValue("FirstName"));
                    user.setLName(inTrans.getStringValue("LastName"));
                    user.setTitle(inTrans.getStringValue("Title"));
                    iatRepositoryManager.updateUser(user);
                    break;

                default:
                    break;
            }
        } catch (Exception ex) {
            logger.error("Error occurred processing the transaction", ex);
            this.publisher.publishEvent(new WebSocketFinalDataSent(webSessionId,
                    new Envelope(new ServerExceptionMessage("Error processing transaction", ex))));
        }
    }

    private void sendMessage(String webSessionId, Message msg, boolean lastTransmission) {
        if (!lastTransmission) {
            this.publisher.publishEvent(new WebSocketDataSent(webSessionId, new Envelope(msg)));
        } else {
            this.publisher.publishEvent(new WebSocketFinalDataSent(webSessionId, new Envelope(msg)));
        }
    }

    private void closeSocket(String webSessionId) {
        webSocketService.unregisterWebSocket(webSessionId);
    }

    private String encryptValue(PartiallyEncryptedRSAKey key, byte[] val) {
        try {
            KeyFactory keyFact = KeyFactory.getInstance("RSA");
            BigInteger exponent = new BigInteger(key.getExponentBytes());
            BigInteger modulus = new BigInteger(key.getModulusBytes());
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
            PublicKey pubKey = keyFact.generatePublic(keySpec);
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            c.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64.getEncoder().encodeToString(c.doFinal(val));
        } catch (Exception ex) {
            logger.error("Error encrypted password verification value", ex);
            return "";
        }
    }

    private void requestConnection(CommunicationEvent e)
            throws java.io.UnsupportedEncodingException, javax.persistence.NoResultException {
        TransactionRequest inTrans = (TransactionRequest) e.getMessage();
        Handshake h = Handshake.createOutHand();
        webSocketService.setSessionProperty(e.getSessionId(), "Handshake", h);
        Client c = (Client) webSocketService.getSessionProperty(e.getSessionId(), "Client");
        if (c == null) {
            logger.error("Client not found");
            sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.NO_SUCH_CLIENT), true);
            return;
        }
        if (inTrans.getActivationKey() == null) {
            sendMessage(e.getSessionId(), h, false);
            return;
        }
        User u = null;
        String email = inTrans.getStringValue("email");
        try {
            u = iatRepositoryManager.getUserByClientAndActivationKey(c, inTrans.getActivationKey());
        } catch (Exception ex) {
            logger.error("Cannot locate user by client and activation key", ex);
            try {
                u = iatRepositoryManager.getUserByClientAndEmail(c, email);
                if (u.getActivationKey() == null) {
                    u.setActivationKey(inTrans.getActivationKey());
                    iatRepositoryManager.updateUser(u);
                }
            } catch (javax.persistence.NoResultException ex2) {
            }
        }
        if (u != null) {
            webSocketService.setSessionProperty(e.getSessionId(), "User", u);
        }
        webSocketService.setSessionProperty(e.getSessionId(), "IATName", inTrans.getIATName());
        sendMessage(e.getSessionId(), h, false);
    }

    private void activateProduct(CommunicationEvent e) throws java.io.UnsupportedEncodingException,
            javax.mail.MessagingException, org.springframework.mail.MailSendException {
        ActivationRequest request = (ActivationRequest) e.getMessage();
        Client c = (Client) this.webSocketService.getSessionProperty(e.getSessionId(), "Client");
        if (c.isDeleted()) {
            this.sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.CLIENT_FROZEN), true);
            return;
        } else if (c.isFrozen()) {
            this.sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.CLIENT_FROZEN), true);
            return;
        }
        if (c.getActivationsRemaining() != null) {
            if (c.getActivationsRemaining() == 0) {
                this.sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.NO_ACTIVATIONS_REMAIN), true);
                return;
            } else {
                c.setActivationsRemaining(c.getActivationsRemaining() - 1);
                iatRepositoryManager.updateClient(c);
            }
        }

        User user;
        List<User> users = c.getUsers();
        if (users.stream().anyMatch(u -> u.getEMail().equals(request.getEMail()))) {
            user = users.stream().filter(u -> u.getEMail().equals(request.getEMail())).findFirst().get();
            if (user.isEMailVerified()) {
                transLogger.info("Client (" + Long.toString(user.getClient().getClientId()) + ") " + c.getProductKey()
                        + ": Product activated");
                TransactionRequest outTrans = new TransactionRequest(TransactionType.E_MAIL_ALREADY_VERIFIED);
                outTrans.setActivationKey(user.getActivationKey());
                sendMessage(e.getSessionId(), outTrans, true);
                return;
            }
        } else {
            user = new User(request, users.size() + 1, c);
            iatRepositoryManager.addUser(user);
        }
        c.setActivationsConsumed(c.getActivationsConsumed() + 1);
        iatRepositoryManager.updateClient(c);
        // try {
        EmailParameters emailParams = new EmailParameters(user.getEMail(), "IAT Software eMail Verification",
                "email/email-verification.html");
        emailParams.addParameter("user", user);
        emailParams.addInlineImage("logo", logoClasspathLocation, "image/png");
        emailParams.addInlineImage("header", headerClasspathLocation, "image/png");
        mailService.sendEmail(emailParams);
        /*
         * } catch (javax.mail.MessagingException |
         * org.springframework.mail.MailSendException ex) {
         * logger.error("Error sending email verification message to " +
         * user.getEMail(), ex); this.sendMessage(e.getSessionId(), new
         * TransactionRequest(TransactionType.TRANSACTION_FAIL), true); return; } trans
         */
        logger.info("Client (" + Long.toString(user.getClient().getClientId()) + ") " + c.getProductKey()
                + ": Product activated");
        this.sendMessage(e.getSessionId(), new TransactionRequest(TransactionType.TRANSACTION_SUCCESS), true);
    }
}
