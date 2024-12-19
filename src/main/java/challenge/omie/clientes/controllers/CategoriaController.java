package challenge.omie.clientes.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import challenge.omie.clientes.domain.categoria.Categoria;
import challenge.omie.clientes.domain.categoria.CategoriaDTO;
import challenge.omie.clientes.service.CategoriaService;
import challenge.omie.clientes.utils.ValidationUtils;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ValidationUtils validationUtils;

    @GetMapping
    public ResponseEntity<?> getAllCategorias() {
        List<CategoriaDTO> clientes = categoriaService.getCategorias();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<?> saveCategoria(@RequestBody CategoriaDTO categoria) {
        if(!validationUtils.isValidDTO(categoria).isEmpty()) 
            return ResponseEntity.badRequest().body(validationUtils.isValidDTO(categoria));
        Categoria newCategoria = new Categoria(categoria);
        categoriaService.salvar(newCategoria);
        return ResponseEntity.ok().body("Categoria criada com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoria) {
        if(!validationUtils.isValidDTO(categoria).isEmpty()) 
            return ResponseEntity.badRequest().body(validationUtils.isValidDTO(categoria));
        try{
            Optional<Categoria> optionalCategoria = categoriaService.getCategoriaById(id);
            if(optionalCategoria.isEmpty()) return ResponseEntity.notFound().build();
            Categoria categoriaToUpdate = new Categoria(categoria);
            categoriaToUpdate.setId(id);
            categoriaService.salvar(categoriaToUpdate);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body("Erro ao atualizar categoria.");
        }
        return ResponseEntity.ok("Categoria atualizada com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable Long id){
        try{
            Optional<Categoria> optionalCategoria = categoriaService.getCategoriaById(id);
            if(optionalCategoria.isEmpty()) return ResponseEntity.notFound().build();
            categoriaService.deletar(id);
            return ResponseEntity.ok("Categoria excluída com sucesso!");
        } catch (Exception e){
            return ResponseEntity.internalServerError().body("Erro ao excluir categoria.");
        }
    }
}
