package py.com.arquitickets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import py.com.arquitickets.controllers.MesasReservasController;
import py.com.arquitickets.models.*;
import py.com.arquitickets.repositories.CategoriaRepository;
import py.com.arquitickets.repositories.MesaRepository;
import py.com.arquitickets.repositories.ReservaConsumosRepository;
import py.com.arquitickets.repositories.ReservaMesaRepository;
import py.com.arquitickets.utils.Respuestas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MesasReservasService {

    private final MesaRepository mesaRepository;
    private final ReservaMesaRepository reservaMesaRepository;
    private final ReservaConsumosRepository reservaConsumosRepository;

    @Autowired
    public MesasReservasService (MesaRepository mesaRepository,
                                 ReservaMesaRepository reservaMesaRepository,
                                 ReservaConsumosRepository reservaConsumosRepository) {
        this.mesaRepository = mesaRepository;
        this.reservaMesaRepository = reservaMesaRepository;
        this.reservaConsumosRepository = reservaConsumosRepository;
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

    public ReservaMesa crearReservaMesa(Cliente cliente, Mesa mesa){
        ReservaMesa reserva = new ReservaMesa();
        reserva.setCliente(cliente);
        reserva.setMesa( mesa);
        reserva.setEstadoReserva("A");
        reserva.setTotalConsumo(0.0);
        reserva.setFechaInicio(new Date());
        return reservaMesaRepository.save(reserva);
    }

    public ReservaMesa actualizarReservaMesa(ReservaMesa reservaMesa) {
        return reservaMesaRepository.save(reservaMesa);
    }

    public ReservaConsumos agregarConsumoReserva(ReservaMesa reserva, Producto producto, Double cantidad){
        ReservaConsumos consumo = new ReservaConsumos();
        consumo.setReserva(reserva);
        consumo.setProducto(producto);
        consumo.setCantidad(cantidad);

        consumo = reservaConsumosRepository.save(consumo);
        return consumo;
    }

}
