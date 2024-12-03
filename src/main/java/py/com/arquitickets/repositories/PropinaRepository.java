package py.com.arquitickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.arquitickets.models.Propina;

import java.time.LocalDate;
import java.util.List;

public interface PropinaRepository extends JpaRepository<Propina, Long> {
    List<Propina> findByFecha(LocalDate fecha);
}
