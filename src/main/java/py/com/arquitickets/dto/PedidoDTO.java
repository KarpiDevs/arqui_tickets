package py.com.arquitickets.dto;

public class PedidoDTO {

    private Long codCliente;

    private Long codProducto;

    private Double cantidad;

    private Double totalConsumo;

    public Long getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Long codCliente) {
        this.codCliente = codCliente;
    }

    public Long getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(Long codProducto) {
        this.codProducto = codProducto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotalConsumo() {
        return totalConsumo;
    }

    public void setTotalConsumo(Double totalConsumo) {
        this.totalConsumo = totalConsumo;
    }
}