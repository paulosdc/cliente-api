package challenge.omie.clientes.domain.email;

import challenge.omie.clientes.domain.categoria.Categoria;
import challenge.omie.clientes.domain.cliente.Cliente;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email")
public class Email implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false) 
    private Categoria categoria;

    @ManyToMany(mappedBy = "emails")
    private Set<Cliente> clientes;
}
