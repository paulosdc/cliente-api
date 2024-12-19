package challenge.omie.clientes.domain.email;

import challenge.omie.clientes.domain.categoria.CategoriaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    private Long id;

    private CategoriaDTO categoria;

    private String nome;

    private String email;

    public EmailDTO(Email email) {
        this.id = email.getId();
        this.categoria = new CategoriaDTO(email.getCategoria());
        this.nome = email.getNome();
        this.email = email.getEmail();
    }
}
