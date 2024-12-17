package challenge.omie.clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import challenge.omie.clientes.domain.email.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long>{
}
