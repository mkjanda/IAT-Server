package net.iatsoftware.iat.config;
/*
import 
import java.util.concurrent.ConcurrentHashMap;

public class DataTransactionWebSocketHandler {
    

        private final ConcurrentHashMap<String, List<String>> partialTransmissions = new ConcurrentHashMap<>();

        @Inject
        Unmarshaller unmarshaller;
        @Inject
        WebSocketService webSocketService;
        @Inject
        ApplicationEventPublisher publisher;

        @Override
        public void afterConnectionEstablished(WebSocketSession sess) {
            logger.error(sess);
            logger.error(sess.getUri());
            logger.error(webSocketService);
            if (sess.getUri().getPath().equals("/IAT/DataTransaction"))
                webSocketService.registerWebSocket(sess);
        }

        @Override
        public boolean supportsPartialMessages() {
            return true;
        }

        protected Envelope unmarshalTransmission(String trans) throws java.io.IOException {
            StringReader sReader = new StringReader(trans);
            StreamSource sSource = new StreamSource(sReader);
            return (Envelope) unmarshaller.unmarshal(sSource);
        }

        @Override
        protected void handleTextMessage(WebSocketSession sess, TextMessage msg) {
            try {
                
                if (!msg.isLast()) {
                    if (partialTransmissions.containsKey(sess.getId())) {
                        partialTransmissions.get(sess.getId()).add(msg.getPayload());
                    } else {
                        List<String> trans = new ArrayList<>();
                        trans.add(msg.getPayload());
                        partialTransmissions.put(sess.getId(), trans);
                    }
                } else if (!partialTransmissions.containsKey(sess.getId())) {
                    publisher.publishEvent(
                            new WebSocketDataReceived(sess.getId(), unmarshalTransmission(msg.getPayload())));
                } else {
                    List<String> fullTrans = partialTransmissions.get(sess.getId());
                    fullTrans.add(msg.getPayload());
                    if (sess.getUri().getPath().equals("/IAT/DataTransaction"))
                        publisher.publishEvent(new WebSocketDataReceived(sess.getId(),
                            unmarshalTransmission(fullTrans.stream().reduce("", (s1, s2) -> s1.concat(s2)))));
                    fullTrans.clear();
                }
            } catch (Exception ex) {
                logger.error("Error receiving web socket transmission", ex);
            }
        }

        @Override
        public void afterConnectionClosed(WebSocketSession sess, CloseStatus status) {
            if (sess.getUri().getPath().equals("/IAT/DataTransaction"))
                webSocketService.unregisterWebSocket(sess.getId());
            partialTransmissions.remove(sess.getId());
        }

}
*/