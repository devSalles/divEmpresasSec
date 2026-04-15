package divEmpresas.service;

import divEmpresas.Enum.Role;
import divEmpresas.core.exceptions.*;
import divEmpresas.core.exceptions.security.AcessoNegadoException;
import divEmpresas.dto.user.*;
import divEmpresas.entity.Organizacao;
import divEmpresas.entity.Usuario;
import divEmpresas.repository.OrganizacaoRepository;
import divEmpresas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final OrganizacaoRepository organizacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usuarioRepository.findByEmail(username).orElseThrow(EmailNaoEncontradoException::new);
    }

    public UsuarioResponseDTO salvarUsuario(UsuarioRequestDTO userDTO)
    {
        String senhaCriptografada = passwordEncoder.encode(userDTO.senha());
        Organizacao organizacao = this.organizacaoRepository.findById(userDTO.idOrganizacao()).orElseThrow(OrganizacaoNaoEncontradaException::new);

        Usuario usuario = userDTO.toUsuario(organizacao);
        usuario.setSenha(senhaCriptografada);

        if(userDTO.idManager()!=null)
        {
            Usuario gerente = this.usuarioRepository.findById(userDTO.idManager()).orElseThrow(GerenteNaoEncontradoException::new);

            if(!gerente.getOrganizacao().getId().equals(organizacao.getId()))
            {
                throw new AcessoNegadoException("Não pode cadastrar usuários em outra empresa");
            }
        }

        return UsuarioResponseDTO.fromUsuario(usuario);
    }

    public UsuarioResponseDTO atualizarAdmin(Long id, UsuarioAdminUpdateDTO usuarioAdminDTO, Usuario usuarioLogado)
    {
        Usuario usuarioAlvo = buscarID(id);

        if (usuarioLogado.getRole() != Role.ADMIN)
        {
            throw new AcessoNegadoException("Acesso negado");
        }

        usuarioAdminDTO.atualizar(usuarioAlvo);
        String senhaCriptografada = passwordEncoder.encode(usuarioAdminDTO.senha());
        usuarioAlvo.setSenha(senhaCriptografada);

        this.usuarioRepository.save(usuarioAlvo);
        return UsuarioResponseDTO.fromUsuario(usuarioAlvo);
    }

    public UsuarioResponseDTO atualizarGerente(Long id, UsuarioGerenteUpdateDTO gerenteDTO, Usuario usuarioLogado)
    {
        Usuario usuarioAlvo = buscarID(id);

        if(usuarioLogado.getRole()!=Role.MANAGER)
        {
            throw new AcessoNegadoException("Acesso negado");
        }

        gerenteDTO.atualizar(usuarioAlvo);
        String senhaCriptografada = passwordEncoder.encode(gerenteDTO.senha());
        usuarioLogado.setSenha(senhaCriptografada);

        this.usuarioRepository.save(usuarioAlvo);
        return UsuarioResponseDTO.fromUsuario(usuarioAlvo);
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioUpdateRequestDTO usuarioDTO, Usuario usuarioLogado)
    {
        Usuario usuarioAlvo = buscarID(id);

        if(!usuarioAlvo.getId().equals(usuarioLogado.getId()))
        {
            throw new AcessoNegadoException("Acesso negado");
        }

        usuarioDTO.atualizar(usuarioAlvo);
        String senhaCriptografada = passwordEncoder.encode(usuarioDTO.senha());
        usuarioAlvo.setSenha(senhaCriptografada);

        this.usuarioRepository.save(usuarioAlvo);
        return UsuarioResponseDTO.fromUsuario(usuarioAlvo);
    }

    public UsuarioResponseDTO buscarPorId(Long id,Usuario usuarioLogado)
    {
        Usuario usuario = buscarID(id);

        if(!usuario.getOrganizacao().getId().equals(usuarioLogado.getOrganizacao().getId()))
        {
            throw new AcessoNegadoException("Acesso negado");
        }

        switch (usuarioLogado.getRole())
        {
            case ADMIN:
                return UsuarioResponseDTO.fromUsuario(usuario);

            case MANAGER:
                if(usuario.getManager().getId().equals(usuarioLogado.getId()) ||
                usuario.getId().equals(usuarioLogado.getId()))
                {
                    return UsuarioResponseDTO.fromUsuario(usuario);
                }
            break;

            case USER:
                if(usuario.getId().equals(usuarioLogado.getId()))
                {
                    return UsuarioResponseDTO.fromUsuario(usuario);
                }
            break;
        }

        throw new AcessoNegadoException("Acesso negado");
    }

    public List<UsuarioResponseDTO> listarTodos(Usuario usuarioLogado)
    {
        switch (usuarioLogado.getRole())
        {
            case ADMIN:
                List<Usuario>usuarios = this.usuarioRepository.findByOrganizacaoId(usuarioLogado.getOrganizacao().getId());
                return usuarios.stream().map(UsuarioResponseDTO::fromUsuario).toList();

            case MANAGER:
                List<Usuario> usuarioManager = this.usuarioRepository
                        .findByOrganizacaoIdAndManagerId(usuarioLogado.getOrganizacao().getId(),usuarioLogado.getId());
                return usuarioManager.stream().map(UsuarioResponseDTO::fromUsuario).toList();

            case USER:
                return List.of(UsuarioResponseDTO.fromUsuario(usuarioLogado));
        }

        throw new AcessoNegadoException("Acesso negado");
    }

    public UsuarioResponseDTO deletarUsuario(Long id, Usuario usuarioLogado)
    {
        Usuario usuarioAlvo = buscarID(id);

        if(usuarioLogado.getRole() != Role.ADMIN)
        {
            throw new RoleInvalidaException("Apenas usuários ADMIN podem excluir");
        }

        if(!usuarioAlvo.getOrganizacao().getId().equals(usuarioLogado.getOrganizacao().getId()))
        {
            throw new AcessoNegadoException("Usuário pertence a outra organização");
        }

        boolean contemSubordinados = this.usuarioRepository.existsByManagerId(usuarioAlvo.getId());

        if(contemSubordinados)
        {
            throw new ContemSubordinadosException("Usuário possui subordinados");
        }

        this.usuarioRepository.delete(usuarioAlvo);
        return UsuarioResponseDTO.fromUsuario(usuarioAlvo);
    }

    // ------------- METODO AUXILIAR -------------

    public Usuario buscarID(Long id)
    {
        return this.usuarioRepository.findById(id).orElseThrow(IdNaoEncontradoException::new);
    }
}
