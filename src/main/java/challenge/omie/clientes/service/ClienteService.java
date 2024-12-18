package challenge.omie.clientes.service;

import challenge.omie.clientes.domain.cliente.Cliente;
import challenge.omie.clientes.domain.cliente.ClienteDTO;
import challenge.omie.clientes.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> getClientes(){
        return clienteRepository.findAll().stream().map(ClienteDTO::new).toList();
    }

    public Page<ClienteDTO> getClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(ClienteDTO::new);
    }

    public Optional<Cliente> getClienteById(Long id){
        return clienteRepository.findById(id);
    }

    public Cliente salvar(Cliente cliente){
        return clienteRepository.save(cliente);
    }
}
