package divEmpresas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Organizacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String nome;

    @Column(nullable = false,unique = true)
    private String cnpj;

    @OneToMany(mappedBy = "organizacao",fetch = FetchType.LAZY)
    private List<Usuario> user = new ArrayList<>();
}
