package py.com.arquitickets.models;

import jakarta.persistence.*;
import py.com.arquitickets.dto.ConsumoDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reserva_mesa")
public class ReservaMesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_reserva")
    private Long nroReserva;

    @ManyToOne
    @JoinColumn(name = "cod_cliente", referencedColumnName = "cod_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "nro_mesa", referencedColumnName = "nro_mesa")
    private Mesa mesa;

    @Column(name = "estado_reserva", nullable = false)
    private String estadoReserva;

    @Column(name = "total_consumo", nullable = false)
    private Double totalConsumo;

    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @Transient
    private List<ConsumoDTO> detalleConsumos;

    public Long getNroReserva() {
        return nroReserva;
    }

    public void setNroReserva(Long nroReserva) {
        this.nroReserva = nroReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public Double getTotalConsumo() {
        return totalConsumo;
    }

    public void setTotalConsumo(Double totalConsumo) {
        this.totalConsumo = totalConsumo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<ConsumoDTO> getDetalleConsumos() {
        return detalleConsumos;
    }

    public void setDetalleConsumos(List<ReservaConsumos> detalleConsumos) {
        this.detalleConsumos = new ArrayList<>();
        for (ReservaConsumos consumo : detalleConsumos) {
            ConsumoDTO detalle = new ConsumoDTO(consumo.getProducto().getCodProducto(),
                                                    consumo.getProducto().getDescProducto(),
                                                    consumo.getProducto().getPrecioUnitario(),
                                                    consumo.getCantidad());
            this.detalleConsumos.add(detalle);
        }
    }
}

