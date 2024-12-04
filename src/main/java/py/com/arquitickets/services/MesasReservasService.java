package py.com.arquitickets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import py.com.arquitickets.models.*;
import py.com.arquitickets.repositories.*;
import py.com.arquitickets.utils.Respuestas;


import java.util.*;

@Service
public class MesasReservasService {

    private final MesaRepository mesaRepository;
    private final ReservaMesaRepository reservaMesaRepository;
    private final ReservaConsumosRepository reservaConsumosRepository;
    private final ProductoRepository productoRepository;
    private final PropinaRepository propinaRepository;

    @Autowired
    public MesasReservasService (MesaRepository mesaRepository,
                                 ReservaMesaRepository reservaMesaRepository,
                                 ReservaConsumosRepository reservaConsumosRepository, ProductoRepository productoRepository, PropinaRepository propinaRepository) {
        this.mesaRepository = mesaRepository;
        this.reservaMesaRepository = reservaMesaRepository;
        this.reservaConsumosRepository = reservaConsumosRepository;
        this.productoRepository = productoRepository;
        this.propinaRepository = propinaRepository;
    }

    // Se detallan servicios para Mesas nada más.
    public List<Mesa> getAllMesas() {
        return mesaRepository.findAll();
    }

    public Optional<Mesa> getMesaById(Long id) {
        return mesaRepository.findById(id);
    }

    public Respuestas crearMesas(Long cantidad) {

        String error = "";
        int cantSillas = 4;
        List<Mesa> mesas = new ArrayList<>();
        for (int i=0; i<cantidad; i++) {
            try {
                Mesa mesa = new Mesa();
                mesa.setDescMesa("Mesa Simple");
                mesa.setCantSillas(Long.valueOf(cantSillas));
                mesaRepository.save(mesa);
                mesas.add(mesa);
            }catch (Exception e) {
                error = e.getMessage();
            }
        }
        Respuestas res = null;
        if (error.equals("")) {
            res = new Respuestas(HttpStatus.OK, "Mesas creadas exitosamente", mesas);
        }else{
            res = new Respuestas(HttpStatus.INTERNAL_SERVER_ERROR, error);
        }

        return res;
    }

    // Se detallan servicios para Consumos de Reserva
    public List<ReservaConsumos> getConsumosByReserva(ReservaMesa reservaMesa) {
        return reservaConsumosRepository.findByReserva(reservaMesa);
    }

    // Se detallan servicios para Reserva de Mesa

    public List<ReservaMesa> getReservaAbiertaByID(Long id) {
        return reservaMesaRepository.findByNroReservaAndEstadoReserva(id, "A");
    }

    public ReservaMesa consultarEstadoMesa(Mesa mesa){
        ReservaMesa estadoMesa = null;

        List <ReservaMesa> reserva = reservaMesaRepository.findByMesaAndEstadoReserva(mesa, "A");
        if (reserva.size() == 1) {
            estadoMesa = reserva.get(0);
            estadoMesa.setDetalleConsumos( getConsumosByReserva(estadoMesa) );
        }
        return estadoMesa;
    }

    public ReservaMesa crearReservaMesa(Cliente cliente, Mesa mesa, Integer codEmpleado){
        ReservaMesa reserva = new ReservaMesa();
        reserva.setCliente(cliente);
        reserva.setMesa(mesa);
        reserva.setEstadoReserva("A");
        reserva.setTotalConsumo(0.0);
        reserva.setFechaInicio(new Date());
        reserva.setCodEmpleado(codEmpleado);
        return reservaMesaRepository.save(reserva);
    }

    public ReservaMesa actualizarReservaMesa(ReservaMesa reservaMesa, Double montoPropina) {
        ReservaMesa reserva;
        try{
            reserva = reservaMesaRepository.save(reservaMesa);
            // Solo si es distinto de null y mayor a 0 se inserta.
            // Si es null significa que es una actualización de reserva.
            if ((montoPropina != null) && (montoPropina > 0) ){
                Propinas propinaMesa = new Propinas();
                propinaMesa.setEmpleado( reservaMesa.getMesa().getEmpleado() );
                propinaMesa.setMonto( montoPropina );
                propinaRepository.save(propinaMesa);
            }
        }catch (Exception e){
            reserva = null;
        }
        return reserva;
    }

    public ReservaConsumos agregarConsumoReserva(ReservaMesa reserva, Producto producto, Double cantidad) {
        ReservaConsumos consumo = reservaConsumosRepository.findByReservaAndProducto(reserva, producto);
        if (consumo == null) {
            consumo = new ReservaConsumos();
            consumo.setReserva(reserva);
            consumo.setProducto(producto);
            consumo.setCantidad(cantidad);
        } else {
            consumo.setCantidad(consumo.getCantidad() + cantidad);
        }
        consumo = reservaConsumosRepository.save(consumo);

        return consumo;
    }

    public Double obtenerVentaEmpleado(Integer codEmpleado) {
        List<ReservaMesa> reservas = reservaMesaRepository.findByCodEmpleado(codEmpleado);

        if (reservas.isEmpty()) {
            return 0.0;
        }
        return reservas.stream()
                .mapToDouble(ReservaMesa::getTotalConsumo)
                .sum();  // Sumamos todos los valores
    }

    public Double obtenerVentaMesa(Integer nroMesa) {
        List<ReservaMesa> reservas = reservaMesaRepository.findByNroMesa(nroMesa);

        if (reservas.isEmpty()) {
            return 0.0;
        }
        return reservas.stream()
                .mapToDouble(ReservaMesa::getTotalConsumo)
                .sum();  // Sumamos todos los valores
    }

    public Double obtenerVentaFecha(Date fechaInicio) {
        List<ReservaMesa> reservas = reservaMesaRepository.findByFechaInicio(fechaInicio);

        if (reservas.isEmpty()) {
            return 0.0;
        }
        return reservas.stream()
                .mapToDouble(ReservaMesa::getTotalConsumo)
                .sum();  // Sumamos todos los valores
    }

}
