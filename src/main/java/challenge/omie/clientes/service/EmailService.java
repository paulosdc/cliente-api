package challenge.omie.clientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challenge.omie.clientes.domain.email.Email;
import challenge.omie.clientes.domain.email.EmailDTO;
import challenge.omie.clientes.repository.EmailRepository;

@Service
public class EmailService {
    
    @Autowired
    private EmailRepository emailRepository;

    public List<EmailDTO> getEmails(){
        return emailRepository.findAll().stream().map(EmailDTO::new).toList();
    }

    public Email salvar(Email email){
        return emailRepository.save(email);
    }

    public void deletar(Email email){
        emailRepository.delete(email);
    }
}
