package divEmpresas.service;

import divEmpresas.core.exceptions.EmailNaoEncontradoException;
import divEmpresas.core.exceptions.GerenteNaoEncontradoException;
import divEmpresas.core.exceptions.NaoPertenceAOrganizacaoException;
import divEmpresas.core.exceptions.OrganizacaoNaoExistenteException;
import divEmpresas.dto.user.UsuarioRequestDTO;
import divEmpresas.dto.user.UsuarioResponseDTO;
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
        Organizacao organizacao = this.organizacaoRepository.findById(userDTO.idOrganizacao()).orElseThrow(OrganizacaoNaoExistenteException::new);

        Usuario usuario = userDTO.toUsuario(organizacao);
        usuario.setSenha(senhaCriptografada);

        if(userDTO.idManager()!=null)
        {
            Usuario gerente = this.usuarioRepository.findById(userDTO.idManager()).orElseThrow(GerenteNaoEncontradoException::new);

            if(!gerente.getOrganizacao().getId().equals(organizacao.getId()))
            {
                throw new NaoPertenceAOrganizacaoException();
            }
        }

        return UsuarioResponseDTO.fromUsuario(usuario);
    }
}
