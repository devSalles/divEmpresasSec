package divEmpresas.core.infra;

import divEmpresas.core.exceptions.*;
import divEmpresas.core.exceptions.security.CriacaoTokenException;
import divEmpresas.core.exceptions.security.AcessoNegadoException;
import divEmpresas.core.exceptions.security.TokenExpiradoException;
import divEmpresas.core.exceptions.security.TokenInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HandlersException {

    //-------------- EXCEÇÕES GLOBAIS --------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageRestError> excecoesGlobais(Exception ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.INTERNAL_SERVER_ERROR,"Erro interno tente novamente mais tarde");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageRestError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageRestError> inputError (MethodArgumentNotValidException ex)
    {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));

        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,"Erro, corrija os dados e tente novamente", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    //-------------- EXCEÇÕES USER --------------
    @ExceptionHandler(EmailNaoEncontradoException.class)
    public ResponseEntity<MessageRestError> EmailNaoEncontradoException(EmailNaoEncontradoException ex)
    {
        MessageRestError messageRestError = new MessageRestError();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageRestError);
    }

    @ExceptionHandler(AcessoNegadoException.class)
    public ResponseEntity<MessageRestError> NaoPertenceAOrganizacaoException(AcessoNegadoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(OrganizacaoNaoEncontradaException.class)
    public ResponseEntity<MessageRestError> OrganizacaoNaoExistenteException(OrganizacaoNaoEncontradaException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.NOT_FOUND,ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageRestError);
    }

    //-------------- EXCEÇÕES TOKEN --------------

    @ExceptionHandler(CriacaoTokenException.class)
    public ResponseEntity<MessageRestError> CriacaoTokenException(CriacaoTokenException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.UNAUTHORIZED,ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageRestError);
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<MessageRestError> TokenInvalidoException(TokenInvalidoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.UNAUTHORIZED,ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageRestError);
    }

    @ExceptionHandler(TokenExpiradoException.class)
    public ResponseEntity<MessageRestError> TokenExpiradoException(TokenExpiradoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.UNAUTHORIZED,ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageRestError);
    }
}
