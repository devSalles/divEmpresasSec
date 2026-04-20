package divEmpresas.core.exceptions.organizacao;

public class CnpjDuplicadoException extends RuntimeException {
    public CnpjDuplicadoException(String message) {
        super(message);
    }
    public CnpjDuplicadoException() {
        super("Já existe uma organização com esse CNPJ");
    }
}
