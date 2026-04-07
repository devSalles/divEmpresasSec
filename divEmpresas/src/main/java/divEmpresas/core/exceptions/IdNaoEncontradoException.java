package divEmpresas.core.exceptions;

public class IdNaoEncontradoException extends RuntimeException {
    public IdNaoEncontradoException(String message) {
        super(message);
    }

    public IdNaoEncontradoException() {
        super("ID não encontrado");
    }
}
