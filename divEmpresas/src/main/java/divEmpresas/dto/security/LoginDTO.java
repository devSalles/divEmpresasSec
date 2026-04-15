package divEmpresas.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(

        @NotBlank(message = "Email obrigatório") @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "Senha obrigatório")
        String senha
) {
}
