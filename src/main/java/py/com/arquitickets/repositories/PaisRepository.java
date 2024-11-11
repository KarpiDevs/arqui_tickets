package py.com.arquitickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.arquitickets.models.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
}

