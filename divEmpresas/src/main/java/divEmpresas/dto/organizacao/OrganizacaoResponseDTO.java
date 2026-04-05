package divEmpresas.dto.organizacao;

import divEmpresas.entity.Organizacao;

public record OrganizacaoResponseDTO(
        Long id,
        String none,
        String cnpj
) {

    public static OrganizacaoResponseDTO fromOrganizacao(Organizacao organizacao)
    {
        return new OrganizacaoResponseDTO(organizacao.getId(), organizacao.getNome(),organizacao.getCnpj());
    }
}
