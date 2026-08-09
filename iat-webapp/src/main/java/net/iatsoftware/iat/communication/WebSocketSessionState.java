package net.iatsoftware.iat.communication;


import org.springframework.web.socket.WebSocketSession;

import net.iatsoftware.iat.configfile.ConfigFile;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.messaging.Handshake;
import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
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
    public PartiallyEncryptedRSAKey rsaKey() {
        return (PartiallyEncryptedRSAKey) getAttribute("RSAKey");
    }

    @Override
    public void setRSAKey(PartiallyEncryptedRSAKey key) {
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
}
