package divEmpresas.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import divEmpresas.core.exceptions.security.AutenticacaoException;
import divEmpresas.core.exceptions.security.CriacaoTokenException;
import divEmpresas.core.exceptions.security.TokenExpiradoException;
import divEmpresas.core.exceptions.security.TokenInvalidoException;
import divEmpresas.dto.security.LoginDTO;
import divEmpresas.dto.security.TokenDTO;
import divEmpresas.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${api.key.value}")
    private String secret;

    private final AuthenticationManager authenticationManager;

    public TokenDTO login(LoginDTO loginDTO)
    {
        var userNamePassword = new UsernamePasswordAuthenticationToken(loginDTO.email(),loginDTO.senha());
        Authentication auth = authenticationManager.authenticate(userNamePassword);

        Object principal = auth.getPrincipal();
        if(!(principal instanceof Usuario usuario))
        {
            throw new AutenticacaoException("Usuário não autenticado");
        }

        String token = createToken(usuario);
        return new TokenDTO(token);
    }

    public String createToken(Usuario usuario)
    {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("divEmpresasSec")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(timeExpiresToken())
                    .sign(algorithm);
            return token;

        }
        catch (JWTCreationException ex)
        {
            throw new CriacaoTokenException();
        }
    }

    public String validateToken(String token)
    {
        try
        {
            Algorithm algorithm =Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();

        }
        catch (TokenExpiredException ex)
        {
            throw new TokenExpiradoException("Token expirado");
        }
        catch (JWTVerificationException ex)
        {
            throw new TokenInvalidoException("Token inválido");
        }
    }

    public Instant timeExpiresToken()
    {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
