package divEmpresas.core.exceptions;

public class NaoPertenceAOrganizacaoException extends RuntimeException {
  public NaoPertenceAOrganizacaoException(String message) {
    super(message);
  }
  public NaoPertenceAOrganizacaoException() {
    super("Gerente deve pertencer a mesma organização");
  }
}
