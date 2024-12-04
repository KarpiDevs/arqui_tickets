package py.com.arquitickets.dto;

public class ReservaDTO {

    private Long codCliente;

    private Long codProducto;

    private Double cantidad;

    private Integer codEmpleado;

    private String estadoReserva;

    private Long nroMesa;

    private String descMesa;

    private Long nroReserva;

    private Double totalConsumo;

    public ReservaDTO(Long nroMesa, String descMesa, String estadoReserva) {
        this.nroMesa = nroMesa;
        this.descMesa = descMesa;
        this.estadoReserva = estadoReserva;
    }

    public ReservaDTO(Long nroReserva, Double totalConsumo) {
        this.nroReserva = nroReserva;
        this.totalConsumo = totalConsumo;
    }

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

    public Integer getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(Integer codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public Long getNroMesa() {
        return nroMesa;
    }

    public void setNroMesa(Long nroMesa) {
        this.nroMesa = nroMesa;
    }

    public String getDescMesa() {
        return descMesa;
    }

    public void setDescMesa(String descMesa) {
        this.descMesa = descMesa;
    }

    public Long getNroReserva() {
        return nroReserva;
    }

    public void setNroReserva(Long nroReserva) {
        this.nroReserva = nroReserva;
    }

    public Double getTotalConsumo() {
        return totalConsumo;
    }

    public void setTotalConsumo(Double totalConsumo) {
        this.totalConsumo = totalConsumo;
    }
}