package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.File;
import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.messaging.TransactionRequest;

import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.security.SecureRandom;

@Component
public class ItemSlideHandler implements TransactionHandler {
    private final SecureRandom random = new SecureRandom();
    private final Base64.Encoder encoder = Base64.getEncoder();

    @Override
    public boolean supports(TransactionContext ctx) {
        if (!(ctx.inbound() instanceof TransactionRequest))
            return false;
        var transaction = (TransactionRequest) ctx.inbound();
        return transaction.getType() == (net.iatsoftware.iat.generated.TransactionType.REQUEST_ITEM_SLIDE_MANIFEST) ||
               transaction.getType() == (net.iatsoftware.iat.generated.TransactionType.REQUEST_ITEM_SLIDES);
    }

    @Override
    public void handle(TransactionContext ctx) {
        if (!ctx.isAuthenticated()) {
            ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
            return;
        }
        var transaction = (TransactionRequest) ctx.inbound();
        if (transaction.getType() == TransactionType.REQUEST_ITEM_SLIDE_MANIFEST) {
            var test = ctx.sessionState().repositoryManager().getIATByNameAndClientID(transaction.getIATName(), ctx.client().getClientId());
            if (test == null) {
                ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
                return;
            }
            List<byte[]> itemSlides = ctx.sessionState().repositoryManager().getItemSlides(test);
            if (itemSlides == null) {
                ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
                return;
            }
            Manifest manifest = new Manifest();
            for (int i = 0; i < itemSlides.size(); i++) {
                File f = new File();
                f.setName("ItemSlide" + (i + 1));
                f.setSize(itemSlides.get(i).length);
                manifest.getFiles().add(f);
            }
            byte[] key = new byte[32];
            random.nextBytes(key);
            test.setItemSlideDownloadKey(encoder.encodeToString(key));
            ctx.sessionState().repositoryManager().updateIAT(test);
            ctx.reply().send(manifest);
        } else if (transaction.getType() == TransactionType.REQUEST_ITEM_SLIDES) {
            var test = ctx.sessionState().repositoryManager().getIATByNameAndClientID(transaction.getIATName(), ctx.client().getClientId());
            if (test == null) { 
                ctx.reply().send(new TransactionRequest(TransactionType.FAIL));
                return;
            }
            var outTrans = new TransactionRequest(TransactionType.ITEM_SLIDE_DOWNLOAD_READY);
            outTrans.setDownloadKey(test.getItemSlideDownloadKey());
            ctx.reply().send(outTrans);
        }
    }
}
