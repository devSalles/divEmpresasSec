package divEmpresas.service;

import divEmpresas.Enum.Role;
import divEmpresas.core.exceptions.organizacao.CnpjDuplicadoException;
import divEmpresas.core.exceptions.organizacao.NomeDuplicadoException;
import divEmpresas.core.exceptions.security.AcessoNegadoException;
import divEmpresas.dto.organizacao.OrganizacaoRequestDTO;
import divEmpresas.dto.organizacao.OrganizacaoResponseDTO;
import divEmpresas.entity.Organizacao;
import divEmpresas.entity.Usuario;
import divEmpresas.repository.OrganizacaoRepository;
import divEmpresas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizacaoService {

    private final OrganizacaoRepository organizacaoRepository;
    private final UsuarioRepository usuarioRepository;


    public OrganizacaoResponseDTO criarOrganziacao(OrganizacaoRequestDTO orgRequestDTO, Usuario usuarioLogado)
    {
        if(usuarioLogado.getRole() != Role.SUPER_ADMIN)
        {
            throw new AcessoNegadoException("Acesso negado, nível de acesso não permitido");
        }

        boolean existeOganizacaoComEsseNome = this.organizacaoRepository.existsByNome(orgRequestDTO.nome());
        if(existeOganizacaoComEsseNome)
        {
            throw new NomeDuplicadoException();
        }

        boolean existeCnpjOrganizacao = this.organizacaoRepository.existsByCnpj(orgRequestDTO.cnpj());
        if(existeCnpjOrganizacao)
        {
            throw new CnpjDuplicadoException();
        }

        Organizacao organizacaoSalvar = orgRequestDTO.toOrganizacao();

        this.organizacaoRepository.save(organizacaoSalvar);
        return OrganizacaoResponseDTO.fromOrganizacao(organizacaoSalvar);
    }
}
