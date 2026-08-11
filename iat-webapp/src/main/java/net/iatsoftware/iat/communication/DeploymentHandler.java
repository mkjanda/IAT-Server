package net.iatsoftware.iat.communication;

import org.springframework.stereotype.Component;

import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.TransactionRequest;


@Component
public class DeploymentHandler implements TransactionHandler {
    
    @Override
    public boolean supports(TransactionContext ctx) {
        var msg = ctx.inbound();
        if (msg instanceof TransactionRequest) {
            if (((TransactionRequest) msg).getType() == TransactionType.DO_IAT_DEPLOY)
                return true;
        }
        return false;
    }

    @Override
    public void handle(TransactionContext ctx) {
        var transaction = (TransactionRequest) ctx.inbound();
        var test = ctx.sessionState().repositoryManager().getIATByNameAndClientID(transaction.getIATName(), ctx.client().getClientId());
        if (test != null) {
            ctx.reply().send(new TransactionRequest(TransactionType.IAT_EXISTS));
        } else  {
            var outTrans = new TransactionRequest(TransactionType.DO_IAT_DEPLOY);
            outTrans.setAuthToken(ctx.client().getAuthToken());
            ctx.reply().send(outTrans);
        }
    }
}
