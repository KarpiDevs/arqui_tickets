package py.com.arquitickets.models;

import java.io.Serializable;
import java.util.Objects;

public class PedidoConsumosId implements Serializable {
    private Long pedido;
    private Long producto;

    public PedidoConsumosId() {}

    public PedidoConsumosId(Long pedido, Long producto) {
        this.pedido = pedido;
        this.producto = producto;
    }

    // Getters, Setters, equals() y hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoConsumosId that = (PedidoConsumosId) o;
        return Objects.equals(pedido, that.pedido) && Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedido, producto);
    }

    // Getters y Setters
    public Long getPedido() {
        return pedido;
    }

    public void setPedido(Long reserva) {
        this.pedido = reserva;
    }

    public Long getProducto() {
        return producto;
    }

    public void setProducto(Long producto) {
        this.producto = producto;
    }
}
