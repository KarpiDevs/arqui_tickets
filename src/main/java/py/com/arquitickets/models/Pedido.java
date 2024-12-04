package py.com.arquitickets.models;

import jakarta.persistence.*;
import py.com.arquitickets.dto.ConsumoDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_pedido")
    private Long nroPedido;

    @ManyToOne
    @JoinColumn(name = "cod_cliente", referencedColumnName = "cod_cliente")
    private Cliente cliente;

    @Column(name = "estado_pedido", nullable = false)
    private String estadoPedido;

    @Column(name = "total_consumo", nullable = false)
    private Double totalConsumo;

    @Column(name = "fecha_pedido", nullable = false)
    private Date fechaPedido;

    @ManyToOne
    @JoinColumn(name = "cod_mto_pago", referencedColumnName = "cod_mto_pago")
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "cod_empleado", referencedColumnName = "cod_empleado")
    private Empleado empleado;

    @Transient
    private List<ConsumoDTO> detalleConsumos;

    public Long getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(Long nroPedido) {
        this.nroPedido = nroPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Double getTotalConsumo() {
        return totalConsumo;
    }

    public void setTotalConsumo(Double totalConsumo) {
        this.totalConsumo = totalConsumo;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public void setDetalleConsumos(List<PedidoConsumo> detalleConsumos) {
        this.detalleConsumos = new ArrayList<>();
        for (PedidoConsumo consumo : detalleConsumos) {
            ConsumoDTO detalle = new ConsumoDTO(consumo.getProducto().getCodProducto(),
                    consumo.getProducto().getDescProducto(),
                    consumo.getProducto().getPrecioUnitario(),
                    consumo.getCantidad());
            this.detalleConsumos.add(detalle);
        }
    }
}


