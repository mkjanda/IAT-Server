package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.messaging.Handshake;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.generated.TransactionType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

    
@Component
public class ConnectionHandler implements TransactionHandler {
    
    private final SecureRandom random = new SecureRandom();
    private final Base64.Encoder b64Encoder = Base64.getEncoder();
    private final static byte[] aesKey = new byte[] {
        (byte)0x2c, (byte)0x5b, (byte)0xd5, (byte)0x54, (byte)0x33, (byte)0xa8, (byte)0x8a, (byte)0x1e,
        (byte)0xff, (byte)0xe7, (byte)0x1f, (byte)0x36, (byte)0xa7, (byte)0xe0, (byte)0xe4, (byte)0xae,
        (byte)0x76, (byte)0x78, (byte)0x12, (byte)0xb3, (byte)0x23, (byte)0x64, (byte)0x89, (byte)0x62,
        (byte)0xdc, (byte)0xfc, (byte)0x79, (byte)0x53, (byte)0x41, (byte)0x51, (byte)0x6a, (byte)0xeb
    };

    private static final Logger logger = LogManager.getLogger(ConnectionHandler.class);

    @Override
    public boolean supports(TransactionContext ctx) {
        var msg = ctx.inbound();
        if (msg instanceof TransactionRequest) {
            TransactionRequest trans = (TransactionRequest) msg;
            if (trans.getType() == TransactionType.REQUEST_CONNECTION)
                return true;
        }
        if (msg instanceof Handshake)
            return true;
        return false;
    }


    @Override
    public void handle(TransactionContext ctx) {
        var msg = ctx.inbound();
        var sessionState = ctx.sessionState();  
        var channel = ctx.reply();
        try {
        if (msg instanceof TransactionRequest) {
            TransactionRequest trans = (TransactionRequest) msg;
            if (trans.getType() == TransactionType.REQUEST_CONNECTION) {
                byte[] challenge = new byte[32];
                random.nextBytes(challenge);
                var text = b64Encoder.encodeToString(challenge);
                sessionState.setUnencryptedValue(text);
                var handshake = new Handshake();
                byte[] iv = new byte[12];
                random.nextBytes(iv);
                var key = new SecretKeySpec(aesKey, "AES");
                GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
                var cipher = Cipher.getInstance("AES/GCM/NoPadding");
                cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);
                byte[] ciphertext = cipher.doFinal(challenge);
                handshake.setValue(b64Encoder.encodeToString(ciphertext));
                channel.send(handshake);
            }
        } else if (msg instanceof Handshake) {
            Handshake hs = (Handshake) msg;
            if (hs.getValue().equals(sessionState.unencryptedValue())) {
                channel.send(new TransactionRequest(TransactionType.REQUEST_TRANSMISSION));
                return;
            }
                channel.close();
        }
        } catch (Exception e) {
            logger.error("Error handling message", e);  
        }
    }
}
