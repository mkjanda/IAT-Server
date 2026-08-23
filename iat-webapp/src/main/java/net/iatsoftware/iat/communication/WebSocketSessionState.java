package net.iatsoftware.iat.communication;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.web.socket.WebSocketSession;

import net.iatsoftware.iat.configfile.ConfigFile;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.messaging.Handshake;
import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.entities.EncryptedRSAKey;
import net.iatsoftware.iat.generated.ManifestType;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.repositories.ClientRepositoryManager;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.services.MailService;


public final class WebSocketSessionState implements SessionState {

    private final WebSocketSession session; 

    public WebSocketSessionState(WebSocketSession session) {
        this.session = session;
    }

    private void setAttribute(String key, Object value) {
        session.getAttributes().put(key, value);
    }

    private Object getAttribute(String key) {
        return session.getAttributes().get(key);
    }

    @Override
    public Client client() {
        return (Client) getAttribute("Client");
    }

    @Override
    public void setClient(Client c) {
        setAttribute("Client", c);
    }

    @Override
    public Handshake pendingHandshake() {
        return (Handshake) getAttribute("Handshake");
    }

    @Override
    public void setPendingHandshake(Handshake h) {
        setAttribute("Handshake", h);
    }

    @Override
    public boolean isHandsShaken() {
        return Boolean.TRUE.equals(getAttribute("HandsShaken"));
    }

    @Override
    public void setHandsShaken(boolean value) {
        setAttribute("HandsShaken", value);
    }

    @Override
    public String iatName() {
        return (String) getAttribute("IATName");
    }

    @Override
    public void setIatName(String name) {
        setAttribute("IATName", name);
    }

    @Override
    public EncryptedRSAKey rsaKey() {
        return (EncryptedRSAKey) getAttribute("RSAKey");
    }

    @Override
    public void setRSAKey(EncryptedRSAKey key) {
        setAttribute("RSAKey", key);
    }

    @Override
    public String unencryptedValue() {
        return (String) getAttribute("UnencryptedValue");
    }

    @Override
    public void setUnencryptedValue(String value) {
        setAttribute("UnencryptedValue", value);
    }       

    @Override
    public long deploymentId() {
        return (Long) getAttribute("DeploymentId");
    }   

    @Override
    public void setDeploymentId(long id) {
        setAttribute("DeploymentId", id);
    }   

    @Override
    public void setConfigFile(ConfigFile configFile) {
        setAttribute("ConfigFile", configFile);
    }

    @Override
    public ConfigFile configFile() {
        return (ConfigFile) getAttribute("ConfigFile");
    }

    @Override
    public IAT iat() {
        return (IAT) getAttribute("IAT");
    }

    @Override
    public void setIat(IAT iat) {
        setAttribute("IAT", iat);
    }   
    @Override
    public ClientRepositoryManager clientRepositoryManager() {
        return (ClientRepositoryManager) getAttribute("ClientRepositoryManager");
    }

    @Override
    public void setClientRepositoryManager(ClientRepositoryManager clientRepositoryManager) {
        setAttribute("ClientRepositoryManager", clientRepositoryManager);
    }

    @Override
    public IATRepositoryManager repositoryManager() {
        return (IATRepositoryManager) getAttribute("RepositoryManager");
    }

    @Override
    public void setRepositoryManager(IATRepositoryManager repositoryManager) {
        setAttribute("RepositoryManager", repositoryManager);
    }

    @Override
    public MailService mailService() {
        return (MailService) getAttribute("MailService");
    }

    @Override
    public void setMailService(MailService mailService) {
        setAttribute("MailService", mailService);
    }

    @Override
    public boolean isAuthenticated() {
        return Boolean.TRUE.equals(getAttribute("Authenticated"));
    }
    @Override
    public void setAuthenticated(boolean value) {
        setAttribute("Authenticated", value);
    }   

    @Override
    public Marshaller marshaller() {
        return (Marshaller) getAttribute("Marshaller");
    }       

    @Override
    public void setMarshaller(Marshaller marshaller) {
        setAttribute("Marshaller", marshaller);
    }

    @Override
    public Unmarshaller unmarshaller() {
        return (Unmarshaller) getAttribute("Unmarshaller");
    }

    @Override
    public void setUnmarshaller(Unmarshaller unmarshaller) {
        setAttribute("Unmarshaller", unmarshaller);
    }

    @Override 
    public TransactionType lastTransactionType() {
        return (TransactionType) getAttribute("LastTransactionType");
    }

    @Override
    public void setLastTransactionType(TransactionType type) {
        setAttribute("LastTransactionType", type);
    }   

    @Override
    public ManifestType wantedManifestType() {
        return (ManifestType) getAttribute("WantedManifestType");
    }
    
    @Override
    public void setWantedManifestType(ManifestType type) {
        setAttribute("WantedManifestType", type);
    }   

    @Override
    public Manifest fileManifest() {
        return (Manifest) getAttribute("FileManifest");
    }

    @Override
    public void setFileManifest(Manifest manifest) {
        setAttribute("FileManifest", manifest);
    }
    @Override
    public Manifest itemSlideManifest() {
        return (Manifest) getAttribute("SlideManifest");
    }

    @Override
    public void setItemSlideManifest(Manifest manifest) {
        setAttribute("SlideManifest", manifest);
    }

    @Override
    public IAT replacementTest() {
        return (IAT) getAttribute("ReplacementTest");
    }

    @Override
    public void setReplacementTest(IAT test) {
        setAttribute("ReplacementTest", test);
    }
    
    @Override
    public String authToken() {
        return (String) getAttribute("AuthToken");
    }

    @Override
    public void setAuthToken(String authToken) {
        setAttribute("AuthToken", authToken);
    }

    /**
     * Removes every key that belongs to a single logical transaction. Leaves
     * Client, repository managers, marshaller/unmarshaller, and MailService so
     * the next message on this session does not have to re-inject infrastructure.
     * Must be called at the start of REQUEST_CONNECTION (and after terminal
     * operations such as delete/clear) so a second attempt never sees leftover
     * Authenticated / handshake / auth-token state from the previous attempt.
     */
    @Override
    public void clearTransientState() {
        var attrs = session.getAttributes();
        attrs.remove("Handshake");
        attrs.remove("HandsShaken");
        attrs.remove("Client");
        attrs.remove("IATName");
        attrs.remove("RSAKey");
        attrs.remove("UnencryptedValue");
        attrs.remove("DeploymentId");
        attrs.remove("ConfigFile");
        attrs.remove("IAT");
        attrs.remove("Authenticated");
        attrs.remove("LastTransactionType");
        attrs.remove("WantedManifestType");
        attrs.remove("FileManifest");
        attrs.remove("SlideManifest");
        attrs.remove("ReplacementTest");
        attrs.remove("AuthToken");
    }
}
