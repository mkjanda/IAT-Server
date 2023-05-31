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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.ClientExceptionReport;
import net.iatsoftware.iat.entities.CorsOrigin;
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.DeploymentPacket;
import net.iatsoftware.iat.entities.EncCodeLine;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.entities.RSAKeyData;
import net.iatsoftware.iat.entities.ResourceReference;
import net.iatsoftware.iat.entities.User;
import net.iatsoftware.iat.entities.TestSegment;
import net.iatsoftware.iat.entities.TestResultFragment;
import net.iatsoftware.iat.entities.OAuthAccess;
import net.iatsoftware.iat.entities.UniqueResponse;
import net.iatsoftware.iat.entities.UniqueResponseItem;
import net.iatsoftware.iat.entities.ResultSet;
import net.iatsoftware.iat.entities.SpecifierValue;
import net.iatsoftware.iat.events.CommunicationEvent;
import net.iatsoftware.iat.generated.CodeType;
import net.iatsoftware.iat.generated.TokenType;
import net.iatsoftware.iat.generated.PacketType;
import net.iatsoftware.iat.generated.ResourceType;
import net.iatsoftware.iat.messaging.RSAKeyPair;
import net.iatsoftware.iat.messaging.IATList;
import net.iatsoftware.iat.messaging.ServerReport;
import net.iatsoftware.iat.entities.DynamicSpecifier;
import net.iatsoftware.iat.entities.AdminTimer;
import net.iatsoftware.iat.entities.TestResource;
public interface IATRepositoryManager {
    Long registerIAT(IAT test);
    void storeDeploymentPacket(DeploymentPacket dp);
    boolean queryIATExists(String productKey, String iatName);
    void storeEncryptionKey(IAT test, PartiallyEncryptedRSAKey key);
    void deleteDeploymentPackets(List<DeploymentPacket> packets);
    void storeCodeLine(EncCodeLine cl);
    void addTestSegment(TestSegment ts);
    void setTestDescriptor(Long testID, byte[] descriptor);
    IAT getIATByNameAndClientID(String testName, long clientID);
    String getTestSegmentHtml(Long testSegmentID);
    Client getClient(Long clientID);
    Client getClient(String productKey);
    User getUserByClientAndActivationKey(Client c, String activationKey) throws jakarta.persistence.NoResultException;
    void addUser(User u);
    void updateUser(User u);
    boolean iatExists(Client c, String iatName);
    boolean noRemainingIATs(Client client);
    long getFreeDiskSpaceKB(Client client);
    Long registerIAT(User user, String iatName, int testSizeKB);
    void registerEncryptionKeys(RSAKeyPair keyPair, Long testID);
    IAT getIAT(Long testID);
    void addUniqueResponseItem(UniqueResponseItem urItem);
    void addUniqueResponses(UniqueResponseItem uri, List<String> responses);
    void addUniqueResponse(UniqueResponse ur);
    int getResultDataFormat(IAT test);
    void creditAdministration(IAT test);
    boolean debitAdministration(IAT test);
    List<Long> getTestElems(IAT test);
    IAT updateIAT(IAT test);
    IAT getIATByAdminID(Long adminID);
    UniqueResponseItem findUniqueResponseItem(IAT test, String surveyName);
    int getElementPositionInTest(IAT test, String elemName);
    PartiallyEncryptedRSAKey getEncryptionKey(IAT test);
    boolean storeResultFragment(Long adminID, String testElem, byte[] encCipher, byte[] encIV, byte[] encData,
            boolean complete);
    List<TestResultFragment> getResultFragments(AdminTimer timer);
    ResultSet getResultSet(Long adminID);
    void updateResultSet(ResultSet rs);
    void deleteResultFragments(Long adminID);
    List<EncCodeLine> getEncryptedLines(TestSegment testSegment, CodeType codeType);
    void updateCodeLine(EncCodeLine line);
    List<EncCodeLine> getOrderedCodeLines(Long testSegmentID);
    IATList buildIATList(Long clientID);
    PartiallyEncryptedRSAKey getDataKey(Long clientID, String testName);
    long getNumResults(Long clientID, String testName);
    List<ResultSet> getResults(Long clientID, String testName);
    void deleteIATResults(Long clientID, String testName);
//    void deleteIAT(Long clientID, String testName);
    int getResultDataFormat(String testName, Long clientID);
    ServerReport retrieveClientReport(Long clientID);
    void addDynamicSpecifier(DynamicSpecifier dSpec, IAT test);
    void deleteAdminTimer(AdminTimer adminTimer);
    List<String> checkAdminTimers();
    long createAdminTimer(IAT test, String iatSessId);
    long createAdminTimer(IAT test, String iatSessId, byte[] token);
    boolean refreshAdminTimer(Long timerID);
    List<IAT> getIATs(Client c);
    long getClientDiskUsageKB(Client c);
    int getNumResultSets(IAT test);
    Map<AdminTimer, List<TestResultFragment>> getCompletedResultSets();
    void storeResultSet(IAT test, String tocData, byte[] resultData, byte[] testeeToken);
    void storeDeploymentSession(DeploymentSession ds);
    void storeEncryptionKey(PartiallyEncryptedRSAKey key);
    void updateEncryptionKey(PartiallyEncryptedRSAKey key);
    void deleteDeploymentSession(IAT test);
    byte[] getDeploymentPacketData(IAT test, PacketType packetType, int ordinal);
    void storeEncryptedCode(TestSegment ts, List<EncCodeLine> code);
    TestSegment getTestSegmentByID(Long id);
    void addTest(IAT test);
    void deleteIAT(Long testID);
    RSAKeyPair getRSAKeyPair(Client c, String iatName);
    List<String> getTestElemNames(IAT test);
    void addSpecifierValue(SpecifierValue sv);
    AdminTimer getAdminTimerById(Long id);
    UniqueResponse getUniqueResponse(UniqueResponseItem uri, String val);
    void updateUniqueResponse(UniqueResponse ur);
    RSAKeyData getRandomRSAData();
    List<SpecifierValue> getDynamicSpecifierValues(Long adminID);
    void addTestBackupFile(String fName, byte[] fileData, Long testID, Long deploymentId);
    void deleteTestBackupFiles(IAT test);
    void restoreTestBackup(IAT test) throws java.net.URISyntaxException, java.io.IOException;
    void reassociateResults(Long newTestID, Long oldTestID);
    void copyRSAKey(Long newTestID, Long oldTestID);
    DeploymentSession updateDeploymentSession(DeploymentSession ds);
    void updateClient(Client c);
    void deleteAdminTimers(Collection<AdminTimer> timers);
    int getNumRemainingIATs(Client c);
    AdminTimer updateTestAdmin(AdminTimer admin);
    void finalizeDeployment(Long dsId);
    String createOAuthToken(Client c, IAT test);
    int verifyAuthToken(String authToken, String clientId, String clientSecret);
    OAuthAccess performOAuth(String authToken);
    OAuthAccess validateAccessToken(String accessToken);
    OAuthAccess getOAuthAccess(long id);
    int getIatPositionInTest(IAT test);
    ResultSet getResultSetsWithToken(IAT test, byte[] token);
    void updateOAuthRegistration(Long testId, String url, boolean allowExplicitRedirects);
    void recordClientException(ClientExceptionReport ex);
    void setTokenDefinition(Long testId, TokenType tokType, String tokenName);
    int verifyRefreshToken(String refreshToken, String clientId, String clientSecret);
    String refreshOAuthAccessToken(String refreshToken);
    void cleanupExpiredOAuthTokens();
    List<IAT> getExpiredTestResults(long timeout);
    List<Client> getClientsWithCors();
    List<CorsOrigin> getCorsOriginsForClient(Client c);
    Iterable<CorsOrigin> getAllCorsOrigins();
    void deleteUser(User u);
    void deleteIAT(IAT test);
    User getUserByVerificationKey(String key);
    User getUserByClientAndEmail(Client c, String email) throws jakarta.persistence.NoResultException;
    boolean clientIdMatchesClientSecret(String id, String secret);
    boolean checkClientEmailExists(String email);
    TestSegment getTestSegment(IAT iat, String segmentName);
    List<TestSegment> getTestSegments(IAT iat);
    TestResource updateTestResource(TestResource resource);
    void addTestResource(TestResource tr);
    void addResourceReference(ResourceReference rr);
    List<ResourceReference> getResourceReferences(Client c, String iatName);
    List<TestResource> getTestResources(IAT test, ResourceType type);
    TestResource getTestResource(IAT test, Long resourceId);
    void deleteDeploymentSession(Long id);
    DeploymentSession getDeploymentSession(IAT test);
    DeploymentSession getDeploymentSession(CommunicationEvent ce);
    DeploymentSession getDeploymentSession(Long dsId);
    TestResource getTestImage(IAT test, int index);
}
