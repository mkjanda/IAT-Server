package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.entities.User;
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
        if ((type == TransactionType.REQUEST_NEW_VERIFICATION_E_MAIL) || 
            (type == TransactionType.REQUEST_E_MAIL_VERIFICATION))
            return true;
        return false;
    }

    @Override
    public void handle(TransactionContext ctx) {
        var transaction = (TransactionRequest) ctx.inbound();
        if (transaction.getType() == TransactionType.REQUEST_NEW_VERIFICATION_E_MAIL) {
            EmailParameters emailParams = new EmailParameters(ctx.user().getEMail(), "IAT Software eMail Verification",
                    "email/email-verification.html");
            emailParams.addParameter("user", ctx.user());
            emailParams.addInlineImage("logo", "classpath:email/images/logo.png", "image/png");                    
            try {
                ctx.mailService().sendEmail(emailParams);
            } catch (Exception e) {
                ctx.reply().sendFinal(new TransactionRequest(TransactionType.SERVER_ERROR));
                return;
            }
            ctx.reply().sendFinal(new TransactionRequest(TransactionType.SUCCESS));
        } else if (transaction.getType() == TransactionType.REQUEST_E_MAIL_VERIFICATION) {
            User user = ctx.user();
            if (user.isEMailVerified()) {
                transaction  = new TransactionRequest(TransactionType.SUCCESS);
                transaction.setActivationKey(user.getActivationKey());
                ctx.reply().sendFinal(transaction);
            } else {
                ctx.reply().sendFinal(new TransactionRequest(TransactionType.FAIL));
            }
        }
    }
}
