package py.com.arquitickets.models;

import java.io.Serializable;
import java.util.Objects;

public class ReservaConsumosId implements Serializable {
    private Long reserva;
    private Long producto;

    public ReservaConsumosId() {}

    public ReservaConsumosId(Long reserva, Long producto) {
        this.reserva = reserva;
        this.producto = producto;
    }

    // Getters, Setters, equals() y hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservaConsumosId that = (ReservaConsumosId) o;
        return Objects.equals(reserva, that.reserva) && Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reserva, producto);
    }

    // Getters y Setters
    public Long getReserva() {
        return reserva;
    }

    public void setReserva(Long reserva) {
        this.reserva = reserva;
    }

    public Long getProducto() {
        return producto;
    }

    public void setProducto(Long producto) {
        this.producto = producto;
    }
}
