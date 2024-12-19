package challenge.omie.clientes.domain.cliente;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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

    @NotEmpty @NotBlank
    private String inscricao;

    @NotEmpty @NotBlank
    private String nome;

    @NotEmpty @NotBlank
    private String apelido;

    @NotEmpty @NotBlank
    private String urlFoto;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(
        name = "cliente_email",
        joinColumns = @JoinColumn(name = "cliente_id"),
        inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    private List<Email> emails;

    public Cliente(ClienteDTO clienteDTO) {
        this.inscricao = clienteDTO.getInscricao();
        this.nome = clienteDTO.getNome();
        this.apelido = clienteDTO.getApelido();
        this.status = clienteDTO.getStatus();
        this.urlFoto = clienteDTO.getUrlFoto();
        this.emails = clienteDTO.getEmails().stream().map(Email::new).toList();;
    }
}
