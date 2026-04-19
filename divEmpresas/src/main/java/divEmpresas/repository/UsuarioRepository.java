package divEmpresas.repository;

import divEmpresas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByIdAndOrganizacaoId(Long id, Long idOrganizacao);

    List<Usuario> findByOrganizacaoId(Long idEmpresa);

    List<Usuario> findByOrganizacaoIdAndManagerId(Long idEmpresa, Long idManager);

    boolean existsByEmailAndOrganizacaoId(String email,Long organizacaoID);

    boolean existsByManagerId(Long idManager);
}
