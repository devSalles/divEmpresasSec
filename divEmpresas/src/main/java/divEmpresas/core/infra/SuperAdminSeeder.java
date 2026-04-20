package divEmpresas.core.infra;

import divEmpresas.Enum.Role;
import divEmpresas.entity.Usuario;
import divEmpresas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuperAdminSeeder implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {

        boolean existeSuperAdmin = this.usuarioRepository.existsByRole(Role.SUPER_ADMIN);

        if(!existeSuperAdmin)
        {
            Usuario usuario = new Usuario();

            usuario.setNome("SuperAdmin");
            usuario.setEmail("superadmin@email.com");
            usuario.setSenha(passwordEncoder.encode("superAdmin123"));
            usuario.setRole(Role.SUPER_ADMIN);
            usuario.setOrganizacao(null);
            usuario.setManager(null);
            usuario.setSubordinados(null);


            this.usuarioRepository.save(usuario);
            System.out.println("Usuário super admin criado!!!");
        }
    }
}
