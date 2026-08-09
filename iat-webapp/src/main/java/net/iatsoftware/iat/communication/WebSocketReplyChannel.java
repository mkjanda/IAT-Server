package net.iatsoftware.iat.communication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.socket.WebSocketSession;

import net.iatsoftware.iat.events.OutboundMessageEvent;
import net.iatsoftware.iat.events.OutboundFinalMessageEvent;
import net.iatsoftware.iat.messaging.Message;


public class WebSocketReplyChannel implements ReplyChannel {
    private final WebSocketSession session;
    private static final Logger logger = LogManager.getLogger(WebSocketReplyChannel.class);
    private final ApplicationEventPublisher publisher;

    public WebSocketReplyChannel(WebSocketSession session, ApplicationEventPublisher publisher) {
        this.session = session;
        this.publisher = publisher;
    }

    @Override
    public void send(Message msg) {
        publisher.publishEvent(new OutboundMessageEvent(session, msg));
    }

    @Override
    public void sendFinal(Message msg) {
        publisher.publishEvent(new OutboundFinalMessageEvent(session, msg));
    }

    @Override
    public void close() {
        try {
            session.close();
        } catch (java.io.IOException e) {
            logger.error(e);
        }
    }
}
