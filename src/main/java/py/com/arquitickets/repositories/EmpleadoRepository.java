package py.com.arquitickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.arquitickets.models.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}

