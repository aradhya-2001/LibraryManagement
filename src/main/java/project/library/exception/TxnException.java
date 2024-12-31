package project.library.exception;

public class TxnException extends RuntimeException {
    public TxnException(String msg) {
        super(msg);
    }
}
