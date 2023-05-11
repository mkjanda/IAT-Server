/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.deployment;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.transform.stream.StreamSource;
/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.configfile.ConfigFile;
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

public class DefaultIATRedeployer extends DefaultBaseIATDeployer implements IATRedeployer {

	@Named("ServerConfiguration")
	@Inject
	Properties serverConfiguration;

	private Long oldTestID;

	public DefaultIATRedeployer(Long clientId, Long deploymentId, Long newTestId, Long oldTestId, String session) {
		super(clientId, deploymentId, newTestId, session);
		this.oldTestID = oldTestId;
	}

	public void setOldTestId(Long id) {
		this.oldTestID = id;
	}

	protected void restoreBackup() throws ServerException {
		try {
			iatRepositoryManager.restoreTestBackup(iatRepositoryManager.getIAT(testId));
		} catch (java.net.URISyntaxException | java.io.IOException ex) {
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
		} catch (ServerException ex) {
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

	protected byte[] digestTest(IAT test) throws DeploymentTerminationException {
		try {
			DeploymentDescriptor.reset();
			var tr = iatRepositoryManager.getTestResource(test, 0L);
			String text = new String(tr.getResourceBytes(), StandardCharsets.UTF_16);
			this.CF = (ConfigFile) unmarshaller.unmarshal(new StreamSource(new StringReader(text)));
			StringReader strReader = new StringReader(text);

			digestTestSegment(new StreamSource(strReader), compiledXSLT.getIATDescriptorX());
// implement digest test
/*
			var surveys = this.CF.getSurvey();
			for (var survey : surveys) {
				res = iatRepositoryManager.getTestResource(test, survey.getSurveyName(),
						ResourceType.DEPLOYMENT_FILE);
				text = new String(res.getResourceBytes(), StandardCharsets.UTF_16);
				digestTestSegment(new StreamSource(new StringReader(text)), compiledXSLT.getSurveyDescriptorX());
			}
			byte[] result = DeploymentDescriptor.digest();
			DeploymentDescriptor.reset();*/
			return null;
		} catch (java.io.IOException ex) {
			throw new DeploymentTerminationException("Error unmarshalling config file", ex);
		}
	}

	@Override
	public void generateTest() {
		this.generationFuture = this.scheduler.submit(() -> {
			try {
				IAT oldIAT = iatRepositoryManager.getIAT(this.oldTestID);
				test = iatRepositoryManager.getIAT(this.testId);
				byte[] digest = digestTest(test);
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
