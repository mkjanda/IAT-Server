/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.deployment;

import java.net.URI;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.transform.stream.StreamSource;
/**
 *
 * @author Michael Janda
 */
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.events.DeploymentFailedEvent;
import net.iatsoftware.iat.events.DeploymentSuccessEvent;
import net.iatsoftware.iat.events.TestDeploymentCompleteEvent;
import net.iatsoftware.iat.events.WebSocketDataSent;
import net.iatsoftware.iat.events.WebSocketFinalDataSent;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.ServerException;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;
import net.iatsoftware.iat.messaging.TransactionRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "DefaultIATRedeployer")
@Scope(value = "prototype")
public class DefaultIATRedeployer
		extends DefaultBaseIATDeployer
		implements IATRedeployer {

	@Named("ServerConfiguration")
	@Inject
	Properties serverConfiguration;

	private final Long oldTestID;

	public DefaultIATRedeployer(
			Long clientID,
			Long deploymentID,
			Long replacementTestID,
			Long testID,
			String sessID) {
		super(clientID, deploymentID, replacementTestID, sessID);
		this.oldTestID = testID;
	}

	protected void createBackup() throws ServerException {
		try {
			var test = iatRepositoryManager.getIAT(this.testId);
			var rootUri = new URI(
					String.format(
							"%s/%s/%s",
							serverConfiguration.getProperty("iat-files"),
							clientID.toString(),
							test.getTestName()));
			if (!Path.of(rootUri).toFile().exists())
				iatRepositoryManager.backupTest(iatRepositoryManager.getIAT(testId));
			var visitor = new FileVisitor<Path>() {
				public FileVisitResult postVisitDirectory(
						Path dir,
						java.io.IOException ex)
						throws java.io.IOException {
					dir.toFile().delete();
					return FileVisitResult.CONTINUE;
				}

				public FileVisitResult preVisitDirectory(
						Path dir,
						BasicFileAttributes attr)
						throws java.io.IOException {
					return FileVisitResult.CONTINUE;
				}

				public FileVisitResult visitFile(Path file, BasicFileAttributes attr)
						throws java.io.IOException {
					if (attr.isRegularFile()) {
						iatRepositoryManager.addTestBackupFile(file.toFile().getName(), Files.readAllBytes(file),
								testId, deploymentSessionId);
						file.toFile().delete();
					}
					return FileVisitResult.CONTINUE;
				}

				public FileVisitResult visitFileFailed(
						Path file,
						java.io.IOException ex)
						throws java.io.IOException {
					return FileVisitResult.CONTINUE;
				}
			};
			Files.walkFileTree(Path.of(rootUri), visitor);
		} catch (java.io.IOException | java.net.URISyntaxException ex) {
			throw new ServerException(
					new ServerExceptionMessage("Error creating backup for redeployment", ex));
		}
	}

	protected void restoreBackup() throws ServerException {
		try {
			iatRepositoryManager.restoreTestBackup(iatRepositoryManager.getIAT(testId));
		}
		catch (java.net.URISyntaxException | java.io.IOException ex) {
			var serverMessage = new ServerExceptionMessage("Error restoring IAT redeploy backup", ex);
			throw new ServerException(serverMessage);
		}
	}

	@Override
	protected void onSuccess(String sessionId) {
		iatRepositoryManager.reassociateResults(this.testId, this.oldTestID);
		var test = this.iatRepositoryManager.getIAT(this.testId);
		iatRepositoryManager.finalizeDeployment(deploymentSessionId);
		test.setRedeployed(true);
		iatRepositoryManager.updateIAT(test);
		iatRepositoryManager.deleteIAT(this.oldTestID);
		this.eventPublisher.publishEvent(
				new DeploymentSuccessEvent(sessionId, deploymentSessionId, this.testId));
	}

	@Override
	protected void onFailure(String sessionId, ServerExceptionMessage sex) {
		try {
			restoreBackup();
		}
		catch (ServerException ex) {
			this.eventPublisher.publishEvent(new WebSocketDataSent(sessionId, 
				new Envelope(ex.getExceptionMessage())));
		}
		this.eventPublisher.publishEvent(
				new DeploymentFailedEvent(sessionId, deploymentSessionId, sex, testId));
	}

	@Override
	public void onDescriptorMismatch(String sessionId) {
		this.iatRepositoryManager.deleteIAT(testId);
		this.eventPublisher.publishEvent(new WebSocketFinalDataSent(sessionId,
			new Envelope(new TransactionRequest(TransactionType.DEPLOYMENT_DESCRIPTOR_MISMATCH))));
	}

	@Override
	public void generateTest(String sessId) {
		final String sessionId = sessId;
		this.scheduler.submit(() -> {
			try {
				IAT oldIAT = iatRepositoryManager.getIAT(this.oldTestID);
				IAT test = iatRepositoryManager.getIAT(this.testId);
				loadTransformSources(test);
				digestTestSegment(IATSource, compiledXSLT.getIATDescriptorX());
				if (SurveySources != null) {
					for (StreamSource s : SurveySources) {
						digestTestSegment(s, compiledXSLT.getSurveyDescriptorX());
					}
				}
				byte[] digest = this.DeploymentDescriptor.digest();
				byte[] oldDigest = oldIAT.getDeploymentDescriptor();
				if (digest.length != oldDigest.length) {
					onDescriptorMismatch(sessionId);
					return;
				}
				for (int ctr = 0; ctr < digest.length; ctr++) {
					if (digest[ctr] != oldDigest[ctr]) {
						{
							onDescriptorMismatch(sessionId);
							return;
						}
					}
				}
				doDeploy(test);
				this.eventPublisher.publishEvent(new TestDeploymentCompleteEvent(sessionId, this.deploymentSessionId));
			} catch (DeploymentTerminationException ex) {
				criticalLogger.error("Error deploying IAT", ex);
				this.eventPublisher.publishEvent(new DeploymentFailedEvent(sessionId, this.deploymentSessionId,
					new ServerExceptionMessage("Deployment Error", ex)));
			}
		});
	}
}
