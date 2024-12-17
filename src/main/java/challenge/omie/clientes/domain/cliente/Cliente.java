package challenge.omie.clientes.domain.cliente;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

import challenge.omie.clientes.domain.email.Email;
import challenge.omie.clientes.domain.status.Status;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cliente")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String inscricao;

    private String nome;

    private String apelido;

    private String urlFoto;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(
        name = "cliente_email",
        joinColumns = @JoinColumn(name = "cliente_id"),
        inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    private Set<Email> emails;

    public Cliente(ClienteDTO clienteDTO) {
        this.inscricao = clienteDTO.getInscricao();
        this.nome = clienteDTO.getNome();
        this.apelido = clienteDTO.getApelido();
        this.status = clienteDTO.getStatus();
    }
}
