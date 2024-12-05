package py.com.arquitickets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.arquitickets.dto.CierreReservaDTO;
import py.com.arquitickets.dto.ConsumoDTO;
import py.com.arquitickets.dto.ReservaDTO;
import py.com.arquitickets.dto.ReservaDashboardDTO;
import py.com.arquitickets.models.*;
import py.com.arquitickets.repositories.ReservaConsumosRepository;
import py.com.arquitickets.services.*;
import py.com.arquitickets.utils.Respuestas;
import py.com.arquitickets.utils.RespuestasDashboard;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
@RequestMapping("/api/mesas")
public class MesasReservasController {

    private final MesasReservasService mesasService;
    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final MetodoPagoService metodoPagoService;
    private final EmpleadoService empleadoService;

    @Autowired
    public MesasReservasController(MesasReservasService mesasService, ClienteService clienteService, ProductoService productoService, MetodoPagoService metodoPagoService, EmpleadoService empleadoService) {
        this.mesasService = mesasService;
        this.clienteService = clienteService;
        this.productoService = productoService;
        this.metodoPagoService = metodoPagoService;
        this.empleadoService = empleadoService;
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

    @PutMapping("/asignar/{nroMesa}/{codEmpleado}")
    public ResponseEntity<Respuestas> crearCantidadMesas(@PathVariable Long nroMesa,@PathVariable Long codEmpleado) {
        Optional<Mesa> mesa = mesasService.getMesaById(nroMesa);
        Mesa mesaAsignada;
        if (mesa.isPresent()){
            Optional<Empleado> empleado = empleadoService.getEmpleadoById(codEmpleado);

            if (empleado.isPresent()){
                mesaAsignada= mesasService.asignarEmpleado(mesa.get(), empleado.get());
            }else{
                Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Empleado seleccionado no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        }else{
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "Mesa seleccionada no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Respuestas response = new Respuestas(HttpStatus.OK, "Empleado asignado correctamente a la mesa");
        return new ResponseEntity<>(response, HttpStatus.OK);
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
                reserva =  mesasService.crearReservaMesa(cliente.get(), mesa.get(), reservaMesa.getCodEmpleado());
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
                mesasService.actualizarReservaMesa(reserva.get(0),null);
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
    public ResponseEntity<Respuestas> cerrarReservaMesa(@RequestBody CierreReservaDTO cierreReserva, @PathVariable Long nroReserva) {
        List<ReservaMesa> reserva = mesasService.getReservaAbiertaByID(nroReserva);
        Optional<MetodoPago> metodoPago = metodoPagoService.getMetodoPagoById(cierreReserva.getCodMetodoPago());

        if (metodoPago.isPresent()) {

            if (!reserva.isEmpty()) {
                reserva.get(0).setEstadoReserva("C");
                reserva.get(0).setFechaFin(new Date());
                List<ReservaConsumos> consumos = mesasService.getConsumosByReserva(reserva.get(0));
                reserva.get(0).setDetalleConsumos(consumos);
                mesasService.actualizarReservaMesa(reserva.get(0), cierreReserva.getMontoPropina());
            } else {
                Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "La reserva seleccionada no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        }else{
            Respuestas response = new Respuestas(HttpStatus.NOT_FOUND, "El metodo de pago seleccionado no existe.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Respuestas response = new Respuestas(HttpStatus.OK, "Reserva cerrada", reserva.get(0));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reportes/empleados")
    public ResponseEntity<Respuestas> obtenerVentaEmpleado(@RequestBody Map<String,Integer> empleado) {
        Integer codEmpleado = (Integer) empleado.get("codEmpleado");
        Double venta = mesasService.obtenerVentaEmpleado(codEmpleado);
        Respuestas response = new Respuestas(HttpStatus.OK, "Reporte de Venta por Empleados", venta);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reportes/mesas")
    public ResponseEntity<Respuestas> obtenerVentaMesa(@RequestBody Map<String,Integer> mesas) {
        Integer nroMesa = (Integer) mesas.get("nroMesa");
        Double venta = mesasService.obtenerVentaMesa(nroMesa);
        Respuestas response = new Respuestas(HttpStatus.OK, "Reporte de Venta por Mesas", venta);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reportes/fecha")
    public ResponseEntity<Respuestas> obtenerVentaFecha(@RequestBody Map<String,Date> fechaInicio) {
        Date fecha = (Date) fechaInicio.get("fechaInicio");
        Double venta = mesasService.obtenerVentaFecha(fecha);
        Respuestas response = new Respuestas(HttpStatus.OK, "Reporte por Fechas", venta);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @PostMapping("/reportes/categoria")
    public ResponseEntity<Respuestas> obtenerVentaCategoria(@RequestBody Map<String,Long> codCategoria) {
        Long categoria = (Long) codCategoria.get("codCategoria");
        List<ConsumoDTO> venta = mesasService.obtenerVentaCategoria(categoria);
        Respuestas response = new Respuestas(HttpStatus.OK, "Reporte por Categoria", venta);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<RespuestasDashboard> obtenerDashboard() {
        // Llamar a los tres servicios y obtener los datos
        List<ConsumoDTO> topProductos = mesasService.ventasPorProducto();
        List<ReservaDTO> ocupacionMesas = mesasService.ocupacionMesas();
        List<ReservaDashboardDTO> ventasDiarias = mesasService.ventaDiarias();

        // Crear un mapa para contener todas las respuestas
        Map<String, Object> data = new HashMap<>();
        data.put("Top Productos Vendidos", topProductos);
        data.put("Ocupacion de Mesas", ocupacionMesas);
        data.put("Facturación Diaria", ventasDiarias);

        // Crear la respuesta con el mapa de datos
        RespuestasDashboard response = new RespuestasDashboard(HttpStatus.OK, "Dashboard General", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
