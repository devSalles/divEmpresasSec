package divEmpresas.core.security;

import divEmpresas.core.exceptions.EmailNaoEncontradoException;
import divEmpresas.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);

        if(token!= null)
        {
            String email = this.tokenService.validateToken(token);
            if(email!=null)
            {
                UserDetails user = this.usuarioRepository.
                        findByEmail(email).orElseThrow(EmailNaoEncontradoException::new);
                if(user != null)
                {
                    var userNamePassword = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(userNamePassword);
                }
            }
        }

        filterChain.doFilter(request,response);
    }


    public String recoverToken(HttpServletRequest request)
    {
        String headerToken = request.getHeader("Authorization");

        if(headerToken == null || !headerToken.startsWith("Bearer "))
        {
            return null;
        }

        return headerToken.replace("Bearer ","");
    }
}
