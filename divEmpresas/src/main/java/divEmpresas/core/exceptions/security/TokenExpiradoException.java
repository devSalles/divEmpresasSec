package divEmpresas.core.exceptions.security;

public class TokenExpiradoException extends RuntimeException {
    public TokenExpiradoException(String message) {
        super(message);
    }
    public TokenExpiradoException() {
        super("Token expirado");
    }
}
