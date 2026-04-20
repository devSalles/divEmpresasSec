package divEmpresas.repository;

import divEmpresas.entity.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizacaoRepository extends JpaRepository<Organizacao,Long > {

    boolean existsByNome(String nome);

    boolean existsByCnpj(String cnpj);
}
