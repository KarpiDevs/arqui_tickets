package py.com.arquitickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.arquitickets.models.*;

import java.util.List;

@Repository
public interface PedidoConsumosRepository extends JpaRepository<PedidoConsumo, Long> {
    PedidoConsumo findByPedidoAndProducto(Pedido pedido, Producto producto);

    List<PedidoConsumo> findByPedido(Pedido pedido);
}

