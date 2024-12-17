package challenge.omie.clientes.controllers;

import challenge.omie.clientes.domain.categoria.Categoria;
import challenge.omie.clientes.domain.cliente.Cliente;
import challenge.omie.clientes.domain.cliente.ClienteDTO;
import challenge.omie.clientes.domain.email.Email;
import challenge.omie.clientes.domain.email.EmailDTO;
import challenge.omie.clientes.domain.status.Status;
import challenge.omie.clientes.service.CategoriaService;
import challenge.omie.clientes.service.ClienteService;
import challenge.omie.clientes.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<?> getAllClientes() {
        List<ClienteDTO> clientes = clienteService.getClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Long id) {
        Optional<Cliente> optionalCliente = clienteService.getClienteById(id);
        if(optionalCliente.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optionalCliente.get());
    }

    @PostMapping
    public ResponseEntity<?> criarCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente(clienteDTO);

        if (clienteDTO.getEmails() != null) {
            Set<Email> emails = new HashSet<>();
            for (EmailDTO emailDTO : clienteDTO.getEmails()) {
                Optional<Categoria> categoria = categoriaService.getCategoriaById(emailDTO.getCategoria());
                if (categoria.isPresent()) {
                    Email email = new Email();
                    email.setNome(emailDTO.getNome());
                    email.setEmail(emailDTO.getEmail());
                    email.setCategoria(categoria.get());
                    emails.add(emailService.salvar(email));
                }
            }
            cliente.setEmails(emails);
        }

        clienteService.salvar(cliente);
        return ResponseEntity.ok().body("Cliente criado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        Optional<Cliente> optionalCliente = clienteService.getClienteById(id);

        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();

            cliente.setInscricao(clienteDTO.getInscricao());
            cliente.setNome(clienteDTO.getNome());
            cliente.setApelido(clienteDTO.getApelido());
            cliente.setStatus(clienteDTO.getStatus());
            
            List<Email> oldEmails = new ArrayList<>();
            for(Email email : cliente.getEmails()){
                oldEmails.add(email);
            }

            if (clienteDTO.getEmails() != null) {
                Set<Email> emails = new HashSet<>();
                for (EmailDTO emailDTO : clienteDTO.getEmails()) {
                    Optional<Categoria> categoria = categoriaService.getCategoriaById(emailDTO.getCategoria());
                    if (categoria.isPresent()) {
                        Email email = new Email();
                        email.setNome(emailDTO.getNome());
                        email.setEmail(emailDTO.getEmail());
                        email.setCategoria(categoria.get());
                        emails.add(email);
                    } else return ResponseEntity.badRequest().body("A categoria escolhida não existe. Cadastre-a antes de seguir!");
                }
                emails.stream().forEach(email -> emailService.salvar(email));
                cliente.setEmails(emails);
            }

            clienteService.salvar(cliente);

            for(Email emailToDelete : oldEmails){
                emailService.deletar(emailToDelete);
            }
            return ResponseEntity.ok().body("Cliente atualizado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> desativarCliente(@PathVariable Long id){
        Cliente cliente = (Cliente) getClienteById(id).getBody();

        if(cliente == null) return ResponseEntity.internalServerError().body("O cliente não existe!"); 

        cliente.setStatus(Status.DESATIVADO);
        clienteService.salvar(cliente);
        return ResponseEntity.ok().body("Cliente desativado com sucesso");
    }
}
