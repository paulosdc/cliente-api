package challenge.omie.clientes.domain.email;

import challenge.omie.clientes.domain.categoria.Categoria;
import challenge.omie.clientes.domain.cliente.Cliente;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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

    @JsonIgnore
    @ManyToMany(mappedBy = "emails")
    private List<Cliente> clientes;

     public Email(EmailDTO emailDTO) {
        this.id = emailDTO.getId();
        this.nome = emailDTO.getNome();
        this.email = emailDTO.getEmail();
        this.categoria = new Categoria(emailDTO.getCategoria());
    }
}
