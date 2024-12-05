package py.com.arquitickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.arquitickets.dto.ConsumoDTO;
import py.com.arquitickets.dto.ReservaDTO;
import py.com.arquitickets.models.Mesa;
import py.com.arquitickets.models.Pedido;
import py.com.arquitickets.models.ReservaMesa;

import java.util.Date;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}

