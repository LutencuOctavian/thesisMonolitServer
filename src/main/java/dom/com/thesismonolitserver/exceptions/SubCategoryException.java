package dom.com.thesismonolitserver.exceptions;

public class SubCategoryException extends RuntimeException {
    public SubCategoryException() {
    }

    public SubCategoryException(String message) {
        super(message);
    }

    public SubCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubCategoryException(Throwable cause) {
        super(cause);
    }
}
