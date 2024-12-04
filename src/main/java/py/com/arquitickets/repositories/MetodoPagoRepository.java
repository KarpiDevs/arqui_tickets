package py.com.arquitickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.arquitickets.models.MetodoPago;
import py.com.arquitickets.models.Propinas;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
}

