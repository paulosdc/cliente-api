package challenge.omie.clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import challenge.omie.clientes.domain.categoria.Categoria;

@Repository
public interface CategoriaRepository  extends JpaRepository<Categoria, Long>{
}
