// net.iatsoftware.iat.services.transaction.TransactionContext
package net.iatsoftware.iat.communication;

import org.springframework.web.socket.WebSocketSession;

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.User;
import net.iatsoftware.iat.messaging.Message;
import net.iatsoftware.iat.repositories.ClientRepositoryManager;
import net.iatsoftware.iat.services.MailService;

public final class TransactionContext {
    private final WebSocketSession session;;
    private final Message inbound;
    private final ReplyChannel reply;
    private final SessionState sessionState;

    public TransactionContext(WebSocketSession session, Message inbound,
                              ReplyChannel reply, SessionState sessionState) {
        this.inbound = inbound;
        this.reply = reply;
        this.session = session;
        this.sessionState = sessionState;    
    }

    public String sessionId() { return session.getId(); }
    public Message inbound() { return inbound; }
    public ReplyChannel reply() { return reply; }
    public SessionState sessionState() { return sessionState; }

    // convenience
    public Client client() { return sessionState.client(); }
    public User user() { return sessionState.client().getUsers().get(0); }
    public IAT test() { return sessionState.test(); }
    public ClientRepositoryManager clientRepositoryManager() { return sessionState.clientRepositoryManager(); }
    public MailService mailService() { return sessionState.mailService(); }
    public boolean isAuthenticated() { return sessionState.isAuthenticated(); }
    public long deploymentId() { return sessionState.deploymentId(); }
}