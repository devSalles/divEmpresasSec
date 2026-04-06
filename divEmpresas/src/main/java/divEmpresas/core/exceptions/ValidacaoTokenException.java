package divEmpresas.core.exceptions;

public class ValidacaoTokenException extends RuntimeException {
    public ValidacaoTokenException(String message) {
        super(message);
    }
    public ValidacaoTokenException() {
        super("Token inválido");
    }
}
