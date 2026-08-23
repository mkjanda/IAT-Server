package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.TransactionRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Handles DELETE_IAT and DELETE_IAT_DATA (clear results).
 * <p>
 * After a successful delete/clear we clear transient session attributes and
 * reply with {@code sendFinal} so the server closes the WebSocket. That prevents
 * the next Clear/Delete attempt from inheriting Authenticated / handshake state
 * from this session — the exact cause of the second-attempt hang.
 * </p>
 */
@Component
public class DeletionHandler implements TransactionHandler {

    private static final Logger logger = LogManager.getLogger(DeletionHandler.class);

    @Override
    public boolean supports(TransactionContext ctx) {
        if (!(ctx.inbound() instanceof TransactionRequest))
            return false;
        var transaction = (TransactionRequest) ctx.inbound();
        return transaction.getType() == TransactionType.DELETE_IAT
                || transaction.getType() == TransactionType.DELETE_IAT_DATA;
    }

    @Override
    public void handle(TransactionContext ctx) {
        if (!ctx.isAuthenticated()) {
            ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
            return;
        }
        var transaction = (TransactionRequest) ctx.inbound();
        var test = ctx.sessionState().repositoryManager()
                .getIATByNameAndClientID(transaction.getIATName(), ctx.client().getClientId());
        if (test == null) {
            ctx.reply().send(new TransactionRequest(TransactionType.NO_SUCH_IAT));
            return;
        }

        try {
            if (transaction.getType() == TransactionType.DELETE_IAT_DATA) {
                ctx.sessionState().repositoryManager()
                        .deleteIATResults(ctx.client().getClientId(), test.getTestName());
            } else if (transaction.getType() == TransactionType.DELETE_IAT) {
                ctx.sessionState().repositoryManager().deleteIAT(test);
            } else {
                ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
                return;
            }
        } catch (Exception ex) {
            logger.error("Delete/clear failed for IAT '{}' client {}",
                    transaction.getIATName(), ctx.client().getClientId(), ex);
            ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
            return;
        }

        // Wipe transaction-scoped attributes so nothing from this attempt can
        // leak into a subsequent operation if the socket is reused before close.

        // sendFinal closes the session after the SUCCESS frame — forces a brand-new
        // WebSocket (and empty attributes map) for the next Clear/Delete.
        ctx.reply().sendFinal(new TransactionRequest(TransactionType.SUCCESS));
    }
}
