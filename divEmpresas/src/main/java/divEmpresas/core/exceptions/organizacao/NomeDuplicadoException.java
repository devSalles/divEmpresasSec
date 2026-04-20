package divEmpresas.core.exceptions.organizacao;

public class NomeDuplicadoException extends RuntimeException {
    public NomeDuplicadoException(String message) {
        super(message);
    }
    public NomeDuplicadoException() {
        super("Já existe uma organização com esse nome");
    }
}
