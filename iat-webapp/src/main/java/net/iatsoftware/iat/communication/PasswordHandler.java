package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.entities.PartiallyEncryptedRSAKey;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.TransactionRequest;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;

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
        if ((transaction.getType() == TransactionType.REQUEST_PASSWORD_VERIFICATION) ||
            (transaction.getType() == TransactionType.VERIFY_PASSWORD))
            return true;
        return false;
    }

    @Override
    public void handle(TransactionContext ctx) {
        var transaction = (TransactionRequest) ctx.inbound();
        if (transaction.getType() == TransactionType.REQUEST_PASSWORD_VERIFICATION) {
            var rsaKeys = ctx.sessionState().repositoryManager().getRSAKeyPair(ctx.client(), transaction.getIATName());
            if (rsaKeys == null) {
                ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
                return;
            }
            var rsaKey = rsaKeys.getDataKey();
            var modulus = new BigInteger(rsaKey.getModulus());
            var exponent = new BigInteger(rsaKey.getExponent());
            byte[] plaintext = new byte[32];
            random.nextBytes(plaintext);
            ctx.sessionState().setUnencryptedValue(new String(plaintext, java.nio.charset.StandardCharsets.UTF_8));
            byte[] zplaintext = new byte[plaintext.length + 1];
            zplaintext[0] = 0;
            System.arraycopy(plaintext, 0, zplaintext, 1, plaintext.length);
            var ciphertext = new BigInteger(zplaintext).modPow(exponent, modulus);
            rsaKey.setTestValue(new String(ciphertext.toByteArray(), java.nio.charset.StandardCharsets.UTF_8));
            ctx.reply().send(rsaKey);
        } else if (transaction.getType() == TransactionType.VERIFY_PASSWORD) {
            if (transaction.getEncryptedTestString().isNil()) 
                ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
            else {
                transaction.getEncryptedTestString().getValue().equals(ctx.sessionState().unencryptedValue());
                if (transaction.getEncryptedTestString().getValue().equals(ctx.sessionState().unencryptedValue())) {
                    ctx.reply().send(new TransactionRequest(TransactionType.SUCCESS));
                } else {
                    ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
                }
            }
        }
    }
    
}
