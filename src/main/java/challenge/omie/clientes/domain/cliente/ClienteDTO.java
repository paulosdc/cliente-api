package challenge.omie.clientes.domain.cliente;

import java.util.List;

import challenge.omie.clientes.domain.email.EmailDTO;
import challenge.omie.clientes.domain.status.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;

    @NotBlank @NotNull
    private String inscricao;

    @NotBlank @NotNull
    private String nome;

    @NotBlank @NotNull
    private String apelido;

    @NotBlank @NotNull
    private String urlFoto;

    @NotBlank @NotNull
    private Status status;

    private List<EmailDTO> emails;

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.inscricao = cliente.getInscricao();
        this.nome = cliente.getNome();
        this.apelido = cliente.getApelido();
        this.urlFoto = cliente.getUrlFoto();
        this.status = cliente.getStatus();
        this.emails = cliente.getEmails().stream().map(EmailDTO::new).toList();
    }
}
