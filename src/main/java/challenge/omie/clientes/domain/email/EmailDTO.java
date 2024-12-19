package challenge.omie.clientes.domain.email;

import challenge.omie.clientes.domain.categoria.CategoriaDTO;
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
public class EmailDTO {

    private Long id;

    @NotBlank @NotNull
    private CategoriaDTO categoria;

    @NotBlank @NotNull
    private String nome;

    @NotBlank @NotNull
    private String email;

    public EmailDTO(Email email) {
        this.id = email.getId();
        this.categoria = new CategoriaDTO(email.getCategoria());
        this.nome = email.getNome();
        this.email = email.getEmail();
    }
}
