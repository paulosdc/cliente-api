package challenge.omie.clientes.domain.categoria;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categoria")
public class Categoria {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty @NotBlank
    private String nome;

    public Categoria(CategoriaDTO categoriaDTO){ 
        this.id = (categoriaDTO.getId() != null && categoriaDTO.getId() != 0) ? categoriaDTO.getId() : null;
        this.nome = categoriaDTO.getNome();
    }
}
