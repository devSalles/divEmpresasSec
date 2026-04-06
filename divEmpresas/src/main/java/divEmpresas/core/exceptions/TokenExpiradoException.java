package divEmpresas.core.exceptions;

public class TokenExpiradoException extends RuntimeException {
    public TokenExpiradoException(String message) {
        super(message);
    }
    public TokenExpiradoException() {
        super("Token expirado");
    }
}
