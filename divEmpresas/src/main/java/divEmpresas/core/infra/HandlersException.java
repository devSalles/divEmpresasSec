package divEmpresas.core.infra;

import divEmpresas.core.exceptions.EmailNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlersException {

    @ExceptionHandler(EmailNaoEncontradoException.class)
    public ResponseEntity<MessageRestError> EmailNaoEncontradoException(EmailNaoEncontradoException ex)
    {
        MessageRestError messageRestError = new MessageRestError();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageRestError);
    }
}
