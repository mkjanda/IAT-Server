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
    MailService mailService;

    @Value("${mail.images.logo-classpath-location}")
    private String logoClasspathLocation;
    @Value("${mail.images.header-classpath-location}")
    private String headerClasspathLocation;


    private void processTransactionRequest(CommunicationEvent e) {
/*
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


                default:
                    break;
            }
        } catch (Exception ex) {
            logger.error("Error occurred processing the transaction", ex);
            this.publisher.publishEvent(new WebSocketFinalDataSent(webSessionId,
                    new Envelope(new ServerExceptionMessage("Error processing transaction", ex))));
        }
                    */
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

}
