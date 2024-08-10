package dom.com.thesismonolitserver.exceptions;

public class PrivilegeException extends RuntimeException {
    public PrivilegeException() {
        super();
    }

    public PrivilegeException(String message) {
        super(message);
    }

    public PrivilegeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrivilegeException(Throwable cause) {
        super(cause);
    }
}
