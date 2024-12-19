package challenge.omie.clientes.domain.cliente;

import java.util.List;

import challenge.omie.clientes.domain.email.EmailDTO;
import challenge.omie.clientes.domain.status.Status;
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

    private String inscricao;

    private String nome;

    private String apelido;

    private String urlFoto;

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
