package net.iatsoftware.iat.communication;

import net.iatsoftware.iat.messaging.Message;

public interface ReplyChannel {
    void send(Message msg);               // non-final
    void sendFinal(Message msg);          // last transmission
    void close();                         // unregister + close
}