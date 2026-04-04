package divEmpresas.repository;

import divEmpresas.entity.AtividadeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<AtividadeLog,Long > {
}
