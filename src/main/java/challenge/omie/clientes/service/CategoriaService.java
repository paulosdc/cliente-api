package challenge.omie.clientes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challenge.omie.clientes.domain.categoria.Categoria;
import challenge.omie.clientes.domain.categoria.CategoriaDTO;
import challenge.omie.clientes.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> getCategorias(){
        return categoriaRepository.findAll().stream().map(CategoriaDTO::new).toList();
    }

    public Optional<Categoria> getCategoriaById(Long id){
        return categoriaRepository.findById(id);
    }

    public void salvar(Categoria categoria){
        categoriaRepository.save(categoria);
    }

    public void deletar(Long id){
        categoriaRepository.deleteById(id);
    }
}
