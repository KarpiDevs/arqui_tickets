    package py.com.arquitickets.models;

    import jakarta.persistence.*;

    @Entity
    @Table(name = "pedido_consumos")
    @IdClass(PedidoConsumosId.class)
    public class PedidoConsumo {

        @Id
        @ManyToOne
        @JoinColumn(name = "nro_pedido", referencedColumnName = "nro_pedido")
        private Pedido pedido;

        @Id
        @ManyToOne
        @JoinColumn(name = "cod_producto", referencedColumnName = "cod_producto")
        private Producto producto;

        @Column(name = "cantidad", nullable = false)
        private Double cantidad;

        public Pedido getPedido() {
            return pedido;
        }

        public void setPedido(Pedido pedido) {
            this.pedido = pedido;
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

