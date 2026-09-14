/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.repositories;

/**
 *
 * @author Michael Janda
 */

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.transaction.annotation.Transactional;

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.ClientExceptionReport;
import net.iatsoftware.iat.entities.CorsOrigin;
import net.iatsoftware.iat.entities.DeploymentPacket;
import net.iatsoftware.iat.entities.EncCodeLine;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.User;
import net.iatsoftware.iat.entities.TestSegment;
import net.iatsoftware.iat.entities.TestBackupFile;
import net.iatsoftware.iat.entities.UniqueResponse;
import net.iatsoftware.iat.entities.UniqueResponseItem;
import net.iatsoftware.iat.entities.EncryptedResultSet;
import net.iatsoftware.iat.entities.ResourceReference;
import net.iatsoftware.iat.entities.Crypt;
import net.iatsoftware.iat.entities.TestResultFragment;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.entities.AdminTimer;
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.SpecifierValue;
import net.iatsoftware.iat.events.CommunicationEvent;
import net.iatsoftware.iat.generated.CodeType;
import net.iatsoftware.iat.generated.PacketType;
import net.iatsoftware.iat.generated.ResourceType;
import net.iatsoftware.iat.messaging.ServerReport;
import net.iatsoftware.iat.messaging.IATReport;
import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.messaging.IATList;
import net.iatsoftware.iat.messaging.IATListEntry;

import org.springframework.oxm.Marshaller;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import java.util.Calendar;

@Service
public class DefaultIATRepositoryManager implements IATRepositoryManager {

    private final Logger critical = LogManager.getLogger("critical");
    @Inject
    AdminTimerRepository adminTimerRepository;
    @Inject
    ClientRepository clientRepository;
    @Inject
    EncCodeLineRepository codeLineRepository;
    @Inject
    DeploymentPacketRepository deploymentPacketRepository;
    @Inject
    IATRepository iatRepository;
    @Inject
    PartiallyEncryptedRSAKeyRepository partiallyEncryptedRSAKeyRepository;
    @Inject
    ResultSetRepository resultSetRepository;
    @Inject
    TestSegmentRepository testSegmentRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    UniqueResponseRepository uniqueResponseRepository;
    @Inject
    UniqueResponseItemRepository uniqueResponseItemRepository;
    @Inject
    TestResultFragmentRepository testResultFragmentRepository;
    @Inject
    DeploymentSessionRepository deploymentSessionRepository;
    @Inject
    SpecifierValueRepository specifierValueRepository;
    @Inject
    TestBackupFileRepository testBackupFileRepository;
    @Inject
    ClientExceptionRepository clientExceptionRepository;
    @Inject
    CorsOriginRepository corsOriginRepository;
    @Inject
    TestResourceRepository testResourceRepository;
    @Inject
    ResourceReferenceRepository resourceReferenceRepository;
    @Inject
    Marshaller marshaller;

    @Transactional
    @Override
    public Long registerIAT(final IAT test) {
        iatRepository.add(test);
        return test.getId();
    }

    @Transactional
    @Override
    public void storeDeploymentPacket(final DeploymentPacket dp) {
        deploymentPacketRepository.add(dp);
    }

    @Transactional
    @Override
    public boolean queryIATExists(final String productKey, final String iatName) {
        return iatRepository.containsByProductKey(productKey, iatName);
    }

    @Transactional
    @Override
    public void addEncryptionKey(final IAT test, final Crypt key) {
        key.setTest(test);
        partiallyEncryptedRSAKeyRepository.add(key);
    }

    @Transactional
    @Override
    public void deleteDeploymentPackets(final List<DeploymentPacket> packets) {
        deploymentPacketRepository.deletePackets(packets);
    }

    @Transactional
    @Override
    public void storeCodeLine(final EncCodeLine cl) {
        codeLineRepository.add(cl);
    }

    @Transactional
    @Override
    public void addTestSegment(TestSegment ts) {
        testSegmentRepository.add(ts);
    }

    @Transactional
    public TestSegment getTestSegment(IAT iat, String segmentName) {
        return testSegmentRepository.getByTestAndElemName(iat, segmentName);
    }

    @Transactional
    @Override
    public void setTestDescriptor(final Long testID, final byte[] descriptor) {
        iatRepository.setDescriptor(testID, descriptor);
    }

    @Transactional
    @Override
    public IAT getIATByNameAndClientID(final String iatName, final long clientID) {
        return iatRepository.get(iatName, clientID);
    }

    @Transactional
    @Override
    public String getTestSegmentHtml(final Long testSegmentID) {
        return testSegmentRepository.getHtmlBytes(testSegmentID);
    }

    @Transactional
    @Override
    public Client getClient(final Long id) {
        return clientRepository.get(id);
    }

    @Transactional
    @Override
    public Client getClient(final String productKey) {
        return clientRepository.getByProductKey(productKey);
    }

    @Transactional
    @Override
    public boolean iatExists(final Client c, final String iatName) {
        return iatRepository.iatExists(c, iatName);
    }

    @Transactional
    @Override
    public boolean noRemainingIATs(final Client client) {
        if (client.getNumIATsAlotted() == null) {
            return false;
        }
        return (client.getNumIATsAlotted() <= iatRepository.getNumIATs(client));
    }

    @Transactional
    @Override
    public long getFreeDiskSpaceKB(final Client client) {
        if (client.getDiskAlottmentMB() == null) {
            return -1;
        }
        return (client.getDiskAlottmentMB() << 10) - iatRepository.getConsumedDiskSpaceKB(client);
    }

    @Transactional
    @Override
    public Long registerIAT(final User user, final String iatName, final int testSizeKB) {
        try {
            final IAT iat = new IAT();
            iat.setUser(user);
            iat.setTestSizeKB(testSizeKB);
            iat.setTestName(iatName);
            iatRepository.add(iat);
            return iat.getId();
        } catch (jakarta.xml.bind.JAXBException ex) {
            critical.error("Unable to create JAXBContext", ex);
            return -1L;
        }
    }

    @Transactional
    @Override
    public IAT getIAT(final Long testID) {
        return iatRepository.get(testID);
    }

    @Transactional
    @Override
    public void addUniqueResponse(final UniqueResponse ur) {
        uniqueResponseRepository.add(ur);
    }

    @Transactional
    @Override
    public void addUniqueResponseItem(final UniqueResponseItem urItem) {
        uniqueResponseItemRepository.add(urItem);
    }

    @Transactional
    @Override
    public void addUniqueResponses(final UniqueResponseItem uri, final List<String> responses) {
        responses.forEach((resp) -> {
            uniqueResponseRepository.add(new UniqueResponse(uri, resp));
        });
    }

    @Transactional
    @Override
    public int getResultDataFormat(final IAT test) {
        return iatRepository.getResultDataFormat(test);
    }

    @Transactional
    @Override
    public void creditAdministration(final IAT test) {
        if (test.getClient().getAdministrationsRemaining() != null) {
            test.getClient().setAdministrationsRemaining(test.getClient().getAdministrationsRemaining() + 1);
        }
        if (test.getClient().getNumAdministrations() > 0) {
            test.getClient().setNumAdministrations(test.getClient().getNumAdministrations() - 1);
            clientRepository.update(test.getClient());
        }
        if (test.getNumAdministrations() > 0) {
            test.setNumAdministrations(test.getNumAdministrations() - 1);
            iatRepository.update(test);
        }
    }

    @Transactional
    @Override
    public boolean debitAdministration(final IAT test) {
        if ((test.getClient().getAdministrationsRemaining() != null)
                && (test.getClient().getAdministrationsRemaining() == 0)) {
            return false;
        }
        if (test.getClient().getAdministrationsRemaining() != null) {
            test.getClient().setAdministrationsRemaining(test.getClient().getAdministrationsRemaining() - 1);
        }
        test.getClient().setNumAdministrations(test.getClient().getNumAdministrations() + 1);
        clientRepository.update(test.getClient());
        test.setNumAdministrations(test.getNumAdministrations() + 1);
        iatRepository.update(test);
        return true;
    }

    @Transactional
    @Override
    public List<byte[]> getItemSlides(IAT test) {
        return testResourceRepository.getItemSlides(test);
    }

    @Transactional
    @Override   
    public Manifest getItemSlideManifest(IAT test) {
        return testResourceRepository.getItemSlideManifest(test);
    }

    @Transactional
    @Override
    public List<TestSegment> getTestElems(final IAT test) {
        final List<TestSegment> segments = testSegmentRepository.getTestElems(test);
            if (test.isAlternate()) {
                test.setAlternate(false);
            } else {
                test.setAlternate(true);
            }
        iatRepository.update(test);
        return segments;
    }

    @Transactional
    @Override
    public IAT updateIAT(final IAT test) {
        return iatRepository.update(test);
    }

    @Transactional
    @Override
    public IAT getIATByAdminID(final Long adminID) {
        final EncryptedResultSet rs = resultSetRepository.get(adminID);
        return rs.getTest();
    }

    @Transactional
    @Override
    public UniqueResponseItem findUniqueResponseItem(final IAT test, final String surveyName) {
        return uniqueResponseItemRepository.getByTestAndSurveyName(test, surveyName);
    }

    @Transactional
    @Override
    public int getElementPositionInTest(final IAT test, final String elemName) {
        return testSegmentRepository.getElementPositionInTest(test, elemName);
    }

    @Transactional
    @Override
    public Crypt getEncryptionKey(final IAT test) {
        return partiallyEncryptedRSAKeyRepository.getDataKey(test);
    }

    @Transactional
    @Override
    public boolean storeResultFragment(final Long timerID, final String testElem, final byte[] encCipher,
            final byte[] encIV, final byte[] encData, final boolean complete) {
        if (!adminTimerRepository.refreshTimer(timerID)) {
            return false;
        }
        final AdminTimer timer = adminTimerRepository.get(timerID);
        final int ordinal = testSegmentRepository.getElementPositionInTest(timer.getTest(), testElem);
        final TestResultFragment frag = new TestResultFragment(timer, ordinal, encCipher, encIV, encData);
        testResultFragmentRepository.add(frag);
        if (complete) {
            adminTimerRepository.setComplete(timer);
        }
        return true;
    }

    @Transactional
    @Override
    public List<TestResultFragment> getResultFragments(final AdminTimer timer) {
        return testResultFragmentRepository.get(timer);
    }

    @Transactional
    @Override
    public EncryptedResultSet getResultSet(final Long adminID) {
        return resultSetRepository.get(adminID);
    }

    @Transactional
    @Override
    public void updateResultSet(final EncryptedResultSet rs) {
        resultSetRepository.update(rs);
    }

    @Transactional
    @Override
    public void deleteResultFragments(final Long adminID) {
        final EncryptedResultSet rs = resultSetRepository.get(adminID);
        testResultFragmentRepository.deleteResultFragments(rs);
    }

    @Transactional
    @Override
    public List<EncCodeLine> getEncryptedLines(final TestSegment testSegment, final CodeType codeType) {
        return codeLineRepository.getEncryptedLines(testSegment, codeType);
    }

    @Transactional
    @Override
    public void updateCodeLine(final EncCodeLine line) {
        codeLineRepository.update(line);
    }

    @Transactional
    @Override
    public List<EncCodeLine> getOrderedCodeLines(final Long testSegmentID) {
        final TestSegment testSegment = testSegmentRepository.get(testSegmentID);
        return codeLineRepository.getOrderedCodeLines(testSegment);

    }

    @Transactional
    @Override
    public IATList buildIATList(final Long clientID) {
        final Client c = clientRepository.get(clientID);
        final List<IAT> iats = iatRepository.getIATsByClient(c);
        final IATList list = new IATList();
        for (final IAT i : iats) {
            final User u = i.getUser();
            final IATListEntry entry = new IATListEntry(i.getTestName(), u);
            list.getIAT().add(entry);
        }
        return list;
    }

    @Transactional
    @Override
    public Crypt getDataKey(final Long clientID, final String testName) {
        final IAT test = iatRepository.get(testName, clientID);
        if (test == null) {
            return null;
        }
        return partiallyEncryptedRSAKeyRepository.getDataKey(test);
    }

    @Transactional
    @Override
    public long getNumResults(final Long clientID, final String testName) {
        final IAT test = iatRepository.get(testName, clientID);
        if (test == null)
            return -1;
        return resultSetRepository.getNumResults(test);
    }

    @Transactional
    @Override
    public List<EncryptedResultSet> getResults(final Long clientID, final String testName) {
        final IAT test = iatRepository.get(testName, clientID);
        test.setLastDataRetrieval(Calendar.getInstance());
        iatRepository.update(test);
        return resultSetRepository.getResults(test);
    }

    @Transactional
    @Override
    public void deleteIATResults(final Long clientID, final String testName) {
        final IAT test = iatRepository.get(testName, clientID);
        resultSetRepository.deleteResults(test);
    }

    @Transactional
    @Override
    public int getResultDataFormat(final String testName, final Long clientID) {
        final IAT test = iatRepository.get(testName, clientID);
        if (test == null)
            return 0;
        return test.getResultFormat();
    }

    @Transactional
    @Override
    public ServerReport retrieveClientReport(final Long clientID) {
        final ServerReport sr = new ServerReport();
        final Client c = clientRepository.get(clientID);
        sr.setContactFName(c.getFirstName());
        sr.setContactLName(c.getLastName());
        sr.setDiskAlottmentMB(c.getDiskAlottmentMB());
        sr.setNumAdministrations(c.getNumAdministrations());
        sr.setNumAdministrationsRemaining(c.getAdministrationsRemaining());
        sr.setNumIATsAlotted(c.getNumIATsAlotted());
        sr.setDiskAlottmentRemainingKB((int) ((sr.getDiskAlottmentMB() << 10) - iatRepository.getDiskUsageByClient(c)));
        final List<IAT> iats = iatRepository.getIATsByClient(c);
        for (final IAT iat : iats) {
            final IATReport report = new IATReport();
            report.setAuthorName(iat.getUser().getFName() + " " + iat.getUser().getLName());
            report.setTestName(iat.getTestName());
            report.setNumAdministrations(iat.getNumAdministrations());
            report.setLastDataRetrieval(iat.getLastDataRetrieval().toString());
            report.setTestSizeKB(iat.getTestSizeKB());
            report.setNumResultSets((int) resultSetRepository.getNumResults(iat));
            sr.getIATReport().add(report);
        }
        return sr;
    }

    @Transactional
    @Override
    public void deleteAdminTimer(final AdminTimer adminTimer) {
        adminTimerRepository.delete(adminTimer);
    }

    @Transactional
    @Override
    public List<String> checkAdminTimers() {
        final List<AdminTimer> expiredTimers = adminTimerRepository.getExpiredTimers();
        for (final AdminTimer t : expiredTimers) {
            UniqueResponse ur;
            if ((ur = t.getUniqueResponse()) != null) {
                uniqueResponseRepository.free(ur);
            }
            creditAdministration(t.getTest());
        }
        adminTimerRepository.deleteTimers(expiredTimers);
        return expiredTimers.stream().map(at -> at.getIATSESSIONID()).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public long createAdminTimer(final IAT test, String iatSessId) {
        final AdminTimer timer = new AdminTimer(test);
        timer.setIATSESSIONID(iatSessId);
        adminTimerRepository.add(timer);
        return timer.getId();
    }

    @Transactional
    @Override
    public long createAdminTimer(final IAT test, String iatSessId, final byte[] token) {
        final AdminTimer timer = new AdminTimer(test);
        timer.setTesteeToken(token);
        timer.setIATSESSIONID(iatSessId);
        adminTimerRepository.add(timer);
        return timer.getId();
    }

    @Transactional
    @Override
    public boolean refreshAdminTimer(final Long timerID) {
        return adminTimerRepository.refreshTimer(timerID);
    }

    @Transactional
    @Override
    public List<IAT> getIATs(final Client client) {
        return iatRepository.getIATsByClient(client);
    }

    @Transactional
    @Override
    public long getClientDiskUsageKB(final Client client) {
        return iatRepository.getConsumedDiskSpaceKB(client);
    }

    @Transactional
    @Override
    public int getNumResultSets(final IAT test) {
        return (int) resultSetRepository.getNumResults(test);
    }

    @Transactional
    @Override
    public Map<AdminTimer, List<TestResultFragment>> getCompletedResultSets() {
        final List<AdminTimer> timers = adminTimerRepository.getCompleted();
        final Map<AdminTimer, List<TestResultFragment>> timerMap = new HashMap<>();
        timers.forEach((t) -> {
            timerMap.put(t, testResultFragmentRepository.get(t));
        });
        return timerMap;
    }

    @Transactional
    public List<ResourceReference> getResourceReferences(final Client client, final String iatName) {
        final IAT test = iatRepository.get(iatName, client.getClientId());
        if (test == null)
            return null;
        return resourceReferenceRepository.getResourceReferences(test);
    }

    @Transactional
    @Override
    public DeploymentSession getDeploymentSession(Long id) {
        try {
            return deploymentSessionRepository.get(id);
        } catch (jakarta.persistence.NoResultException ex) {
            return null;
        }
    }

    @Transactional
    @Override
    public void storeDeploymentSession(DeploymentSession ds) {
        deploymentSessionRepository.add(ds);
    }

    @Transactional
    @Override
    public void storeEncryptionKey(Crypt key) {
        partiallyEncryptedRSAKeyRepository.add(key);
    }

    @Transactional
    @Override
    public void updateEncryptionKey(Crypt key) {
        partiallyEncryptedRSAKeyRepository.update(key);
    }

    @Transactional
    public void finalizeDeployment(Long dsId) {
        var ds = deploymentSessionRepository.get(dsId);
        deploymentSessionRepository.delete(ds);
    }

    @Transactional
    @Override
    public byte[] getDeploymentPacketData(IAT test, PacketType packetType, int ordinal) {
        try {
            return deploymentPacketRepository.getData(deploymentSessionRepository.get(test.getId()), packetType,
                    ordinal);
        } catch (jakarta.persistence.NoResultException ex) {
            return null;
        }
    }

    @Transactional
    @Override
    public void storeEncryptedCode(TestSegment ts, List<EncCodeLine> code) {
        ts = testSegmentRepository.update(ts);
        for (final EncCodeLine cl : code) {
            cl.setTestSegment(ts);
            codeLineRepository.add(cl);
        }
    }

    @Transactional
    @Override
    public TestSegment getTestSegmentByID(Long id) {
        return testSegmentRepository.get(id);
    }

    @Transactional
    public List<TestSegment> getTestSegments(IAT test) {
        return testSegmentRepository.getTestSegments(test);
    }

    @Transactional
    @Override
    public void addTest(IAT test) {
        iatRepository.add(test);
    }

    @Transactional
    public void deleteIAT(Long testID) {
        iatRepository.deleteById(testID);
    }

    @Transactional
    public List<String> getTestElemNames(IAT test) {
        return testSegmentRepository.getTestElemNames(test);
    }

    @Transactional
    public void addSpecifierValue(SpecifierValue sv) {
        specifierValueRepository.add(sv);
        adminTimerRepository.update(sv.getAdmin());
    }

    @Transactional
    public Crypt getRSAKey(Long clientID, String testName) {
        IAT test = iatRepository.get(testName, clientID);
        return partiallyEncryptedRSAKeyRepository.getDataKey(test);
    }

    @Transactional
    public AdminTimer getAdminTimerById(Long id) {
        return adminTimerRepository.get(id);
    }

    @Transactional
    public UniqueResponse getUniqueResponse(UniqueResponseItem uri, String val) {
        return uniqueResponseRepository.getUniqueResponse(uri, val);
    }

    @Transactional
    public void updateUniqueResponse(UniqueResponse ur) {
        uniqueResponseRepository.update(ur);
    }

    @Transactional
    public List<SpecifierValue> getDynamicSpecifierValues(final Long adminID) {
        return specifierValueRepository.getByAdminID(adminTimerRepository.get(adminID));
    }

    @Transactional
    public void addTestBackupFile(String fName, byte[] fileData, Long testID,
            Long deploymentId) {
        try {
            IAT test = iatRepository.get(testID);
            DeploymentSession ds = deploymentSessionRepository.get(test.getId());
            TestBackupFile tbf = new TestBackupFile(fName, fileData, test, ds);
            testBackupFileRepository.add(tbf);
        } catch (jakarta.persistence.NoResultException ex) {

        }
    }

    @Transactional
    public void deleteTestBackupFiles(IAT test) {
        testBackupFileRepository.deleteTestBackup(test);
    }

    @Transactional
    public void restoreTestBackup(IAT test) throws java.net.URISyntaxException, java.io.IOException {
        testBackupFileRepository.restoreTestBackup(test);
    }

    @Transactional
    @Override
    public void copyRSAKey(Long newTestID, Long oldTestID) {
        partiallyEncryptedRSAKeyRepository.copyRSAKeys(iatRepository.get(newTestID), iatRepository.get(oldTestID));
    }

    @Transactional
    public DeploymentSession updateDeploymentSession(DeploymentSession ds) {
        return deploymentSessionRepository.update(ds);
    }

    @Transactional
    public void updateClient(Client c) {
        clientRepository.update(c);
    }

    @Transactional
    @Override
    public void deleteAdminTimers(Collection<AdminTimer> timers) {
        adminTimerRepository.deleteTimers(timers);
    }

    @Transactional
    @Override
    public int getNumRemainingIATs(Client c) {
        if (c.getNumIATsAlotted() == null) {
            return -1;
        }
        return (c.getNumIATsAlotted() - (int) iatRepository.getNumIATs(c));
    }

    @Transactional
    @Override
    public AdminTimer updateTestAdmin(AdminTimer admin) {
        return adminTimerRepository.update(admin);
    }

    @Transactional
    @Override
    public int getIatPositionInTest(final IAT test) {
        return testSegmentRepository.getInitialIatPositionInTest(test);
    }

    
    @Transactional
    @Override
    public void recordClientException(final ClientExceptionReport ex) {
        clientExceptionRepository.add(ex);
    }

    
    @Transactional
    @Override
    public List<IAT> getExpiredTestResults(final long timeout) {
        return iatRepository.getExpiredTestResults(timeout);
    }

    @Transactional
    @Override
    public List<Client> getClientsWithCors() {
        return corsOriginRepository.getClientsWithCors();
    }

    @Transactional
    @Override
    public List<CorsOrigin> getCorsOriginsForClient(final Client c) {
        return corsOriginRepository.getCorsOriginsForClient(c);
    }

    @Transactional
    @Override
    public Iterable<CorsOrigin> getAllCorsOrigins() {
        return corsOriginRepository.getAll();
    }

    @Transactional
    @Override
    public boolean clientIdMatchesClientSecret(String id, String secret) {
        return iatRepository.clientIdMatchesClientSecret(id, secret);
    }

    @Transactional
    @Override
    public boolean checkClientEmailExists(String email) {
        return clientRepository.clientWithEmailExists(email);
    }

    @Transactional
    public TestResource getTestResource(IAT test, Long id) {
        return testResourceRepository.get(test, id);
    }

    @Transactional
    public TestResource updateTestResource(TestResource resource) {
        return testResourceRepository.update(resource);
    }

    @Transactional
    public void addTestResource(TestResource tr) {
        testResourceRepository.add(tr);
    }

    @Transactional
    public void addResourceReference(ResourceReference rr) {
        resourceReferenceRepository.add(rr);
    }

    @Transactional
    public List<TestResource> getTestResources(IAT test, ResourceType type) {
        return testResourceRepository.getFromTest(test, type);
    }

    @Transactional
    public List<TestResource> getDeploymentResources(IAT test) {
        return testResourceRepository.getDeploymentResources(test);
    }


    @Transactional
    public void deleteDeploymentSession(Long id) {
        deploymentSessionRepository.deleteById(id);
    }

    @Transactional
    public void deleteDeploymentSession(IAT test) {
        deleteDeploymentSession(test.getDeploymentSession().getId());
    }

    @Transactional
    public DeploymentSession getDeploymentSession(IAT test) {
        return deploymentSessionRepository.get(test);
    }

    @Transactional
    public DeploymentSession getDeploymentSession(CommunicationEvent e) {
        return deploymentSessionRepository.get(e);
    }

    @Transactional
    public void deleteIAT(IAT test) {
        iatRepository.delete(test);
    }

    @Transactional
    public TestResource getTestImage(IAT test, int index) {
        return testResourceRepository.getTestImage(test, index);
    }

    @Transactional
    public IAT getTest(long testID) {
        return iatRepository.get(testID);
    }

    @Transactional
    public void addResultSet(EncryptedResultSet encResults) {
        resultSetRepository.add(encResults);
    }

}
