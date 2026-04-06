package divEmpresas.core.exceptions;

public class OrganizacaoNaoExistenteException extends RuntimeException {
    public OrganizacaoNaoExistenteException(String message) {
        super(message);
    }
    public OrganizacaoNaoExistenteException() {
        super("Organização não encontrada");
    }
}
