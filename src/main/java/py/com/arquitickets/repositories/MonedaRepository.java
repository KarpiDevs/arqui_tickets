package py.com.arquitickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.arquitickets.models.Moneda;

import java.util.Optional;

@Repository
public interface MonedaRepository extends JpaRepository<Moneda, Long> {

    Optional<Moneda> findByCodMoneda(String codMoneda);

}

