package divEmpresas.repository;

import divEmpresas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByOrganizacaoId(Long idEmpresa);

    List<Usuario> findByOrganizacaoIdAndManagerId(Long idEmpresa, Long idManager);

    boolean existyByEmailAndOrganizacaoId(String email,Long organizacaoID);

    boolean existsByManagerId(Long idManager);
}
