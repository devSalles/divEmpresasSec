package divEmpresas.core.exceptions;

public class GerenteNaoEncontradoException extends RuntimeException {
    public GerenteNaoEncontradoException(String message) {
        super(message);
    }
    public GerenteNaoEncontradoException() {
        super("Gerente não encontrado");
    }
}
