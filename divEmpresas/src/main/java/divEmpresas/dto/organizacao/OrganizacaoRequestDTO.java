package divEmpresas.dto.organizacao;

import divEmpresas.entity.Organizacao;
import divEmpresas.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;


public record OrganizacaoRequestDTO(

        @NotBlank(message = "Nome obrigatório")
        String nome,

        @NotBlank(message ="CNPJ obrigatório" ) @CNPJ(message = "Formato de CNPJ inválido")
        String cnpj
) {

    public Organizacao toOrganizacao()
    {
        Organizacao organizacao = new Organizacao();

        organizacao.setNome(this.nome);
        organizacao.setCnpj(this.cnpj);

        return organizacao;
    }
}
