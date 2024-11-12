package py.com.arquitickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.arquitickets.models.Mesa;
import py.com.arquitickets.models.Producto;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

}

