package budget.simple.budgetsimple_back_end.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler{
    private String message = "message";
    @ExceptionHandler(NotExistingUserException.class)
    public ResponseEntity<Object> handleNotExistingUserException(
            NotExistingUserException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(message, ex.getLocalizedMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<Object> handleExistingUserException(
            ExistingUserException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(message, ex.getLocalizedMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoUsersFoundException.class)
    public ResponseEntity<Object> handleNoUsersFoundException(
            NoUsersFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(message, ex.getLocalizedMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}
