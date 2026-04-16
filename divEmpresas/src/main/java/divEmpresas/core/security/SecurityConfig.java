package divEmpresas.core.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    {
        return httpSecurity.csrf(csrf->csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize-> authorize

                        //ROTAS USUÁRIOS
                        .requestMatchers(HttpMethod.POST,"/user/registro").hasAnyRole("ADMIN","MANAGER")
                        .requestMatchers(HttpMethod.POST,"/user/login").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/user/atualizar-admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/user/atualizar-gerente").hasAnyRole("ADMIN","MANAGER")
                        .requestMatchers(HttpMethod.PUT,"/user/atualizar-subordinado").hasAnyRole("ADMIN","MANAGER","USER")
                        .requestMatchers(HttpMethod.GET,"/user/buscar/**").hasAnyRole("ADMIN","MANAGER","USER")
                        .requestMatchers(HttpMethod.GET,"/user/listar-todos").hasAnyRole("ADMIN","MANAGER","USER")
                        .requestMatchers(HttpMethod.DELETE,"/user/deletar-usuario").hasAnyRole("ADMIN","MANAGER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws  Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
