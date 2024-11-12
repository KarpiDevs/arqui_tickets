    package py.com.arquitickets.models;

    import jakarta.persistence.*;

    @Entity
    @Table(name = "reserva_consumos")
    @IdClass(ReservaConsumosId.class)
    public class ReservaConsumos {

        @Id
        @ManyToOne
        @JoinColumn(name = "nro_reserva", referencedColumnName = "nro_reserva")
        private ReservaMesa reserva;

        @Id
        @ManyToOne
        @JoinColumn(name = "cod_producto", referencedColumnName = "cod_producto")
        private Producto producto;

        @Column(name = "cantidad", nullable = false)
        private Double cantidad;

        public ReservaMesa getReserva() {
            return reserva;
        }

        public void setReserva(ReservaMesa reserva) {
            this.reserva = reserva;
        }

        public Producto getProducto() {
            return producto;
        }

        public void setProducto(Producto producto) {
            this.producto = producto;
        }

        public Double getCantidad() {
            return cantidad;
        }

        public void setCantidad(Double cantidad) {
            this.cantidad = cantidad;
        }
    }

