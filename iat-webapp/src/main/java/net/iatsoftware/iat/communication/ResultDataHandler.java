package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.resultdata.ResultSetDescriptor;
import net.iatsoftware.iat.messaging.TransactionRequest;

import org.springframework.stereotype.Component;

@Component
public class ResultDataHandler implements TransactionHandler {

    @Override
    public boolean supports(TransactionContext ctx) {
        if (!(ctx.inbound() instanceof TransactionRequest))
            return false;
        var transaction = (TransactionRequest) ctx.inbound();
        return transaction.getType() == (net.iatsoftware.iat.generated.TransactionType.REQUEST_ITEM_SLIDE_MANIFEST) ||
                transaction.getType() == (net.iatsoftware.iat.generated.TransactionType.REQUEST_ENCRYPTION_KEY) ||
                transaction.getType() == (net.iatsoftware.iat.generated.TransactionType.REQUEST_RESULTS);
    }

    @Override
    public void handle(TransactionContext ctx) {
        if (!ctx.isAuthenticated()) {
            ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
            return;
        }
        var transaction = (TransactionRequest) ctx.inbound();
        var outTrans = new TransactionRequest();
        var test = ctx.sessionState().repositoryManager().getIATByNameAndClientID(transaction.getIATName(), ctx.client().getClientId());
                if (test == null) {
                    ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
                    return;
                }
        switch (transaction.getType()) {
            case TransactionType.REQUEST_ITEM_SLIDE_MANIFEST: 
                var manifest = ctx.sessionState().repositoryManager().getItemSlideManifest(test);
                ctx.reply().send(manifest);
                break;
            

            case TransactionType.REQUEST_RESULT_DESCRIPTOR:
                var descriptor = new ResultSetDescriptor();
                try {
                    descriptor.load(ctx.client().getClientId(), transaction.getIATName());
                    ctx.reply().send(descriptor);
                } catch (Exception ex) {
                    ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
                }
                break;

            case TransactionType.REQUEST_RESULTS:
                outTrans.setType(TransactionType.RESULTS_READY);
                outTrans.setDownloadKey(ctx.client().getAuthToken());
                ctx.reply().send(outTrans);
                break;

            
            default:
                ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
                break;
        }
    }
}
