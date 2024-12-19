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
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllClientes() {
        List<ClienteDTO> clientes = clienteService.getClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> getAllClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<ClienteDTO> clientes = clienteService.getClientes(PageRequest.of(page, size));
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Long id) {
        Optional<Cliente> optionalCliente = clienteService.getClienteById(id);
        if(optionalCliente.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optionalCliente.get());
    }

    @PostMapping
    public ResponseEntity<?> criarCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
        if (!isValidCpfOrCnpj(clienteDTO.getInscricao())) {
            return ResponseEntity.badRequest().body("Inscrição inválida. Deve ser um CPF ou CNPJ válido.");
        }

        Cliente cliente = new Cliente(clienteDTO);

        if (clienteDTO.getEmails() != null) {
            List<Email> emails = new ArrayList<>();
            for (EmailDTO emailDTO : clienteDTO.getEmails()) {
                Optional<Categoria> categoria = categoriaService.getCategoriaById(emailDTO.getCategoria().getId());
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
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody @Valid ClienteDTO clienteDTO) {
        if (!isValidCpfOrCnpj(clienteDTO.getInscricao())) {
            return ResponseEntity.badRequest().body("Inscrição inválida. Deve ser um CPF ou CNPJ válido.");
        }

        Optional<Cliente> optionalCliente = clienteService.getClienteById(id);
    
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
    
            cliente.setInscricao(clienteDTO.getInscricao());
            cliente.setNome(clienteDTO.getNome());
            cliente.setApelido(clienteDTO.getApelido());
            cliente.setStatus(clienteDTO.getStatus());
            cliente.setUrlFoto(clienteDTO.getUrlFoto());
    
            List<Email> oldEmails = new ArrayList<>(cliente.getEmails());
    
            if (clienteDTO.getEmails() != null) {
                List<Email> newEmails = new ArrayList<>();
                for (EmailDTO emailDTO : clienteDTO.getEmails()) {
                    Optional<Categoria> categoria = categoriaService.getCategoriaById(emailDTO.getCategoria().getId());
                    if (categoria.isPresent()) {
                        Email email = new Email(emailDTO);
                        newEmails.add(emailService.salvar(email));
                    } else {
                        return ResponseEntity.badRequest().body("A categoria escolhida não existe. Cadastre-a antes de seguir!");
                    }
                }
                cliente.setEmails(newEmails);
    
                List<Long> newEmailIds = newEmails.stream().map(Email::getId).toList();
                for (Email oldEmail : oldEmails) {
                    if (!newEmailIds.contains(oldEmail.getId())) {
                        emailService.deletar(oldEmail);
                    }
                }
            }
    
            clienteService.salvar(cliente);
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

    private boolean isValidCpfOrCnpj(String inscricao) {
        return inscricao != null && (isValidCpf(inscricao) || isValidCnpj(inscricao));
    }

    public static boolean isValidCpf(String cpf) {
        cpf = cpf.replaceAll("[^\\d]", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int firstDigit = 11 - (sum % 11);
            if (firstDigit >= 10) {
                firstDigit = 0;
            }

            if (firstDigit != Character.getNumericValue(cpf.charAt(9))) {
                return false;
            }

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10) {
                secondDigit = 0;
            }

            return secondDigit == Character.getNumericValue(cpf.charAt(10));
        } catch (InputMismatchException e) {
            return false;
        }
    }

    public static boolean isValidCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("[^\\d]", "");

        if (cnpj.length() != 14) {
            return false;
        }

        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        try {
            int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int sum = 0;
            for (int i = 0; i < 12; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weights1[i];
            }
            int firstDigit = sum % 11 < 2 ? 0 : 11 - (sum % 11);

            if (firstDigit != Character.getNumericValue(cnpj.charAt(12))) {
                return false;
            }

            sum = 0;
            for (int i = 0; i < 13; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weights2[i];
            }
            int secondDigit = sum % 11 < 2 ? 0 : 11 - (sum % 11);

            return secondDigit == Character.getNumericValue(cnpj.charAt(13));
        } catch (InputMismatchException e) {
            return false;
        }
    }

}
