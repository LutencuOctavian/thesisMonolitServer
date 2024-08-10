package dom.com.thesismonolitserver.exceptions;

public class InvalidCodeException extends RuntimeException {
    public InvalidCodeException(String msg) {
        super(msg);
    }

    public InvalidCodeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
