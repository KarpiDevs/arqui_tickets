package py.com.arquitickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.arquitickets.models.Mesa;
import py.com.arquitickets.models.ReservaConsumos;
import py.com.arquitickets.models.ReservaMesa;

import java.util.List;

@Repository
public interface ReservaConsumosRepository extends JpaRepository<ReservaConsumos, Long> {
    List<ReservaConsumos> findByReserva(ReservaMesa reservaMesa);
}

