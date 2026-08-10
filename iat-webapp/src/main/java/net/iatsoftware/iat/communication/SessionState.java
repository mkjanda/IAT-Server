// net.iatsoftware.iat.services.transaction.SessionState
package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.configfile.ConfigFile;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.messaging.Handshake;
import net.iatsoftware.iat.repositories.ClientRepositoryManager;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.services.MailService;

public interface SessionState {
    Client client();
    void setClient(Client c);

    Handshake pendingHandshake();
    void setPendingHandshake(Handshake h);

    boolean isHandsShaken();
    void setHandsShaken(boolean value);

    String iatName();
    void setIatName(String name);

    IAT iat();
    void setIat(IAT iat);

    long deploymentId();
    void setDeploymentId(long id);

    String unencryptedValue();
    void setUnencryptedValue(String value);

    ConfigFile configFile();
    void setConfigFile(ConfigFile configFile);

    ClientRepositoryManager clientRepositoryManager();
    void setClientRepositoryManager(ClientRepositoryManager clientRepositoryManager);

    IATRepositoryManager repositoryManager();
    void setRepositoryManager(IATRepositoryManager repositoryManager);

    MailService mailService();
    void setMailService(MailService mailService);

    PartiallyEncryptedRSAKey rsaKey();
    void setRSAKey(PartiallyEncryptedRSAKey key);
    
    boolean isAuthenticated();
    void setAuthenticated(boolean value);
}