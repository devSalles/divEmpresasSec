package divEmpresas.dto.user;

import divEmpresas.Enum.Role;
import divEmpresas.entity.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioAdminUpdateDTO(

        @NotBlank(message = "Nome obrigatório")
        String nome,

        @NotBlank(message = "Email obrigatório") @Email(message = "formato de email inválido")
        String email,

        @NotBlank(message = "Senha obrigatória")
        @Size(min = 6,message = "Tamanho mínimo de senha 6 caracteres")
        String senha,

        @NotNull(message = "Nível de acesso obrigatório")
        Role role,

        Long idGerente
) implements UsuarioUpdateDTO {

    @Override
    public Usuario atualizar(Usuario usuario) {

        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);
        usuario.setRole(role);

        return usuario;
    }
}
