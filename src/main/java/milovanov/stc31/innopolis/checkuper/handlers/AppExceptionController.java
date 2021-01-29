package milovanov.stc31.innopolis.checkuper.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AppExceptionController {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<Exception> catchException(Exception exception) {
        ResponseEntity<Exception> response = ResponseEntity.status(200).body(exception);
        return response;
    }
}
