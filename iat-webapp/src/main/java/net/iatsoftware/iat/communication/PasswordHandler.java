package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.entities.EncryptedRSAKey;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.TransactionRequest;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.time.Duration;
import java.security.SecureRandom;

@Component
public class PasswordHandler implements TransactionHandler {
    static final SecureRandom random = new SecureRandom();
    static final Base64.Encoder b64Encoder = Base64.getEncoder();
    static public final Cache<String, String> authTokenCache = Caffeine.newBuilder()
    .maximumSize(10_000)
    .expireAfterWrite(Duration.ofMinutes(10))
    .build();

    @Override
    public boolean supports(TransactionContext ctx) {
        // Must guard the cast — Handshake and other non-TransactionRequest messages
        // also flow through the handler list. An unchecked cast aborts the whole
        // forEach and leaves the client hanging with no response.
        if (!(ctx.inbound() instanceof TransactionRequest))
            return false;
        var transaction = (TransactionRequest) ctx.inbound();
        return transaction.getType() == TransactionType.REQUEST_RSA_KEY
                || transaction.getType() == TransactionType.PASSWORD_VALID;
    }

    @Override
    public void handle(TransactionContext ctx) {
        var transaction = (TransactionRequest) ctx.inbound();
        if (transaction.getType() == TransactionType.REQUEST_RSA_KEY) {
            EncryptedRSAKey key = ctx.sessionState().repositoryManager().getRSAKey(
                    ctx.client().getClientId(), transaction.getIATName());
            ctx.reply().send(key);
        } else {
            var bytes = new byte[24];
            random.nextBytes(bytes);
            String authToken = b64Encoder.encodeToString(bytes);
            authTokenCache.put(ctx.client().getProductKey(), authToken);
            var response = new TransactionRequest(TransactionType.AUTH_TOKEN);
            response.setAuthToken(authToken);
            ctx.sessionState().setAuthenticated(true);
            ctx.reply().send(response);
        }
    }
}
