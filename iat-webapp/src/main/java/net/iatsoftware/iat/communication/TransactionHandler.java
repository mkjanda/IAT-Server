package net.iatsoftware.iat.communication;

public interface TransactionHandler {
    public boolean supports(TransactionContext ctx);
    void handle(TransactionContext ctx);
}
