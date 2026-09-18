package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.services.EmailParameters;

import org.springframework.stereotype.Component;

@Component
public class EmailVerification implements TransactionHandler {
    @Override
    public boolean supports(TransactionContext ctx) {
        if (!(ctx.inbound() instanceof TransactionRequest))
            return false;
        var transaction = (TransactionRequest) ctx.inbound();
        TransactionType type = transaction.getType();
        if ((type == TransactionType.REQUEST_NEW_VERIFICATION_EMAIL) || 
            (type == TransactionType.REQUEST_EMAIL_VERIFICATION))
            return true;
        return false;
    }

    @Override
    public void handle(TransactionContext ctx) {
        var transaction = (TransactionRequest) ctx.inbound();
        if (transaction.getType() == TransactionType.REQUEST_NEW_VERIFICATION_EMAIL) {
            EmailParameters emailParams = new EmailParameters(ctx.client().getEmail(), "IAT Software eMail Verification",
                    "email/email-verification.html");
            emailParams.addParameter("client", ctx.client());
            emailParams.addInlineImage("logo", "classpath:email/images/logo.png", "image/png");                    
            try {
                ctx.mailService().sendEmail(emailParams);
            } catch (Exception e) {
                ctx.reply().sendFinal(new TransactionRequest(TransactionType.SERVER_ERROR));
                return;
            }
            ctx.reply().send(new TransactionRequest(TransactionType.SUCCESS));
        }
    }
}
