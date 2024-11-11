package py.com.arquitickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.arquitickets.models.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}

