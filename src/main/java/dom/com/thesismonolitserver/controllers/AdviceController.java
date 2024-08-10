package dom.com.thesismonolitserver.controllers;

import dom.com.thesismonolitserver.exceptions.CategoryException;
import dom.com.thesismonolitserver.exceptions.ImageException;
import dom.com.thesismonolitserver.exceptions.SubCategoryException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class AdviceController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ CategoryException.class, SubCategoryException.class, ImageException.class})
    public ResponseEntity<Object> handleRoleException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Object> handleRuntimeException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
