package py.com.arquitickets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.arquitickets.dto.ReservaDTO;
import py.com.arquitickets.models.*;
import py.com.arquitickets.repositories.ReservaConsumosRepository;
import py.com.arquitickets.services.CategoriaService;
import py.com.arquitickets.services.ClienteService;
import py.com.arquitickets.services.MesasReservasService;
import py.com.arquitickets.services.ProductoService;
import py.com.arquitickets.utils.Respuestas;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mesas")
public class MesasReservasController {

    private final MesasReservasService mesasService;
    private final ClienteService clienteService;
    private final ProductoService productoService;

    @Autowired
    public MesasReservasController(MesasReservasService mesasService, ClienteService clienteService, ProductoService productoService) {
        this.mesasService = mesasService;
        this.clienteService = clienteService;
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<Respuestas> getAllMesas() {
        List<Mesa> mesas = mesasService.getAllMesas();
        Respuestas response = new Respuestas(HttpStatus.OK, "Mesas obtenidas exitosamente", mesas);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{cantidad}")
    public ResponseEntity<Respuestas> crearCantidadMesas(@PathVariable Long cantidad) {
        Respuestas resMesas = mesasService.crearMesas(cantidad);
        return new ResponseEntity<>(resMesas, HttpStatus.OK);
    }

    @GetMapping("/estado/{nroMesa}")
    public ResponseEntity<Respuestas> consultarEstadoMesa(@PathVariable Long nroMesa) {
        Optional<Mesa> mesa = mesasService.getMesaById(nroMesa);
        ReservaMesa estadoMesa = null;
        if (mesa.isPresent()) {
            estadoMesa = mesasService.consultarEstadoMesa(mesa.get());
            Respuestas response = new Respuestas(HttpStatus.OK, "Detalles de mesas obtenido", estadoMesa);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Mesa seleccionada no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/reserva/{nroMesa}")
    public ResponseEntity<Respuestas> reservaMesaCliente(@RequestBody ReservaDTO reservaMesa, @PathVariable Long nroMesa) {
        Optional<Cliente> cliente = clienteService.getClienteById(reservaMesa.getCodCliente());
        Optional<Mesa> mesa = mesasService.getMesaById(nroMesa);
        ReservaMesa reserva = null;
        if (cliente.isPresent()){
            if (mesa.isPresent()){
                reserva =  mesasService.crearReservaMesa(cliente.get(), mesa.get() );
            }else{
                Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Mesa seleccionado no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }else{
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Cliente seleccionado no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Respuestas response = new Respuestas(HttpStatus.OK, "Reservado la mesa al cliente", reserva);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reserva/{nroReserva}")
    public ResponseEntity<Respuestas> actualizarReservaMesaCliente(@RequestBody ReservaDTO reservaMesa, @PathVariable Long nroReserva) {
        Optional<Cliente> cliente = clienteService.getClienteById(reservaMesa.getCodCliente());
        List<ReservaMesa> reserva = mesasService.getReservaAbiertaByID(nroReserva);

        if (!reserva.isEmpty()){
            if (cliente.isPresent()){
                reserva.get(0).setCliente(cliente.get());
                mesasService.actualizarReservaMesa(reserva.get(0));
            }else{
                Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Mesa seleccionado no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }else{
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Reserva no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Respuestas response = new Respuestas(HttpStatus.OK, "Cambio de cliente a la reserva", reserva);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/consumo/{nroReserva}")
    public ResponseEntity<Respuestas> agregarConsumoReserva(@RequestBody ReservaDTO reservaMesa, @PathVariable Long nroReserva) {
        ReservaConsumos consumo = null;
        List<ReservaMesa> reserva = mesasService.getReservaAbiertaByID(nroReserva);
        Optional<Producto> producto = productoService.getProductoById(reservaMesa.getCodProducto());
        Boolean emitirAlerta;

        if (!reserva.isEmpty()){
            if (producto.isPresent()){
                mesasService.agregarConsumoReserva(reserva.get(0), producto.get(), reservaMesa.getCantidad());
                emitirAlerta = productoService.controlStockMinimo(producto.get().getCodProducto());
            }else{
                Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "El producto seleccionado no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }else{
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "La reserva seleccionada no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Respuestas response = new Respuestas(HttpStatus.OK, "Consumo agregado a la reserva", reserva.get(0));
        if (emitirAlerta){
            response.setMessage(response.getMessage() + " - PRODUCTO LLEGÓ AL STOCK MÍNIMO DEFINIDO");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/cerrar/{nroReserva}")
    public ResponseEntity<Respuestas> cerrarReservaMesa(@PathVariable Long nroReserva) {
        List<ReservaMesa> reserva = mesasService.getReservaAbiertaByID(nroReserva);

        if (!reserva.isEmpty()){
            reserva.get(0).setEstadoReserva("C");
            reserva.get(0).setFechaFin(new Date());
            List<ReservaConsumos> consumos = mesasService.getConsumosByReserva(reserva.get(0));
            reserva.get(0).setDetalleConsumos(consumos);
            mesasService.actualizarReservaMesa(reserva.get(0));
        }else{
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "La reserva seleccionada no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Respuestas response = new Respuestas(HttpStatus.OK, "Reserva cerrada", reserva.get(0));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
