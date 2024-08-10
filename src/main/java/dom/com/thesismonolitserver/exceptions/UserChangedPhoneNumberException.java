package dom.com.thesismonolitserver.exceptions;

public class UserChangedPhoneNumberException extends RuntimeException {
    public UserChangedPhoneNumberException() {
        super();
    }

    public UserChangedPhoneNumberException(String message) {
        super(message);
    }

    public UserChangedPhoneNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserChangedPhoneNumberException(Throwable cause) {
        super(cause);
    }

    protected UserChangedPhoneNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
