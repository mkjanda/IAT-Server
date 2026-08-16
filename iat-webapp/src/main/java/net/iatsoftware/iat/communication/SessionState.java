// net.iatsoftware.iat.services.transaction.SessionState
package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.configfile.ConfigFile;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.generated.ManifestType;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.Handshake;
import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.repositories.ClientRepositoryManager;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.services.MailService;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

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

    Marshaller marshaller();
    void setMarshaller(Marshaller marshaller);

    Unmarshaller unmarshaller();
    void setUnmarshaller(Unmarshaller unmarshaller);

    TransactionType lastTransactionType();
    void setLastTransactionType(TransactionType type);

    ManifestType wantedManifestType();
    void setWantedManifestType(ManifestType type);

    Manifest fileManifest();
    void setFileManifest(Manifest manifest);

    Manifest itemSlideManifest();
    void setItemSlideManifest(Manifest manifest);

    IAT replacementTest();
    void setReplacementTest(IAT test);
}