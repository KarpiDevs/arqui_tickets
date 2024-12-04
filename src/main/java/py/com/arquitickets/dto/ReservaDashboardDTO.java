package py.com.arquitickets.dto;

public class ReservaDashboardDTO {

    private Long nroReserva;

    private Double totalConsumo;

    public ReservaDashboardDTO(Long nroReserva, Double totalConsumo) {
        this.nroReserva = nroReserva;
        this.totalConsumo = totalConsumo;
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