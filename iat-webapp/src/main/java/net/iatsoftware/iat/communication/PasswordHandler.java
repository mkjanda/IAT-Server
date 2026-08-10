package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.TransactionRequest;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class PasswordHandler implements TransactionHandler {
    static final SecureRandom random = new SecureRandom();
    static final Base64.Encoder b64Encoder = Base64.getEncoder();

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
            String token = ctx.clientRepositoryManager().generateAuthToken(ctx.client(), System.currentTimeMillis(), 3_000_000L);
            var outTransaction = new TransactionRequest(TransactionType.PASSWORD_VALID);
            outTransaction.setAuthToken(token);
            ctx.reply().send(outTransaction);
        } else {
            ctx.reply().send(new TransactionRequest(TransactionType.PASSWORD_INVALID));
        }
    }
}
