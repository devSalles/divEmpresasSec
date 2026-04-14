package divEmpresas.dto.user;

import divEmpresas.entity.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateRequestDTO (

    @NotBlank(message = "Nome obrigatório")
    String nome,

    @NotBlank(message = "Email obrigatório")
    @Email(message = "formato de email inválido")
    String email,

    @NotBlank(message = "Senha obrigatória")
    @Size(min = 6, message = "Tamanho mínimo de senha 6 caracteres")
    String senha

)implements UsuarioUpdateDTO{

    @Override
    public Usuario atualizar(Usuario usuario) {

        usuario.setNome(this.nome);
        usuario.setEmail(this.email);

        return usuario;
    }
}

