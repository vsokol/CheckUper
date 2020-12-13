package milovanov.stc31.innopolis.checkuper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<Exception> catchException(Exception exception) {
        // TODO: сделать обработку ошибок
        ResponseEntity<Exception> response = ResponseEntity.status(200).body(exception);
        return response;
    }
}
