package divEmpresas.core.infra;

import divEmpresas.core.exceptions.*;
import divEmpresas.core.exceptions.organizacao.CnpjDuplicadoException;
import divEmpresas.core.exceptions.organizacao.NomeDuplicadoException;
import divEmpresas.core.exceptions.security.*;
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

    @ExceptionHandler(RoleInvalidaException.class)
    public ResponseEntity<MessageRestError> RoleInvalidaException(RoleInvalidaException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.FORBIDDEN, ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(messageRestError);
    }

    @ExceptionHandler(ContemSubordinadosException.class)
    public ResponseEntity<MessageRestError> ContemGestorException(ContemSubordinadosException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    //-------------- EXCEÇÕES ORGANIZAÇÃO --------------
    @ExceptionHandler({
            NomeDuplicadoException.class,
            CnpjDuplicadoException.class
    })
    public ResponseEntity<MessageRestError> handleCnpjAndNomeDuplicado(RuntimeException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    //-------------- EXCEÇÕES SECURITY --------------

    @ExceptionHandler(CriacaoTokenException.class)
    public ResponseEntity<MessageRestError> CriacaoTokenException(CriacaoTokenException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageRestError);
    }

    @ExceptionHandler({
            TokenInvalidoException.class,
            TokenExpiradoException.class
    })
    public ResponseEntity<MessageRestError> HandleTokenException(RuntimeException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.UNAUTHORIZED,ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageRestError);
    }

    @ExceptionHandler(AutenticacaoException.class)
    public ResponseEntity<MessageRestError> AutenticacaoException (AutenticacaoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageRestError);
    }
}
