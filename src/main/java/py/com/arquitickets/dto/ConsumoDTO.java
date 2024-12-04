package py.com.arquitickets.dto;

public class ConsumoDTO {

    Long codProducto;

    String descProducto;

    Double cantidad;

    Double precioUnitario;

    Double precioTotal;

    public ConsumoDTO(Long codProducto, String descProducto, Double precioUnitario, Double cantidad) {
        this.codProducto = codProducto;
        this.descProducto = descProducto;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.precioTotal = precioUnitario * cantidad;
    }

    public ConsumoDTO(Long codProducto, String descProducto, Double cantidad) {
        this.codProducto = codProducto;
        this.descProducto = descProducto;
        this.cantidad = cantidad;
    }

    public Long getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(Long codProducto) {
        this.codProducto = codProducto;
    }

    public String getDescProducto() {
        return descProducto;
    }

    public void setDescProducto(String descProducto) {
        this.descProducto = descProducto;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }
}