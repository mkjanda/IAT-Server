package net.iatsoftware.iat.events;

import net.iatsoftware.iat.messaging.Message;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.WebSocketSession;

public class OutboundMessageEvent extends ApplicationEvent {
    private final WebSocketSession session;
    private final Message message;
    
    public OutboundMessageEvent(WebSocketSession sess, Message msg) {
        super(sess);
        this.session = sess;
        this.message = msg;
    }
    
    public WebSocketSession session() {
        return this.session;
    }
    
    public Message message() {
        return this.message;
    }

    public boolean finalMessage() {
        return false;
    }
    
}
