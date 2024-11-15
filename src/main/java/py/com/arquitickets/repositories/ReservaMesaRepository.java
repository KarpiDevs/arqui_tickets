package py.com.arquitickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.arquitickets.models.Mesa;
import py.com.arquitickets.models.ReservaMesa;

import java.util.List;

@Repository
public interface ReservaMesaRepository extends JpaRepository<ReservaMesa, Long> {

    List<ReservaMesa> findByMesaAndEstadoReserva(Mesa mesa, String estadoReserva);

    List<ReservaMesa> findByNroReservaAndEstadoReserva(Long nroReserva, String estadoReserva);
}

