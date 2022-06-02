package net.iatsoftware.iat.messaging;


public class ServerException extends java.lang.Exception {
    private ServerExceptionMessage exceptionMessage = null;

    public ServerException(ServerExceptionMessage msg) {
        super(msg.getExceptionMessage());
        exceptionMessage = msg;
    }

    public ServerExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }
    
}
