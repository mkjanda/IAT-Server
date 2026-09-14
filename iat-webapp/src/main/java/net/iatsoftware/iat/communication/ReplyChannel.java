package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.messaging.MessageBase;

public interface ReplyChannel {
    void send(MessageBase msg);               // non-final
    void sendFinal(MessageBase msg);          // last transmission
    void close();                         // unregister + close
}