package dom.com.thesismonolitserver.exceptions;

public class RoleException extends RuntimeException {
    public  RoleException(String msg) {
        super(msg);
    }

    public  RoleException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
