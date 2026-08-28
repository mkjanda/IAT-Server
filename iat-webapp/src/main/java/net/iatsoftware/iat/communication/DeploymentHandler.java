package net.iatsoftware.iat.communication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.oxm.Marshaller;
import org.springframework.stereotype.Component;

import net.iatsoftware.iat.configfile.ConfigFile;
import net.iatsoftware.iat.entities.EncryptedRSAKey;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.generated.ManifestType;
import net.iatsoftware.iat.generated.ResourceType;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.services.DeploymentService;

import java.io.ByteArrayOutputStream;
import javax.xml.transform.stream.StreamResult;
import jakarta.inject.Inject;

@Component
public class DeploymentHandler implements TransactionHandler {
    static final Logger critical = LogManager.getLogger("critical");

    @Inject
    DeploymentService deploymentService;
    @Inject
    IATRepositoryManager iatRepositoryManager;
    @Inject
    Marshaller marshaller;

    private void handleDeploymentRequest(TransactionContext ctx) {
        var transaction = (TransactionRequest) ctx.inbound();
        var test = ctx.sessionState().repositoryManager().getIATByNameAndClientID(transaction.getIATName(),
                ctx.client().getClientId());
        if (test != null) {
            ctx.reply().send(new TransactionRequest(TransactionType.IAT_EXISTS));
        } else {
            try {
                long deploymentId = deploymentService.beginNewDeployment(ctx.client(), ctx.user(),
                        transaction.getIATName(), ctx.sessionState(), ctx.reply());
                ctx.sessionState().setDeploymentId(deploymentId);
            } catch (Exception ex) {
                critical.error("Error while beginning new deployment for client " + ctx.client().getClientId()
                        + " and user " + ctx.user().getUserId() + ": " + ex.getMessage(), ex);
                ctx.reply().send(new TransactionRequest(TransactionType.TRANSACTION_FAIL));
                return;
            }
            if (ctx.sessionState().deploymentId() == -1L)
                ctx.reply().send(new TransactionRequest(TransactionType.TRANSACTION_FAIL));

            var outTrans = new TransactionRequest(TransactionType.REQUEST_ENCRYPTION_KEY);
            outTrans.setDeploymentId(ctx.deploymentId());
            ctx.reply().send(outTrans);
        }
    }

    @Override
    public boolean supports(TransactionContext ctx) {
        var msg = ctx.inbound();
        if (msg instanceof Manifest) {
            return true;
        } else if (msg instanceof ConfigFile) {
            return true;
        } else if (msg instanceof EncryptedRSAKey)
            return true;
        else if (msg instanceof TransactionRequest) {
            if (((TransactionRequest) msg).getType() == TransactionType.DO_IAT_DEPLOY ||
                    ((TransactionRequest) msg).getType() == TransactionType.REQUEST_IAT_UPLOAD)
                return true;
        }
        return false;
    }

    @Override
    public void handle(TransactionContext ctx) {
        if (ctx.inbound() instanceof EncryptedRSAKey) {
            var key = (EncryptedRSAKey) ctx.inbound();
            ctx.sessionState().setRSAKey(key);
            key.setTest(ctx.test());
            ctx.test().setDataKey(key);
            iatRepositoryManager.addEncryptionKey(ctx.test(), key);
            var outTrans = new TransactionRequest(TransactionType.REQUEST_CONFIG_FILE);
            outTrans.setDeploymentId(ctx.deploymentId());
            ctx.reply().send(outTrans);
        } else if (ctx.inbound() instanceof TransactionRequest) {
            var msg = (TransactionRequest) ctx.inbound();
            if (msg.getType() == TransactionType.REQUEST_IAT_UPLOAD) {
                this.handleDeploymentRequest(ctx);
            } else if (msg.getType() == TransactionType.DO_IAT_DEPLOY) {
                var ds = deploymentService.getDeployer(ctx.deploymentId());
                if (ds == null) {
                    critical.error(
                            "Received a deployment request for a deployment that does not exist.  Deployment ID: "
                                    + ctx.deploymentId());
                    ctx.reply().send(new TransactionRequest(TransactionType.TRANSACTION_FAIL));
                } else {
                    ds.generateTest();
                }
            }
        } else if (ctx.inbound() instanceof ConfigFile) {
            var ds = deploymentService.getDeployer(ctx.deploymentId());
            var test = iatRepositoryManager.getTest(ds.getTestId());
            if (test == null) {
                critical.error("Received a config file for a deployment that does not exist.  Deployment ID: "
                        + ctx.deploymentId());
                ctx.reply().send(new TransactionRequest(TransactionType.TRANSACTION_FAIL));
            } else {
                try {
                    ctx.sessionState().setConfigFile((ConfigFile) ctx.inbound());
                    var bOut = new ByteArrayOutputStream();
                    marshaller.marshal((ConfigFile) ctx.inbound(), new StreamResult(bOut));
                    iatRepositoryManager.addTestResource(new TestResource(test, 0, "text/xml", bOut.toByteArray(),
                            ResourceType.TEST_CONFIGURATION));
                    var outTrans = new TransactionRequest(TransactionType.REQUEST_FILE_MANIFEST);
                    outTrans.setDeploymentId(ctx.deploymentId());
                    ctx.reply().send(outTrans);
                } catch (java.io.IOException ex) {
                    critical.error("Error marshalling config file.", ex);
                    ctx.reply().send(new TransactionRequest(TransactionType.TRANSACTION_FAIL));
                }
            }
        } else if (ctx.inbound() instanceof Manifest) {
            var manifest = (Manifest) ctx.inbound();
            TransactionRequest outTrans;
            if (manifest.getManifestType() == ManifestType.FILE_MANIFEST) {
                ctx.sessionState().setFileManifest(manifest);
                var deployer = deploymentService.getDeployer(ctx.deploymentId());
                var test = iatRepositoryManager.getTest(deployer.getTestId());
                test.setTestSizeKB(manifest.sizeInKb());
                iatRepositoryManager.updateIAT(test);
                outTrans = new TransactionRequest(TransactionType.REQUEST_FILES);
                outTrans.setDeploymentId(ctx.deploymentId());
                ctx.reply().send(outTrans);
                ctx.reply().send(new TransactionRequest(TransactionType.REQUEST_ITEM_SLIDE_MANIFEST));
            } else if (manifest.getManifestType() == ManifestType.ITEM_SLIDE_MANIFEST) {
                ctx.sessionState().setItemSlideManifest(manifest);
                outTrans = new TransactionRequest(TransactionType.REQUEST_ITEM_SLIDES);
                outTrans.setDeploymentId(ctx.deploymentId());
                ctx.reply().send(outTrans);
            }
        }
    }
}
