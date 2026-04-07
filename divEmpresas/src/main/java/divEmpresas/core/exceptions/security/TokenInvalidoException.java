package divEmpresas.core.exceptions.security;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String message) {
        super(message);
    }
    public TokenInvalidoException() {
        super("Token inválido");
    }
}
