package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.controllers.ResultRetrievalController;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.TransactionRequest;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;


@Component
public class ResultDataHandler implements TransactionHandler {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getEncoder();
    @Override
    public boolean supports(TransactionContext ctx) {
        if (!(ctx.inbound() instanceof TransactionRequest))
            return false;
        var transaction = (TransactionRequest) ctx.inbound();
        return transaction.getType() == net.iatsoftware.iat.generated.TransactionType.REQUEST_ITEM_SLIDE_MANIFEST ||
                transaction.getType() == net.iatsoftware.iat.generated.TransactionType.REQUEST_FILE_MANIFEST ||
                transaction.getType() == net.iatsoftware.iat.generated.TransactionType.REQUEST_RESULTS;
    }

    @Override
    public void handle(TransactionContext ctx) {
        var transaction = (TransactionRequest) ctx.inbound();
        var test = ctx.sessionState().repositoryManager().getIATByNameAndClientID(transaction.getIATName(),
                ctx.client().getClientId());
        if (test == null) {
            ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
            return;
        }
        try {
            switch (transaction.getType()) {
                case TransactionType.REQUEST_ITEM_SLIDE_MANIFEST:
                    var manifest = ctx.sessionState().repositoryManager().getItemSlideManifest(test);
                    ctx.reply().send(manifest);
                    break;

                case TransactionType.REQUEST_RESULTS:    
                    Long token = -1L;
                    if (ResultRetrievalController.authTokenCache.getIfPresent(ctx.client().getProductKey()) == null) {
                        token = System.currentTimeMillis();
                        ResultRetrievalController.authTokenCache.put(ctx.client().getProductKey(), token);
                    } else {
                        token = ResultRetrievalController.authTokenCache.getIfPresent(ctx.client().getProductKey());
                    } 
                    var outTrans = new TransactionRequest(TransactionType.AUTH_TOKEN);
                    outTrans.setAuthToken(ResultRetrievalController.authTokenCache.getIfPresent(ctx.client().getProductKey()));
                    ctx.reply().send(outTrans);
                    break;

                default:
                    ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
                    break;
            }
        } catch (Exception ex) {
            ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
        }
    }
}
