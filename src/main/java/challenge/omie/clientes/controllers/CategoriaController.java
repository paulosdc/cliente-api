package challenge.omie.clientes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<?> getAllCategorias() {
        List<CategoriaDTO> clientes = categoriaService.getCategorias();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<?> saveCategoria(@RequestBody CategoriaDTO categoria) {
        Categoria newCategoria = new Categoria(categoria);
        categoriaService.salvar(newCategoria);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateCategoria(@RequestBody Categoria categoria) {
        try{
            categoriaService.salvar(categoria);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body("Erro ao atualizar categoria.");
        }
        return ResponseEntity.ok("Categoria atualizada com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable Long id){
        try{
            categoriaService.deletar(id);
            return ResponseEntity.ok("Categoria excluída com sucesso!");
        } catch (Exception e){
            return ResponseEntity.internalServerError().body("Erro ao excluir categoria.");
        }
    }
}
