package py.com.arquitickets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.arquitickets.dto.*;
import py.com.arquitickets.models.*;
import py.com.arquitickets.repositories.PedidoRepository;
import py.com.arquitickets.services.*;
import py.com.arquitickets.utils.Respuestas;
import py.com.arquitickets.utils.RespuestasDashboard;

import java.util.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {

    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final MetodoPagoService metodoPagoService;
    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidosController(ClienteService clienteService, ProductoService productoService, MetodoPagoService metodoPagoService, PedidoService pedidoService, PedidoRepository pedidoRepository) {
        this.clienteService = clienteService;
        this.productoService = productoService;
        this.metodoPagoService = metodoPagoService;
        this.pedidoService = pedidoService;
        this.pedidoRepository = pedidoRepository;
    }

    @PutMapping
    public ResponseEntity<Respuestas> insertarPedido(@RequestBody PedidoDTO pedidoReq) {
        Optional<Cliente> cliente = clienteService.getClienteById(pedidoReq.getCodCliente());
        Pedido pedido;
        if (cliente.isPresent()){
            pedido =  pedidoService.crearPedido( cliente.get() );
        }else{
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Cliente seleccionado no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Respuestas response = new Respuestas(HttpStatus.OK, "Pedido agendado", pedido);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/consumo/{nroPedido}")
    public ResponseEntity<Respuestas> agregarConsumoPedido(@RequestBody PedidoDTO pedidoReq, @PathVariable Long nroPedido) {
        Optional<Pedido> pedido = pedidoService.getPedidoById(nroPedido);
        Optional<Producto> producto = productoService.getProductoById(pedidoReq.getCodProducto());
        Boolean emitirAlerta;
        Double vStockActual;

        if (!producto.isPresent()){
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "El producto seleccionado no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if (producto.get().getStockActual() == null){
            vStockActual = 0.0;
        }else{
            vStockActual = producto.get().getStockActual();
        }
        if (vStockActual == 0.0){
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "No hay stock disponible para el producto");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if (pedido.isPresent()){
            pedidoService.agregarConsumoPedido(pedido.get(), producto.get(), pedidoReq.getCantidad());
            emitirAlerta = productoService.controlStockMinimo(producto.get().getCodProducto());
        }else{
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "El pedido seleccionado no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Respuestas response = new Respuestas(HttpStatus.OK, "Consumo agregado al pedido", pedido.get());
        if (emitirAlerta){
            response.setMessage(response.getMessage() + " - PRODUCTO LLEGÓ AL STOCK MÍNIMO DEFINIDO");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/finalizar/{nroPedido}")
    public ResponseEntity<Respuestas> finalizarPedido(@RequestBody CierreReservaDTO cierrePedido, @PathVariable Long nroPedido) {
        Optional<Pedido> pedido = pedidoService.getPedidoById(nroPedido);
        Optional<MetodoPago> metodoPago = metodoPagoService.getMetodoPagoById(cierrePedido.getCodMetodoPago());

        if (metodoPago.isPresent()) {

            if (pedido.isPresent()) {
                pedido.get().setEstadoPedido("C");
                List<PedidoConsumo> consumos = pedidoService.getConsumosByPedido(pedido.get());
                pedido.get().setDetalleConsumos(consumos);
                pedidoService.actualizarReservaMesa(pedido.get());
            } else {
                Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "El pedido seleccionada no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        }else{
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "El metodo de pago seleccionado no existe.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Respuestas response = new Respuestas(HttpStatus.OK, "Pedido realizado", pedido.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
