package py.com.arquitickets.dto;

public class CierreReservaDTO {

    Double montoPropina;

    Long codMetodoPago;

    public Double getMontoPropina() {
        return montoPropina;
    }

    public void setMontoPropina(Double montoPropina) {
        this.montoPropina = montoPropina;
    }

    public Long getCodMetodoPago() {
        return codMetodoPago;
    }

    public void setCodMetodoPago(Long codMetodoPago) {
        this.codMetodoPago = codMetodoPago;
    }
}
