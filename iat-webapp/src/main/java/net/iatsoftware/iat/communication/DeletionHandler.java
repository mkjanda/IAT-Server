package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.TransactionRequest;

import org.springframework.stereotype.Component;


@Component
public class DeletionHandler implements TransactionHandler {

    @Override
    public boolean supports(TransactionContext ctx) {
        if (!(ctx.inbound() instanceof TransactionRequest))
            return false;   
        var transaction = (TransactionRequest) ctx.inbound();
        return (transaction.getType() == TransactionType.DELETE_IAT) ||
                (transaction.getType() == TransactionType.DELETE_IAT_DATA);
    }

    @Override
    public void handle(TransactionContext ctx) {
        if (!ctx.isAuthenticated()) {
            ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
            return;
        }
        var transaction = (TransactionRequest) ctx.inbound();
        var test = ctx.sessionState().repositoryManager().getIATByNameAndClientID(transaction.getIATName(), ctx.client().getClientId());
        if (test == null) {
            ctx.reply().send(new TransactionRequest(TransactionType.NO_SUCH_IAT));
            return;
        }
        if (transaction.getType() == TransactionType.DELETE_IAT_DATA) {
            ctx.sessionState().repositoryManager().deleteIATResults(ctx.client().getClientId(), test.getTestName());
            ctx.reply().send(new TransactionRequest(TransactionType.SUCCESS));
            return;
        } else if (transaction.getType() == TransactionType.DELETE_IAT) {
            ctx.sessionState().repositoryManager().deleteIAT(test);
            ctx.reply().send(new TransactionRequest(TransactionType.SUCCESS));
            return;
        }
    }
}
