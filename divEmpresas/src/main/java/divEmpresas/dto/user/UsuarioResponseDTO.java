package divEmpresas.dto.user;

import divEmpresas.Enum.Role;
import divEmpresas.entity.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        Role role,
        Long idOrgaizacao,
        Long idManager
) {
    public static UsuarioResponseDTO fromUsuario(Usuario usuario)
    {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole(),
                usuario.getOrganizacao().getId(),usuario.getManager().getId());
    }
}
