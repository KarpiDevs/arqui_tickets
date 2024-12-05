package py.com.arquitickets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import py.com.arquitickets.dto.*;
import py.com.arquitickets.models.*;
import py.com.arquitickets.repositories.*;
import py.com.arquitickets.utils.Respuestas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoConsumosRepository pedidoConsumosRepository;
    private final MetodoPagoService metodoPagoService;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, PedidoConsumosRepository pedidoConsumosRepository, MetodoPagoService metodoPagoService) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoConsumosRepository = pedidoConsumosRepository;
        this.metodoPagoService = metodoPagoService;
    }

    public Optional<Pedido> getPedidoById(Long id) { return pedidoRepository.findById(id); }

    public List<PedidoConsumo> getConsumosByPedido(Pedido pedido){
        return pedidoConsumosRepository.findByPedido(pedido);
    }

    public Pedido crearPedido(Cliente cliente){
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEstadoPedido("A");
        pedido.setFechaPedido(new Date());
        pedido.setTotalConsumo(0.0);
        return pedidoRepository.save(pedido);
    }

    public Pedido actualizarReservaMesa(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public PedidoConsumo agregarConsumoPedido(Pedido pedido, Producto producto, Double cantidad) {
        PedidoConsumo consumo = pedidoConsumosRepository.findByPedidoAndProducto(pedido, producto);
        if (consumo == null) {
            consumo = new PedidoConsumo();
            consumo.setPedido(pedido);
            consumo.setProducto(producto);
            consumo.setCantidad(cantidad);
        } else {
            consumo.setCantidad(consumo.getCantidad() + cantidad);
        }
        consumo = pedidoConsumosRepository.save(consumo);

        return consumo;
    }


}
