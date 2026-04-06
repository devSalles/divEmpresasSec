package divEmpresas.core.exceptions;

public class CriacaoTokenException extends RuntimeException {
    public CriacaoTokenException(String message) {
        super(message);
    }
    public CriacaoTokenException() {
        super("Erro na criação do token");
    }
}
