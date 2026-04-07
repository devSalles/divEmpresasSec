package divEmpresas.core.exceptions;

public class OrganizacaoNaoEncontradaException extends RuntimeException {
    public OrganizacaoNaoEncontradaException(String message) {
        super(message);
    }
    public OrganizacaoNaoEncontradaException() {
        super("Organização não encontrada");
    }
}
