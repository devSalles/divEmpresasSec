package divEmpresas.service;

import divEmpresas.core.exceptions.security.AutenticacaoException;
import divEmpresas.core.security.TokenService;
import divEmpresas.dto.security.LoginDTO;
import divEmpresas.dto.security.TokenDTO;
import divEmpresas.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public TokenDTO login(LoginDTO loginDTO)
    {
        var userNamePassword = new UsernamePasswordAuthenticationToken(loginDTO.email(),loginDTO.senha());
        Authentication auth = authenticationManager.authenticate(userNamePassword);

        Object principal = auth.getPrincipal();
        if((!(principal instanceof Usuario usuario)))
        {
            throw new AutenticacaoException("Usuário não autenticado");
        }

        String token = tokenService.createToken(usuario);
        return new TokenDTO(token);
    }

}
