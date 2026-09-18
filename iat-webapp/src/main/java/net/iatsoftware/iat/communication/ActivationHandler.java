package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.controllers.EMailVerification;
import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.messaging.ActivationRequest;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.services.EmailParameters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

@Component
@PropertySource("classpath:email/email-config.properties")
public class ActivationHandler implements TransactionHandler {
    static final Random RANDOM = new Random();
    private static final Logger critical = LogManager.getLogger("critical");
    
    @Value("${mail.images.logo-classpath-location}")
    private String logoClasspathLocation;

    private String generateProductKey() {
        List<String> codeDigits = new ArrayList<>();
        List<String> chars = Arrays.asList("01234567890ABCDEFGHIJKLMNOPQRSTUVXYZ".split(""));
        for (int ctr = 0; ctr < 20; ctr++) 
            codeDigits.add(chars.get(RANDOM.nextInt(chars.size())));
        return codeDigits.stream().reduce("", (a, b) -> a + b, (a, b) -> a + b).toString();
    }

    private void handle(ActivationRequest request, TransactionContext ctx) {
        Client client = ctx.clientRepositoryManager().getClientByEmail(request.getEmail().toLowerCase());
        if (client != null)
        {
            var outTrans = new TransactionRequest(TransactionType.EMAIL_ALREADY_VERIFIED);
            outTrans.setProductKey(client.getProductKey());
            ctx.reply().send(outTrans);
            return;
        }
        client = new Client();
        client.setName(request.getName());
        client.setEmail(request.getEmail().toLowerCase());
        client.setActivationsRemaining(100);
        client.setNumIATsAlotted(1);
        client.setAdministrationsRemaining(5000);
        client.setDiskAlottmentMB(50);
        String productKey = generateProductKey();
        while (ctx.sessionState().repositoryManager().getClient(productKey) != null)
            productKey = generateProductKey();
        client.setProductKey(productKey);
        ctx.sessionState().setClient(client);
        var outTrans = new TransactionRequest(TransactionType.PRODUCT_KEY);
        outTrans.setProductKey(productKey);
        EMailVerification.contextCache.put(productKey, ctx);
        EmailParameters emailParams = new EmailParameters(request.getEmail(), "IAT Software eMail Verification",
                "email/email-verification.html");
        emailParams.addParameter("client", client);
        emailParams.addInlineImage("logo", logoClasspathLocation, "image/png");
        try {
            ctx.mailService().sendEmail(emailParams);
        } catch (Exception e) {
            critical.error("Could not send activation email.", e);
            ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
            return;
        }
        ctx.reply().send(outTrans);
    }

    @Override
    public boolean supports(TransactionContext ctx) {
        return (ctx.inbound() instanceof ActivationRequest);    
    }

    @Override
    public void handle(TransactionContext ctx) {
        if (ctx.inbound() instanceof ActivationRequest) {
            handle((ActivationRequest) ctx.inbound(), ctx);
            return;
        }
    }
}
