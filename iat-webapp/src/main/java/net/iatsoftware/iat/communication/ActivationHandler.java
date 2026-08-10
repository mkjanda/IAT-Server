package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.entities.User;
import net.iatsoftware.iat.generated.ActivationResult;
import net.iatsoftware.iat.messaging.ActivationRequest;
import net.iatsoftware.iat.messaging.ActivationResponse;
import net.iatsoftware.iat.services.EmailParameters;

import org.springframework.stereotype.Component;

@Component
public class ActivationHandler implements TransactionHandler {
    private final String logoClasspathLocation = "classpath:email/images/logo.png";

    public boolean supports(TransactionContext ctx) {
        return (ctx.inbound() instanceof ActivationRequest);    
    }

    public void handle(TransactionContext ctx) {
        User user = ctx.user();
        if (user == null) {
            user = new User((ActivationRequest)ctx.inbound(), 1, ctx.client());
            ctx.clientRepositoryManager().addUser(user);
        }
        EmailParameters emailParams = new EmailParameters(user.getEMail(), "IAT Software eMail Verification",
                "email/email-verification.html");
        emailParams.addParameter("user", user);
        emailParams.addInlineImage("logo", logoClasspathLocation, "image/png");
        try {
            ctx.mailService().sendEmail(emailParams);
        } catch (Exception e) {
            ctx.reply().sendFinal(new ActivationResponse(ctx.client().getContactFName() + " " + ctx.client().getContactLName(), ctx.client().getEmail(), ctx.client(), ActivationResult.SERVER_FAILURE));
            return;
        }
        ctx.reply().sendFinal(new ActivationResponse(ctx.client().getContactFName() + " " + ctx.client().getContactLName(), ctx.client().getEmail(), ctx.client(), ActivationResult.SUCCESS));
    }

}
