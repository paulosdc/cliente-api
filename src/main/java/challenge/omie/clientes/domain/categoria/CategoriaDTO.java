package challenge.omie.clientes.domain.categoria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    
    String nome;

    public CategoriaDTO(Categoria categoria) {
        this.nome = categoria.getNome();
    }
}
