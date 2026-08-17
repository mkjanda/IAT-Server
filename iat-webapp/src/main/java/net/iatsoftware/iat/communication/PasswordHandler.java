package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.TransactionRequest;
import java.time.Duration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class PasswordHandler implements TransactionHandler {
    static final SecureRandom random = new SecureRandom();
    static final Base64.Encoder b64Encoder = Base64.getEncoder();
    static public final Cache<String, TransactionContext> authTokenCache = Caffeine.newBuilder()
    .maximumSize(10_000)
    .expireAfterWrite(Duration.ofMinutes(10))
    .build();

    @Override
    public boolean supports(TransactionContext ctx) {
        if (ctx.inbound() instanceof PartiallyEncryptedRSAKey)
            return true;
        if (!(ctx.inbound() instanceof TransactionRequest))
            return false;
        var transaction = (TransactionRequest) ctx.inbound();
        if (transaction.getIATName() == null)
            return false;
        if (transaction.getType() == TransactionType.VERIFY_PASSWORD) 
            return true;
        return false;
    }

    @Override
    public void handle(TransactionContext ctx) {
        var transaction = (TransactionRequest) ctx.inbound();
        ctx.sessionState().setIatName(transaction.getIATName());
        var key = ctx.sessionState().repositoryManager().getRSAKeyPair(ctx.client(), transaction.getIATName()).getDataKey();

        if (key.testPassword(transaction.getTestString())) {
            ctx.sessionState().setAuthenticated(true);
            byte[] tokenBytes = new byte[32];
            random.nextBytes(tokenBytes);
            String tokenString = b64Encoder.encodeToString(tokenBytes);
            authTokenCache.put(tokenString, ctx);
            ctx.sessionState().setAuthToken(tokenString);
            var outTransaction = new TransactionRequest(TransactionType.PASSWORD_VALID);
            outTransaction.setActivationKey(tokenString);
            ctx.reply().send(outTransaction);
        } else {
            ctx.reply().send(new TransactionRequest(TransactionType.PASSWORD_INVALID));
        }
    }
}
