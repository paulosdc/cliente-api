package challenge.omie.clientes.domain.categoria;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = CategoriaDTODeserializer.class)
public class CategoriaDTO {
    
    Long id;

    String nome;

    public CategoriaDTO(Categoria categoria) {
        this.id =  categoria.getId();
        this.nome = categoria.getNome();
    }
}
