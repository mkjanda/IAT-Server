package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.ServerReport;
import net.iatsoftware.iat.messaging.TransactionRequest;

import org.springframework.stereotype.Component;

@Component
public class ServerReportHandler implements TransactionHandler {

    @Override
    public boolean supports(TransactionContext ctx) {
        if (!(ctx.inbound() instanceof TransactionRequest))
            return false;
        var transaction = (TransactionRequest) ctx.inbound();
        return transaction.getType() == (TransactionType.REQUEST_SERVER_REPORT);
    }

    @Override
    public void handle(TransactionContext ctx) {
        var report = new ServerReport();
        report.setProductKey(ctx.client().getProductKey());
        report.load(ctx.client().getClientId(), ctx.sessionState().repositoryManager());
        ctx.reply().send(report);
    }
}
