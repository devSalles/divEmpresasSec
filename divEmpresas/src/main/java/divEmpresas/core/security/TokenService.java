package divEmpresas.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import divEmpresas.core.exceptions.CriacaoTokenException;
import divEmpresas.core.exceptions.ValidacaoTokenException;
import divEmpresas.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.key.value}")
    private String secret;


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
        catch (JWTVerificationException ex)
        {
            throw new ValidacaoTokenException();
        }
    }

    public Instant timeExpiresToken()
    {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
