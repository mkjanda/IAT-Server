package net.iatsoftware.iat.events;

import org.springframework.web.socket.WebSocketSession;
import net.iatsoftware.iat.messaging.Message;

public class OutboundFinalMessageEvent extends OutboundMessageEvent {
    public OutboundFinalMessageEvent(WebSocketSession sess, Message msg) {
        super(sess, msg);
    }
    
    @Override
    public boolean finalMessage() {
        return true;
    }
}
