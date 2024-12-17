package challenge.omie.clientes.domain.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    private Long categoria;

    private String nome;

    private String email;

    public EmailDTO(Email email) {
        this.categoria = email.getCategoria().getId();
        this.nome = email.getNome();
        this.email = email.getEmail();
    }
}
