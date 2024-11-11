package py.com.arquitickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.arquitickets.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

